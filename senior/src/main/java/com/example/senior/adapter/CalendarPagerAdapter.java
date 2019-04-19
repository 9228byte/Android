package com.example.senior.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.senior.calendar.Constant;
import com.example.senior.fragment.CalendarFragment;


/**
 * Created by lao on 2019/4/10
 */

public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    private int mYear;

    public CalendarPagerAdapter(FragmentManager fm, int year) {
        super(fm);
        mYear = year;
    }

    //获取指定月份的碎片视图
    @Override
    public Fragment getItem(int position) {
        //返回碎片管理器实例视图
        return CalendarFragment.newInstance(mYear, position + 1);
    }

    //获取碎片的个数
    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String(Constant.xuhaoArray[position + 1] + "月");
    }
}
