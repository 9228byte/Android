package com.example.senior.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.senior.bean.CalendarTransfer;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/10
 */

public class CalendarGridAdapter extends BaseAdapter {
    private static final String TAG = "CanlendarGridAdapter";
    private Context mContext;
    private boolean isLeapyear = false;
    private int daysOfMonth = 0;
    private int dayOfWeek = 0;
    private int lastDaysOfMonth = 0;
    private String[] dayNumber = new String[49];
    private ArrayList<CalendarTransfer> transArray = new ArrayList<CalendarTransfer>();
    private static String[] weekTitle = {"周一", "周二", "周三" , "周四", "周五", "周六", "周日"};
    private LunarCalender lc;
    private int currentDay = -1;

    public CalendarGridAdapter(Context context, int year, int month, int day) {
        mContext = month;
        lc = new LunarCalendar();
        Log.d(TAG, "CalendarGridAdapter: " );
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
