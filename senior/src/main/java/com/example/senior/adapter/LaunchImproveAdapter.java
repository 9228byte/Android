package com.example.senior.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.senior.fragment.LaunchFragment;

/**
 * Created by lao on 2019/4/8
 */

public class LaunchImproveAdapter extends FragmentStatePagerAdapter {
    private int[] mImageArray;

    //碎片页适配器构造函数，传入碎片管理器与图片数组
    public LaunchImproveAdapter(FragmentManager fm, int[] imageArray) {
        super(fm);
        mImageArray = imageArray;
    }

    //获取碎片Fragment的个数
    @Override
    public Fragment getItem(int i) {
        return LaunchFragment.newInstance(i, mImageArray[i]);
    }

    //获取指定位置的碎片Fragment
    @Override
    public int getCount() {
        return mImageArray.length;
    }
}
