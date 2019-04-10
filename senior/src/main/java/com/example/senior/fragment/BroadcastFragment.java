package com.example.senior.fragment;

import android.arch.lifecycle.LifecycleOwner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.senior.R;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/8
 */

public class BroadcastFragment extends Fragment {
    private static final String TAG = "BroadcastFraagment";
    //声明一个广播事件的标识串
    public final static String EVENT = "com.example.senior.fragment.BroadcastFragment";
    private View mView;
    protected Context mContext;
    private int mPosition;
    private int mImageId;
    private String mDesc;
    private int mColorSeq = 0;       //背景颜色的序号
    private Spinner sp_bg;
    private boolean bFirst = true;      //是否首次打开

    //获取碎片的一个实例
    public static BroadcastFragment newInstance(int position, int image_id, String desc) {
        BroadcastFragment fragment = new BroadcastFragment();       //创建该碎片的一个实例
        Bundle bundle = new Bundle();       //创建一个包裹
        bundle.putInt("position", position);
        bundle.putInt("image_id", image_id);
        bundle.putString("desc", desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    //创建碎片视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();       //获取上下文对象
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position", 0);
            mImageId = getArguments().getInt("image_id", 0);
            mDesc = getArguments().getString("desc");
        }
        mView = inflater.inflate(R.layout.fragment_broadcast, container, false);
        ImageView iv_pic = mView.findViewById(R.id.iv_pic);
        TextView tv_desc = mView.findViewById(R.id.tv_desc);
        iv_pic.setImageResource(mImageId);
        tv_desc.setText(mDesc);
        return mView;
    }

    private String[] mColorNameArray = {"红色", "黄色", "绿色", "青色", "蓝色"};

    //初始化页面背景色的下拉框
    private void initSpinner() {
        ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String>(mContext,
                R.layout.item_select, mColorNameArray);
        sp_bg  = mView.findViewById(R.id.sp_bg);
        sp_bg.setPrompt("请选择背景颜色");
        sp_bg.setAdapter(dividerAdapter);
        sp_bg.setSelection(0);
        sp_bg.setOnItemSelectedListener(new ColorSelectedListener());
    }

    private int[] mColorIdArray = {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE};

    class ColorSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (!bFirst || mColorSeq != position) {
                mColorSeq = position;
                //创建一个广播意图
                Intent intent = new Intent(BroadcastFragment.EVENT);
                intent.putExtra("seq", position);
                intent.putExtra("color", mColorIdArray[position]);
                //通过本地广播管理器来发送广播
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
            bFirst = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    //定义一个广播接收器，用于处理背景色变更事件
    private class BgChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                //从广播消息中取出最新的颜色序号
                mColorSeq = intent.getIntExtra("seq", 0);
                //设置下拉默认显示该序号
                sp_bg.setSelection(mColorSeq);
            }
        }
    }

    //声明一个北京颜色变更的广播接收器
    private BgChangeReceiver bgChangeReceiver;

    @Override
    public void onStart() {
        super.onStart();
        initSpinner();
        //创建一个背景色变更的广播接收器
        bgChangeReceiver = new BgChangeReceiver();
        //创建一个意图过滤器，只处理指定时间来源的广播
        IntentFilter filter = new IntentFilter(BroadcastFragment.EVENT);
        //注册广播接收器，注册之后才能正常接收广播
        LocalBroadcastManager.getInstance(mContext).registerReceiver(bgChangeReceiver, filter);
    }

    @Override
    public void onStop() {
        //注销广播接收器。注销之后就不再接收广播
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(bgChangeReceiver);
        super.onStop();
    }
}
