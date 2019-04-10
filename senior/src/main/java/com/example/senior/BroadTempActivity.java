package com.example.senior;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.example.senior.adapter.BroadcastPagerAdapter;
import com.example.senior.bean.GoodsInfo;
import com.example.senior.fragment.BroadcastFragment;

import java.util.ArrayList;

/**
 *  Created by lao on 2019/4/8
 */


public class BroadTempActivity extends AppCompatActivity {
    private static final String TAG = "BroadTempAcativity";
    private LinearLayout ll_brd_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_temp);
        ll_brd_temp = findViewById(R.id.ll_brd_temp);
        initPagerStrip();
        initViewPager();
    }

    //初始化翻页标题栏
    private void initPagerStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_tab.setTextColor(Color.BLACK);
    }

    //初始化翻页视图
    private void initViewPager() {
        ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();        //默认商品队列
        BroadcastPagerAdapter adapter = new BroadcastPagerAdapter(getSupportFragmentManager(), goodsList);
        ViewPager vp_content = findViewById(R.id.vp_content);
        vp_content.setAdapter(adapter);
        vp_content.setCurrentItem(0);
    }

    //声明一个背景色变更的广播接收器
    private BgChangeReceiver bgChangeReveiver;

    //定义一个广播接收器，用于处理背景色变更事件
    private class BgChangeReceiver extends BroadcastReceiver {
        //一旦接收到背景色变更的广播，马上触发接收器的onReceiver方法
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                //从广播消息中取出最新的颜色
                int color = intent.getIntExtra("color", android.graphics.Color.WHITE);
                //把页面背景色设置为广播发来的颜色
                ll_brd_temp.setBackgroundColor(color);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //创建一个背景色变更的广播接收器
        bgChangeReveiver = new BgChangeReceiver();
        //创建一个意图接收器，只处理指定事件来源的广播
        IntentFilter filter = new IntentFilter(BroadcastFragment.EVENT);
        //注册广播接收器，注册后才能正常接收广播
        LocalBroadcastManager.getInstance(this).registerReceiver(bgChangeReveiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销广播接收器，注销后就不能接收广播
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bgChangeReveiver);
    }
}
