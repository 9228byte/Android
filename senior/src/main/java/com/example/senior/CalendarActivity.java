package com.example.senior;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.senior.adapter.CalendarPagerAdapter;
import com.example.senior.util.DateUtil;
import com.example.senior.widget.MonthPicker;

public class CalendarActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "CalendarActivity";
    private LinearLayout ll_calendar_main;      //主要页面
    private LinearLayout ll_month_select;       //月份选择器，默认隐藏
    private MonthPicker mp_month;
    private ViewPager vp_calendar;
    private TextView tv_calendar;
    private boolean isShowSelect = false;   //是否显示月份选择器
    private int mSelectedYear = 2000;       //当前选中的月份

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ll_calendar_main = findViewById(R.id.ll_calendar_main);
        ll_month_select = findViewById(R.id.ll_month_select);
        mp_month = findViewById(R.id.mp_month);
        vp_calendar = findViewById(R.id.vp_calendar);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        PagerTabStrip pts_calendar = findViewById(R.id.pts_calendar);
        pts_calendar.setTextColor(Color.BLACK);
        pts_calendar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        vp_calendar =findViewById(R.id.vp_calendar);
        tv_calendar = findViewById(R.id.tv_calendar);
        tv_calendar.setOnClickListener(this);
        showCalendar(DateUtil.getNowYear(), DateUtil.getNowMonth());//显示目前系统日期
    }

    //显示万年历
    private void showCalendar(int year, int month) {
        if (year != mSelectedYear) {
            tv_calendar.setText(year + "年");
            //翻页视图适配器
            CalendarPagerAdapter adapter = new CalendarPagerAdapter(getSupportFragmentManager(), year);
            vp_calendar.setAdapter(adapter);
            mSelectedYear = year;
        }
        vp_calendar.setCurrentItem(month -1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_calendar) {
            //点击显示月份选择页面
            resetPage();
        } else if (v.getId() == R.id.btn_ok) {
            //点击确定刷新页面
            showCalendar(mp_month.getYear(), mp_month.getMonth() + 1);
            resetPage();
        }
    }

    private void resetPage() {
        isShowSelect = !isShowSelect;
        ll_calendar_main.setVisibility(isShowSelect ? View.GONE : View.VISIBLE);
        ll_month_select.setVisibility(isShowSelect ? View.VISIBLE : View.GONE);
    }
}
