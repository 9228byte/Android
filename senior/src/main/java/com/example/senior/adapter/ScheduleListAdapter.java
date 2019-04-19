package com.example.senior.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.senior.R;
import com.example.senior.ScheduleDetailActivity;
import com.example.senior.bean.CalendarTransfer;
import com.example.senior.bean.ScheduleArrange;
import com.example.senior.calendar.Constant;
import com.example.senior.calendar.LunarCalendar;
import com.example.senior.util.DateUtil;

import java.util.ArrayList;


/**
 * ScheduleListAdapter
 *
 * @author lao
 * @date 2019/4/13
 */
public class ScheduleListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private static final String TAG = "ScheduleListAdapter";
    private Context mContext;
    private ArrayList<CalendarTransfer> mTranArray = new ArrayList<CalendarTransfer>();
    private ArrayList<ScheduleArrange> mArrangeList = new ArrayList<ScheduleArrange>();

    public ScheduleListAdapter(Context context, ArrayList<CalendarTransfer> tranArray,
                               ArrayList<ScheduleArrange> arrangeList) {
        mContext = context;
        mTranArray = tranArray;
        mArrangeList = arrangeList;
    }

    @Override
    public int getCount() {
        return mTranArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mTranArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_schedule, null);
            holder.week_number = convertView.findViewById(R.id.week_number);
            holder.week_schedule = convertView.findViewById(R.id.week_shedule);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.week_number.setText(Constant.weekArray[position]);
        if (position < 5) {
            holder.week_number.setTextColor(Color.BLACK);
        } else {
            holder.week_number.setTextColor(Color.RED);
        }
        CalendarTransfer trans = mTranArray.get(position);
        String day = String.format("%s%02d%02d", trans.solar_year, trans.solar_month, trans.solar_day);
        String arrangeTitle = "";
        int i = 0;
        for (; i < mArrangeList.size(); i++) {
            if (mArrangeList.get(i).day.equals(day)) {
                ScheduleArrange item = mArrangeList.get(i);
                //拼接当前的日程安排标题
                arrangeTitle = String.format("%s时%s分:%s", item.hour, item.minute, item.title);
                break;
            }
        }
        if (i >= mArrangeList.size()) {
            arrangeTitle = "今日暂无日程安排";
        }
        //拼接公历的日期和文字
        String solar_date = String.format("%s月%s日", trans.solar_month, trans.solar_day);
        //拼接农历的日期和文字
        String lunar_date = String.format("农历%s月%s日", LunarCalendar.chineseNumber[trans.lunar_month - 1], LunarCalendar.getChinaDayString(trans.lunar_day));
        String holiday = "";
        //判断当前是否为特殊日子
        if (DateUtil.isHoliday(trans.day_name)) {
            holiday = trans.day_name;
        }
        //拼接当前天完整的日期描述
        String content = String.format("%s %s %s\n%s", solar_date, lunar_date, holiday, arrangeTitle);
        holder.week_schedule.setText(content);
        if (day.equals(DateUtil.getNowDate())) {
            holder.week_schedule.setTextColor(Color.BLUE);
        } else {
            holder.week_schedule.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: position=" + position);
        CalendarTransfer trans = mTranArray.get(position);
        String day = String.format("%s%02d%02d", trans.solar_year, trans.solar_month, trans.solar_day);
        String solar_date = String.format("%s年,%d月%d日",trans.solar_year, trans.solar_month, trans.solar_day);
        String lunar_date = String.format("农历%s月%s日",LunarCalendar.chineseNumber[trans.lunar_month - 1], LunarCalendar.getChinaDayString(trans.lunar_day));
        String holiday = "";
        if (DateUtil.isHoliday(trans.day_name)) {
            holiday = trans.day_name;
        }
        //跳转到当天的日程详情页面
        Intent intent = new Intent(mContext, ScheduleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("day", day);
        bundle.putString("solar_date", solar_date);
        bundle.putString("lunar_date", lunar_date);
        bundle.putString("week", Constant.weekArray[position]);
        bundle.putString("holiday", holiday);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public final class ViewHolder {
        public TextView week_number;
        public TextView week_schedule;
    }
}
