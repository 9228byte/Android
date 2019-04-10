package com.example.senior;

import android.graphics.Color;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.senior.adapter.ImagePaperAdapter;
import com.example.senior.bean.GoodsInfo;

import java.util.ArrayList;

public class PagerTitleStripActivity extends AppCompatActivity implements OnPageChangeListener{
    private ArrayList<GoodsInfo> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_title_strip);
        initPagerStrip();       //初始化翻页标题栏
        initViewPager();        //初始化翻页视图
    }

    //初始化翻页标题栏
    private void initPagerStrip() {
        PagerTitleStrip pts_title = findViewById(R.id.pts_title);
        //设置标题栏文本大小
        pts_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        //设置翻页标题栏的文本颜色
        pts_title.setTextColor(Color.BLUE);
    }

    //初始化翻页视图
    private void initViewPager() {
        goodsList = GoodsInfo.getDefaultList();
        ImagePaperAdapter adapter = new ImagePaperAdapter(this, goodsList);
        ViewPager vp_content = findViewById(R.id.vp_content);
        vp_content.setAdapter(adapter);
        vp_content.setCurrentItem(0);
        //添加页面变化监听器
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Toast.makeText(this, "您翻到的手机品牌是" + goodsList.get(i).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
