package com.example.custom;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.custom.adapter.TrafficInfoAdapter;
import com.example.custom.bean.AppInfo;
import com.example.custom.service.TrafficService;
import com.example.custom.util.AppUtil;
import com.example.custom.util.DateUtil;
import com.example.custom.util.SharedUtil;
import com.example.custom.util.StringUtil;
import com.example.custom.widget.CircleAnimation;
import com.example.custom.widget.CustomDateDialog;
import com.example.custom.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MobileAssistantActivity extends AppCompatActivity implements View.OnClickListener, CustomDateDialog.OnDateSetListener {
    private static final String TAG = "MobileAssistantActivity";
    private TextView tv_day;
    private RelativeLayout rl_month;
    private TextView tv_month_traffic;
    private RelativeLayout rl_day;
    private TextView tv_day_traffic;
    private NoScrollListView nslv_traffic;
    private int mDay;
    private int mNowDay;
    private long traffic_month = 0;
    private long traffic_day = 0;
    private int limit_month;
    private int limit_day;
    private int line_width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_assistant);
        Intent intent = new Intent(this, TrafficService.class);
        startService(intent);
        initView();
    }

    //初始化个视图对象
    private void initView() {
        Log.d(TAG, "initView: ");
        tv_day = findViewById(R.id.tv_day);
        rl_month = findViewById(R.id.rl_month);
        tv_month_traffic = findViewById(R.id.tv_month_traffic);
        rl_day = findViewById(R.id.rl_day);
        tv_day_traffic = findViewById(R.id.tv_day_traffic);
        nslv_traffic = findViewById(R.id.nslv_traffic);
        findViewById(R.id.iv_menu).setOnClickListener(this);
        findViewById(R.id.iv_refresh).setOnClickListener(this);
        limit_month = SharedUtil.getIntance(this).readInt("limit_month", 1024);
        limit_day = SharedUtil.getIntance(this).readInt("limit_day", 30);
        mNowDay = Integer.parseInt(DateUtil.getNowDateTime("yyyyMMdd"));
        mDay = mNowDay;
        String day = DateUtil.getNowDateTime("yyyy年MM月dd日");
        tv_day.setText(day);
        tv_day.setOnClickListener(this);
        //延迟50毫秒刷新流量数据
        mHandler.postDelayed(mDayRefresh, 500);
    }

    private Handler mHandler = new Handler();

    private Runnable mDayRefresh = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: ");
            refreshTraffic(mDay);
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_day) {
            //自定义选择日期
            Calendar calendar = Calendar.getInstance();
            CustomDateDialog dialog = new CustomDateDialog(this);
            dialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
            dialog.show();
        } else if (v.getId() == R.id.iv_menu) {
            //跳转到流量限额配置页面
            Intent intent = new Intent(this, MobileConfigActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.iv_refresh) {
            mHandler.post(mDayRefresh);     //立即刷新
            Toast.makeText(this, "立即刷新", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        String date = String.format("%d年%d月%d日", year, month, day);
        tv_day.setText(date);
        mDay = year * 10000 + month * 100 + day;
        mHandler.post(mDayRefresh);
    }

    //刷新指定日期的流量数据
    private void refreshTraffic(int day) {
        String last_date = DateUtil.getAddDate(""+ day,  -1);
        //截止昨天应用流量
        ArrayList<AppInfo> lastArray = MainApplication.getInstance().mTrafficHelper.query("day=" + last_date);
        //查询截止今日的应用流量
        ArrayList<AppInfo> thisArray = MainApplication.getInstance().mTrafficHelper.query("day="+ day);
        ArrayList<AppInfo> newArray = new ArrayList<>();
        traffic_day = 0;
        Log.d(TAG, "refreshTraffic: thisArray=" + thisArray.size());
        for (int i = 0; i < thisArray.size(); i++) {
            AppInfo item = thisArray.get(i);
            for (int j = 0; j < lastArray.size(); j ++){
                if (item.uid == lastArray.get(j).uid) {
                    item.traffic -= lastArray.get(j).traffic;
                    break;
                }
            }
            traffic_day += item.traffic;
            newArray.add(item);
        }
        ArrayList<AppInfo> fullArray = AppUtil.fillAppInfo(this, newArray);
        TrafficInfoAdapter adapter = new TrafficInfoAdapter(MobileAssistantActivity.this, fullArray);
        nslv_traffic.setAdapter(adapter);
        showDayAnimation();
        showMonthAnimation();
    }

    //显示流量圆弧动画
    private void showDayAnimation() {
        rl_day.removeAllViews();
        int diameter = Math.min(rl_day.getWidth(), rl_day.getHeight()) - line_width * 2;
        String desc= "今日已用流量" + StringUtil.formatData(traffic_day);
        CircleAnimation dayAnimation = new CircleAnimation(MobileAssistantActivity.this);
        dayAnimation.setRect((rl_day.getWidth() - diameter) / 2 + line_width,
                (rl_day.getHeight() - diameter) / 2 + line_width,
                (rl_day.getWidth() + diameter) / 2 - line_width,
                (rl_day.getHeight() + diameter) /2  - line_width);
        float trafficM = traffic_day / 1024.0f /1024.0f;
        if (trafficM > limit_day * 2) {
            int end_angle = (int) ((trafficM > limit_day * 3) ? 360 : (trafficM - limit_day * 2) * 360 / limit_day);
            dayAnimation.setAngle(0, end_angle);;
            dayAnimation.setFront(0xffff9900, line_width, Paint.Style.STROKE);
            desc = String.format("%s\n超出限额%s", desc ,StringUtil.formatData(traffic_day - limit_day * 1024 * 1024));
        } else if (trafficM > trafficM) {
            int end_angle = (int) ((trafficM > limit_day * 2) ? 360 : (trafficM - limit_day * 2) * 360 / limit_day);
            dayAnimation.setAngle(0, end_angle);;
            dayAnimation.setFront(0xffff9900, line_width, Paint.Style.STROKE);
            desc = String.format("%s\n超出限额%s", desc ,StringUtil.formatData(traffic_day - limit_day * 1024 * 1024));
        } else {
            int end_angle = (int) (trafficM * 360 /limit_day);
            dayAnimation.setAngle(0, end_angle);;
            dayAnimation.setFront(0xffff9900, line_width, Paint.Style.STROKE);
            desc = String.format("%s\n超出限额%s", desc ,StringUtil.formatData(limit_day * 1024 * 1024 - traffic_day));
        }
        rl_day.addView(dayAnimation);
        dayAnimation.render();
        tv_day_traffic.setText(desc);
    }

    private void showMonthAnimation() {
        rl_month.removeAllViews();
        int diameter = Math.min(rl_month.getWidth(), rl_month.getHeight()) - line_width * 2;
        Log.d(TAG, "showMonthAnimation: diameter" + diameter);
        tv_month_traffic.setText("本月已用流量待统计");
        CircleAnimation monthAnimation = new CircleAnimation(MobileAssistantActivity.this);
        monthAnimation.setRect((rl_month.getWidth() - diameter) / 2 + line_width,
                (rl_month.getHeight() - diameter) / 2 + line_width,
                (rl_month.getWidth() + diameter) / 2 - line_width,
                (rl_month.getHeight() + diameter) / 2 - line_width);
        monthAnimation.setAngle(0 ,0);
        monthAnimation.setFront(Color.GREEN, line_width, Paint.Style.STROKE);
        rl_month.addView(monthAnimation);
        monthAnimation.render();
    }
}
