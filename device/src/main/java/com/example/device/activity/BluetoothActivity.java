package com.example.device.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.device.R;
import com.example.device.adapter.BlueListAdapter;
import com.example.device.bean.BlueDevice;
import com.example.device.util.BluetoothUtil;

import java.util.ArrayList;
import java.util.Set;

/**
 * BluetoothActivity
 *
 * @author lao
 * @date 2019/9/9
 */

public class BluetoothActivity extends AppCompatActivity implements
        OnCheckedChangeListener, OnItemClickListener {
    private static final String TAG = "BluetoothActivity";
    private CheckBox ck_bluetooth;
    private TextView tv_discovery;
    private ListView lv_bluetooth;          //展示蓝牙设备的列表视图对象
    private BluetoothAdapter mBluetooth;      //蓝牙适配器对象
    private BlueListAdapter mListAdapter;   //蓝牙设备的列表适配器对象
    private ArrayList<BlueDevice> mDeviceList = new ArrayList<BlueDevice>();        //蓝牙设备队列
    private Handler mHandler = new Handler();       //处理器对象
    private int mOpenCode = 1;      //是否允许扫描蓝牙设备的选择对话框返回结果代码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initBluetooth();        //初始化蓝牙设配器服务
        ck_bluetooth = findViewById(R.id.ck_bluetooth);
        tv_discovery = findViewById(R.id.tv_discovery);
        lv_bluetooth = findViewById(R.id.lv_bluetooth);
        ck_bluetooth.setOnCheckedChangeListener(this);
        if (BluetoothUtil.getBlueToothStatus(this)) {
            ck_bluetooth.setChecked(true);
        }
        initBlueDevice();    //  初始化蓝牙设备列表
    }

    //初始化蓝牙适配器
    private void initBluetooth() {
        //Android从4.3开始增加支持BLE技术（即蓝牙4.0以上版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //获取蓝牙服务
            BluetoothManager bm = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetooth = bm.getAdapter();
        } else {
            mBluetooth = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetooth == null) {
            Toast.makeText(this, "本机为找到蓝牙功能", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //初始化蓝牙设备
    private void initBlueDevice() {
        mDeviceList.clear();
        //获取已经配对的蓝牙设备集合
        Set<BluetoothDevice> bondedDevices = mBluetooth.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            mDeviceList.add(new BlueDevice(device.getName(), device.getAddress(), device.getBondState()));
        }
        if (mListAdapter == null) { //首次打开页面，则创建一个新的蓝牙设备列表
            mListAdapter = new BlueListAdapter(this, mDeviceList);
            lv_bluetooth.setAdapter(mListAdapter);
            lv_bluetooth.setOnItemClickListener(this);
        } else {
            mListAdapter.notifyDataSetChanged();
        }
    }

    private Runnable mDiscoverable = new Runnable() {
        @Override
        public void run() {
            //Android8.0要在已打开功能时才会弹出下面的选择窗
            if (BluetoothUtil.getBlueToothStatus(BluetoothActivity.this)) {
                //弹出是否允许扫描蓝牙设备的选项对话框
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivity(intent);
            } else {
                mHandler.postDelayed(this, 100);
            }
        }
    };


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_bluetooth) {
            if (isChecked) {        //开启蓝牙
                ck_bluetooth.setText("蓝牙开");
                if (!BluetoothUtil.getBlueToothStatus(this)) {
                    BluetoothUtil.setBlueToothStatus(this, true);
                }
                mHandler.post(mDiscoverable);
            } else {
                ck_bluetooth.setText("蓝牙关");
                cancelDiscovery();      //取消蓝牙设备的搜索
                BluetoothUtil.setBlueToothStatus(this, false);
                initBlueDevice();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mOpenCode) {
            //延迟50毫秒后启动蓝牙设备的刷新任务
            mHandler.postDelayed(mRefresh, 50);
            if (requestCode == RESULT_OK) {
                Toast.makeText(this, "允许本地蓝牙被附近的其他蓝牙设备发现", Toast.LENGTH_SHORT).show();
            } else if (requestCode == RESULT_CANCELED){
                Toast.makeText(this, "允许本地蓝牙被附近的其他蓝牙设备发现", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //定义一个刷新任务，每隔两分钟舒心扫描任务
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            beginDiscovery();       //开始扫描周围的蓝牙设备
            //延迟2秒后再次启动蓝牙设备的刷线任务
            mHandler.postDelayed(this, 2000);
        }
    };

    //开始扫描周围的蓝牙设备
    private void beginDiscovery() {
        //如果当前不是正在搜索，则开始新的搜索任务
        if (!mBluetooth.isDiscovering()) {
            initBlueDevice();       //初始化蓝牙设备列表
            tv_discovery.setText("正在搜索蓝牙设备");
            mBluetooth.startDiscovery();    //开始扫描周围的蓝牙设备
        }
    }

    //取消蓝牙设备的搜索
    private void cancelDiscovery() {
        mHandler.removeCallbacks(mRefresh);
        tv_discovery.setText("取消搜索蓝牙设备");
        //当前正在搜索，则取消搜索任务
        if (mBluetooth.isDiscovering()) {
            mBluetooth.cancelDiscovery();       //取消扫描周围的蓝牙设备
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(mRefresh, 50);
        //需要过滤多个动作，则调用IntentFilter对象的addAction添加新动作
        IntentFilter discoveryFilter = new IntentFilter();
        discoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
        discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        discoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //注册蓝牙设备搜索的广播接收器
        registerReceiver(discoveryReceiver, discoveryFilter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelDiscovery();      //取消蓝牙设备的搜索
        //注销蓝牙设备搜索的广播接收器
        unregisterReceiver(discoveryReceiver);
    }

    //蓝牙设备的搜索结果通过广播返回
    private BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {  //发现新的蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "name=" + device.getName() + ",state=" + device.getBondState());
                refreshDevice(device, device.getBondState());   //将发现的蓝牙设备加入到设备列表中
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {     //搜索完毕
//                mHandler.removeCallbacks(mRefresh); //需要持续搜索就要注释这行
                tv_discovery.setText("蓝牙设备搜索完成");
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {//配对状态改变
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
                    tv_discovery.setText("正在配对" + device.getName());
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    tv_discovery.setText("完成配对" + device.getName());
                } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    tv_discovery.setText("取消配对" + device.getName());
                    refreshDevice(device, device.getBondState());
                }
            }
        }
    };

    //刷新蓝牙设备
    private void refreshDevice(BluetoothDevice device, int state) {
        int i;
        for (i = 0; i < mDeviceList.size(); i++) {
            BlueDevice item = mDeviceList.get(i);
            if (item.address.equals(device.getAddress())) {
                item.state = state;
                mDeviceList.set(i, item);
                break;
            }
        }
        if (i >= mDeviceList.size()) {
            mDeviceList.add(new BlueDevice(device.getName(), device.getAddress(), device.getBondState()));
        }
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        cancelDiscovery();
        BlueDevice item = mDeviceList.get(position);
        //根据设备地址获得远端的蓝牙设备
        BluetoothDevice device = mBluetooth.getRemoteDevice(item.address);
        Log.d(TAG, "getBindState" + device.getBondState() + ",item.state" + item.state);
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {       //尚未配对
            BluetoothUtil.createBond(device);       //创建配对信息
        } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {  //已经配对
            boolean isSuccess = BluetoothUtil.removeBond(device);       //移除配对信息
            if (!isSuccess) {
                refreshDevice(device, BluetoothDevice.BOND_NONE);
            }
        }
    }


}
