package com.example.senior;

import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.senior.adapter.ImagePaperAdapter;
import com.example.senior.bean.GoodsInfo;

import java.util.ArrayList;

public class PagerTabStripActivity extends AppCompatActivity implements OnPageChangeListener{
    private ArrayList<GoodsInfo> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_strip);
        initPagerStrip();
        initViewPager();
    }

    private void initPagerStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_tab.setTextColor(Color.GREEN);
    }

    private void initViewPager() {
        goodsList = GoodsInfo.getDefaultList();
        ImagePaperAdapter adapter = new ImagePaperAdapter(this, goodsList);
        ViewPager vp_content = findViewById(R.id.vp_content);
        vp_content.setAdapter(adapter);
        vp_content.setCurrentItem(0);
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Toast.makeText(this, "您翻到的手机品牌是：" + goodsList.get(i).name,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
