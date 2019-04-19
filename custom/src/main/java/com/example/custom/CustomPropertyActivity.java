package com.example.custom;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.custom.adapter.ImagePagerAdapter;
import com.example.custom.bean.GoodsInfo;

import java.util.ArrayList;

public class CustomPropertyActivity extends AppCompatActivity implements OnPageChangeListener {
    private ArrayList<GoodsInfo> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_property);
        goodsList = GoodsInfo.getDefaultList();
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, goodsList);
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
        Toast.makeText(this, "您翻到的手机品牌是：" + goodsList.get(i).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
