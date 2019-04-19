package com.example.senior;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.senior.adapter.SchedulePagerAdapter;
import com.example.senior.calendar.SpecialCalendar;
import com.example.senior.util.DateUtil;

public class ScheduleActivity extends AppCompatActivity {
    private final static String TAG = "ScheduleActivity";
    //碎片选中事件的标识串
    public static String ACTION_FRAGMENT_SELECTED = "com.example.senior.ACTION.FRAGMENT_SELECTED";
    //选择星期参数的标识串
    public static String EXTRA_SELECTED_WEEK = "selected_week";
    //显示节日事件的标识串
    public static String ACTION_SHOW_FESTIVAL = "com.example.senior.ACTION_SHOW_FESTIVAL";
    //节日图片参数标识串
    public static String EXTRA_FESTIVAL_RES = "festival_res";
    private LinearLayout ll_schedule;
    private ViewPager vp_schedule;
    private int mSelectedWeek;      //当前选中的一个星期
    private int mFestivalResid = 0;     //节日图片资源编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        PagerTabStrip pts_schedule = findViewById(R.id.pts_schedule);
        pts_schedule.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        pts_schedule.setTextColor(Color.BLACK);
        ll_schedule = findViewById(R.id.ll_schedule);
        vp_schedule = findViewById(R.id.vp_schedule);
        TextView tv_schedule = findViewById(R.id.tv_schedule);
        tv_schedule.setText(DateUtil.getNowYearCN() + " 日程安排");
        mSelectedWeek = SpecialCalendar.getTodayWeek();
        SchedulePagerAdapter adapter = new SchedulePagerAdapter(getSupportFragmentManager());
        vp_schedule.setAdapter(adapter);
        vp_schedule.setCurrentItem(mSelectedWeek -1);
        vp_schedule.addOnPageChangeListener(new ScheduleChangeListener());
        //延迟50毫秒执行任务mFirst
        mHandler.postDelayed(mFirst, 50);
    }

    private Handler mHandler = new Handler();

    private Runnable mFirst = new Runnable() {
        @Override
        public void run() {
            sendBroadcast(mSelectedWeek);       //发送广播，表示当期是在第几个星期
        }
    };

    //发送当前周数广播
    private void sendBroadcast(int week) {
        Intent intent = new Intent(ACTION_FRAGMENT_SELECTED);
        intent.putExtra(EXTRA_SELECTED_WEEK, week);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        festivalReciver = new FestivalContralReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(festivalReciver, new IntentFilter(ACTION_SHOW_FESTIVAL));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(festivalReciver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFestivalResid != 0) {
            //在横屏和竖屏切换时不会重新onCreate，只会onReseme
            ll_schedule.setBackgroundResource(mFestivalResid);
        }
    }

    //声明一个节日图片的广播接收器
    private FestivalContralReceiver festivalReciver;

    private class FestivalContralReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                //从广播中取出节日资源编号
                mFestivalResid = intent.getIntExtra(EXTRA_FESTIVAL_RES, 1);
                ll_schedule.setBackgroundResource(mFestivalResid);
            }
        }
    }

    //定义一个页面变化监听器,用于处理翻页视图的翻页事件
    public class ScheduleChangeListener implements ViewPager.OnPageChangeListener {

        //翻页过程中触发
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        //页面结束后
        @Override
        public void onPageSelected(int i) {
            Log.d(TAG, "onPageSelected: position=" + i + ",mSelectedWeek=" + mSelectedWeek);
            mSelectedWeek = i + 1;
            sendBroadcast(mSelectedWeek);
        }

        //翻页状态改变时触发
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
