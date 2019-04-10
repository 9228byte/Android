package com.example.senior.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.view.ViewGroup.LayoutParams;
import android.support.v4.view.PagerAdapter;

import com.example.senior.bean.GoodsInfo;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/7
 */

public class ImagePaperAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<ImageView> mViewList = new ArrayList<ImageView>();
    private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();

    public ImagePaperAdapter(Context context, ArrayList<GoodsInfo> goods_list) {
        mContext = context;
        mGoodsList = goods_list;
        //为每一个商品分配一个专用的图像视图
        for (int i = 0; i < mGoodsList.size(); i++) {
            ImageView view = new ImageView(mContext);
            view.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            view.setImageResource(mGoodsList.get(i).pic);
            view.setScaleType(ScaleType.FIT_CENTER);
            //把该商品的图像视图添加到图像视图队列
            mViewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    //从容器中销毁指定位置的页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    //实例化指定位置的页面，将其添加到指定容器中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    //获得指定页面的标题文本，为PagerTitleStrip/PagerTabStrip翻页标题栏
    @Override
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }
}
