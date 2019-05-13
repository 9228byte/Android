package com.example.group.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.group.fragment.AppliancesFragment;
import com.example.group.fragment.ClothesFragment;

import java.util.ArrayList;


/**
 * ClassPagerAdater
 *
 * @author lao
 * @date 2019/5/13
 */
public class ClassPagerAdater extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public ClassPagerAdater(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ClothesFragment();
        } else if (position == 1) {
            return new AppliancesFragment();
        }
        return new ClothesFragment();
    }

    @Override
    public int getCount() {
        return mTitleArray.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
