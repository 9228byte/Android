package com.example.senior.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
    @Override
    public Fragment getItem(int i) {
        return CalendarFragment.newInstance(mYear, i +1);
    }

    @Override
    public int getCount() {
        return 12;
    }
}
