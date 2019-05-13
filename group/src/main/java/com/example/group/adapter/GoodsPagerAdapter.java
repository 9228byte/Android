package com.example.group.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.group.fragment.BookCoverFragment;
import com.example.group.fragment.BookDetailFragment;

import java.util.ArrayList;


/**
 * GoodsPagerAdapter
 *
 * @author lao
 * @date 2019/5/4
 */
public class GoodsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public GoodsPagerAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new BookCoverFragment(); //数据封面
        } else if (i == 1) {
            return new BookDetailFragment();    //数据详情
        }
        return new BookCoverFragment();
    }

    @Override
    public int getCount() {
        return mTitleArray.size();
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return mTitleArray.get(i);
    }
}
