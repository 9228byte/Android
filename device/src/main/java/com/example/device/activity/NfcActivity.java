package com.example.device.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.device.R;
import com.example.device.nfc.BusCard;
import com.example.device.nfc.ByteArrayChange;


/**
 * NfcActivity
 *
 * @author lao
 * @date 2019/9/8
 */

public class NfcActivity extends Activity {
    private static final String TAG = "NfcActivity";
    private TextView tv_nfc_result;
    private NfcAdapter mNfcAdapt;
    private RadioButton rb_guard_card, rb_bus_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        tv_nfc_result = findViewById(R.id.tv_nfc_result);
        rb_guard_card = findViewById(R.id.rb_guard_card);
        rb_bus_card = findViewById(R.id.rb_bus_card);
        initNFC();
    }

    private void initNFC() {
        mNfcAdapt = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapt == null) {
            tv_nfc_result.setText("当前手机不支持NFC");
        } else if (!mNfcAdapt.isEnabled()) {
            tv_nfc_result.setText("请先在设置中启用NFC功能");
        } else {
            tv_nfc_result.setText("当前手机支持NFC");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapt == null || !mNfcAdapt.isEnabled()) {
            return;
        }
        //探测NFC卡片后,必须以FLAG_ACTIVITY_SINGLE_TOP方式启动Activity,
        //或者在AndroidManifest.xml中设置LauchMode为singleTop或者singleTask,
        //保证无论NFC标签靠近手机多少次，Activity实例都只有一个
        Intent intent = new Intent(this, NfcActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //读取标签之前先缺洞标签类型。这里以大多数的NFC为例
        String[][] techLists = new String[][]{new String[]{NfcA.class.getName()}, {IsoDep.class.getName()}};
        try {
            //定义一个锅炉器（检测NFC卡片）
            IntentFilter[] filters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED, "*/*")};
            //为本App启动NFC感应
            mNfcAdapt.enableForegroundDispatch(this, pendingIntent, filters, techLists);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapt == null || !mNfcAdapt.isEnabled()) {
            return;
        }
        //禁用本App的NFC感应
        mNfcAdapt.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction(); //获取到本次启动的action
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) || action.equals(NfcAdapter.ACTION_TECH_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            //从intent中读取NFC卡片内容
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //获取NFC卡片的序列号
            byte[] ids = tag.getId();
            String card_info = String.format("卡片的序列号为：%s", ByteArrayChange.ByteArrayToHexString(ids));
            if (rb_guard_card.isChecked()) {
                String result = readGuardCard(tag);
                card_info = String.format("%s\n详细信息如下：\n%s", card_info, result);
                tv_nfc_result.setText(card_info);
            } else if (rb_bus_card.isChecked()) {
                String result = readBusCard(tag);
                card_info = String.format("%s\n详细信息如下：\n%s", card_info, result);
                tv_nfc_result.setText(card_info);
            }

        }
    }

    //读取小区门禁卡
    public String readGuardCard(Tag tag) {
        MifareClassic classic = MifareClassic.get(tag);
        for (String tech : tag.getTechList()) {
            Log.d(TAG, "当前设备支持" + tech);        //显示设备支持技术
        }
        String info;
        try {
            classic.connect();      //连接卡片数据
            int type  = classic.getType();      //获取TAG的数据类型
            String typeDesc;
            if (type == MifareClassic.TYPE_CLASSIC) {
                typeDesc = "传统类型";
            } else if (type == MifareClassic.TYPE_PLUS) {
                typeDesc = "增强类型";
            } else if (type == MifareClassic.TYPE_PRO) {
                typeDesc = "专业类型";
            } else {
                typeDesc = "未知类型";
            }
            info = String.format("\t卡片类型：%s\n\t扇区数量：%d\n\t分块个数：%d\n\t存储空间：%d字节",
                    typeDesc, classic.getSectorCount(), classic.getBlockCount(), classic.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            info = e.getMessage();
        } finally {     //无论是否发证异常，都要释放资源
            try {
                classic.close();        //释放卡片数据
            } catch (Exception e) {
                e.printStackTrace();
                info = e.getMessage();
            }
        }
        return info;
    }

    //读取北京一卡通
    public String readBusCard(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            return BusCard.parser(isoDep);
        } else {
            return "";
        }
    }

}
