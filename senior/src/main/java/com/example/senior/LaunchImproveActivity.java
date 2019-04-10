package com.example.senior;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.senior.adapter.LaunchImproveAdapter;

/**
 *  Created by lao on 2019/4/8
 */


public class LaunchImproveActivity extends AppCompatActivity {
    private int[] launchImageArray = {R.drawable.guide_bg1,
            R.drawable.guide_bg2, R.drawable.guide_bg3, R.drawable.guide_bg4};      //导航页图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ViewPager vp_launch = findViewById(R.id.vp_launch);
        //传入碎片管理器，图片数组
        LaunchImproveAdapter adapter = new LaunchImproveAdapter(getSupportFragmentManager(), launchImageArray);
        vp_launch.setAdapter(adapter);
        vp_launch.setCurrentItem(0);
    }
}
