package com.example.senior.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.senior.calendar.Constant;
import com.example.senior.fragment.ScheduleFragment;


/**
 * SchedulePagerAdapter
 *
 * @author lao
 * @date 2019/4/13
 */
public class SchedulePagerAdapter extends FragmentStatePagerAdapter {

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 52;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String("第" + Constant.xuhaoArray[position + 1] + "周");
    }
}
