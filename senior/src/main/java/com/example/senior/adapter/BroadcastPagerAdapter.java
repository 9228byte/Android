package com.example.senior.adapter;

import android.content.BroadcastReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ArrayAdapter;

import com.example.senior.bean.GoodsInfo;
import com.example.senior.fragment.BroadcastFragment;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/8
 */

public class BroadcastPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();

    public BroadcastPagerAdapter(FragmentManager fm, ArrayList<GoodsInfo> goodsList) {
        super(fm);
        mGoodsList = goodsList;
    }

    @Override
    public Fragment getItem(int i) {
        return BroadcastFragment.newInstance(i, mGoodsList.get(i).pic, mGoodsList.get(i).desc);
    }

    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {        //设置翻页标题
        return mGoodsList.get(position).desc;
    }
}
