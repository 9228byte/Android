package com.example.senior;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.senior.adapter.ImagePaperAdapter;
import com.example.senior.bean.GoodsInfo;

import java.util.ArrayList;

/**
 *  Created by lao on 2019/4/7
 */


public class ViewPagerActivity extends AppCompatActivity implements OnPageChangeListener {
    private ArrayList<GoodsInfo> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        goodsList = GoodsInfo.getDefaultList();
        //构建一个商品图片的翻页适配器
        ImagePaperAdapter adapter = new ImagePaperAdapter(this, goodsList);
        //从布局文件中获取翻页视图
        ViewPager vp_content = findViewById(R.id.vp_content);
        //设置翻页视图的适配器
        vp_content.setAdapter(adapter);
        //选择当前项
        vp_content.setCurrentItem(0);
        //设置监听器
        vp_content.addOnPageChangeListener(this);
    }

    //在翻页过程中触发，该方法有三个参数取值说明为：第一个表示当前页面的序号
    //第二个参数表示当前页面偏移的百分比，第三个参数表示当前页面的偏移距离
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    //翻页结束后触发，表示滑到那一个界面
    @Override
    public void onPageSelected(int i) {

    }

    //翻页状态改变时触发，0表示静止，1表示正在滑动，2表示滑动完毕
    //翻页过程中，状态值变化为正在滑动-活动完毕-静止
    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
