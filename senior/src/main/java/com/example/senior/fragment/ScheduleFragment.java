package com.example.senior.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.senior.R;
import com.example.senior.ScheduleActivity;
import com.example.senior.adapter.CalendarGridAdapter;
import com.example.senior.adapter.ScheduleListAdapter;
import com.example.senior.bean.CalendarTransfer;
import com.example.senior.bean.ScheduleArrange;
import com.example.senior.calendar.Constant;
import com.example.senior.calendar.SpecialCalendar;
import com.example.senior.database.DbHelper;
import com.example.senior.database.ScheduleArrangeHelper;
import com.example.senior.util.DateUtil;

import java.util.ArrayList;


/**
 * ScheduleFragment
 *
 * @author lao
 * @date 2019/4/13
 */
public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    protected View mView;
    protected Context mContext;
    private int mSelectedWeek, mNowWeek;    //当前选择周数，以及今天所处的周数
    private ListView lv_schedule;       //声明一个列表视图对象
    private int mYear, mMonth, mDay;        //当前选择周数的星期一对应的年月日
    private int first_pos = 0;      //当前选择周数的星期一在该月日历中的具体日期
    private String thisDate;    //当前选择周数的星期一的具体日期
    private ArrayList<CalendarTransfer> transArray  = new ArrayList<CalendarTransfer>();
    private ScheduleArrangeHelper mArrangeHelper;     //声明一个日程安排的数据库帮助器

    public static ScheduleFragment newInstance(int week) {
        Bundle bundle = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        bundle.putInt("week", week);
        fragment.setArguments(bundle);
        return fragment;
    }

    //创建碎片视图页

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (getArguments() != null) {
            mSelectedWeek = getArguments().getInt("week", 1);
        }
        //获取今天所处的周数
        mNowWeek = SpecialCalendar.getTodayWeek();
        initDate(mSelectedWeek - mNowWeek);
        //根据年月日计算当周位于哪个日历网格适配器
        CalendarGridAdapter calV= new CalendarGridAdapter(mContext, mYear, mMonth, mDay);
        Log.d(TAG, "onCreateView: mYear=" + mYear + " mMonth=" + mMonth + " mDay=" + mDay);
        for (int i = first_pos; i < first_pos + 7 ; i++ ) {
            CalendarTransfer trans = calV.getCalendarList(i);
            transArray.add(trans);      //添加到日历转换队列中
        }
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        lv_schedule = mView.findViewById(R.id.lv_schedule);
        return mView;
    }

    //初始化当周的星期一对应的年月日
    private void initDate(int diff_weeks) {
        String nowDate = DateUtil.getNowDate();
        Log.d(TAG, "initDate: 现在日期nowDate="+ nowDate);
        thisDate = DateUtil.getAddDate(nowDate , diff_weeks * 7);
        Log.d(TAG, "initDate: thisDate=" + thisDate);
        int thisDay = Integer.valueOf(thisDate.substring(6, 8));
        int weekIndex = DateUtil.getWeekIndex(thisDate);
        int week_count = (int) Math.ceil((thisDay - weekIndex + 0.5) / 7.0);
        if ((thisDay - weekIndex) % 7 >0) {     //需要计算当天所在周是当月的第几周
            week_count++;
        }
        if (thisDay - weekIndex < 0) {
            week_count++;
        }
        first_pos = week_count * 7;
        mYear = Integer.parseInt(thisDate.substring(0, 4));
        mMonth = Integer.parseInt(thisDate.substring(4, 6));
        mDay = Integer.parseInt(thisDate.substring(6, 8));
        Log.d(TAG, "initDate:月 mMonth=" + mMonth );
    }

    //检查当周的七天是否存在特殊节日
    private void checkFestival() {
        int i = 0;
        for (; i < transArray.size(); i++) {
            CalendarTransfer trans = transArray.get(i);
            int j = 0;
            for (; j < Constant.festivalArray.length; j++) {
                if (trans.day_name.contains(Constant.festivalArray[j])) {
                    //找到了特殊节日，则发送该节日的图片guangbo
                    sendFestival(Constant.festivalResArray[j]);
                    break;
                }
            }
            if (j < Constant.festivalArray.length) {
                break;
            }
        }
        //未找到特殊节日，则发送日常图片的广播
        if (i >= transArray.size()) {
            sendFestival(R.drawable.normal_day);
        }
    }

    //把图片发编号通过广播发送出去
    private void sendFestival(int resid) {
        Intent intent = new Intent(ScheduleActivity.ACTION_SHOW_FESTIVAL);
        intent.putExtra(ScheduleActivity.EXTRA_FESTIVAL_RES, resid);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private class ScrollControlReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int selectedWeek = intent.getIntExtra(ScheduleActivity.EXTRA_FESTIVAL_RES, 1);
                if (mSelectedWeek == selectedWeek) {
                    checkFestival();
                }
            }
        }
    }

    private ScrollControlReceiver scrollControlReceiver;

    @Override
    public void onStart() {
        super.onStart();
        scrollControlReceiver = new ScrollControlReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(scrollControlReceiver, new IntentFilter(ScheduleActivity.ACTION_FRAGMENT_SELECTED));
        mArrangeHelper = new ScheduleArrangeHelper(mContext, DbHelper.db_name, null, 1);
        CalendarTransfer begin_trans = transArray.get(0);
        String begin_day = String.format("%s%02d%02d", begin_trans.solar_year, begin_trans.solar_month, begin_trans.solar_day);
        CalendarTransfer end_trans = transArray.get(transArray.size() - 1);
        String end_day = String.format("%s%02d%02d", end_trans.solar_year, end_trans.solar_month, end_trans.solar_day);
        ArrayList<ScheduleArrange> arrangeList = (ArrayList<ScheduleArrange>) mArrangeHelper.queryInfoByDayRange(begin_day, end_day);
        ScheduleListAdapter listAdapter = new ScheduleListAdapter(mContext, transArray, arrangeList);
        lv_schedule.setAdapter(listAdapter);
        lv_schedule.setOnItemClickListener(listAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(scrollControlReceiver);
        mArrangeHelper.close();
    }
}
