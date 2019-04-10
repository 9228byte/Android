package com.example.senior.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.senior.bean.GoodsInfo;
import com.example.senior.fragment.DynamicFragment;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/7
 */

public class MobilePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();

    //碎片页适配器的构造函数，传入碎片管理器与商品信息队列
    public MobilePagerAdapter(FragmentManager fm, ArrayList<GoodsInfo> goodsList) {
        super(fm);
        mGoodsList = goodsList;
    }

    //获取指定位置的碎片Fragment
    @Override
    public Fragment getItem(int i) {
        return DynamicFragment.newInstance(i,
                mGoodsList.get(i).pic, mGoodsList.get(i).desc);
    }

    //获取碎片Fragment的个数
    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }


}
