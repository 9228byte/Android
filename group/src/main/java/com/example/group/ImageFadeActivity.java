package com.example.group;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.group.adapter.RecyclerCollapseAdapter;

/**
 * ImageFadeActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class ImageFadeActivity extends AppCompatActivity {
    private String[] yearArray = {"鼠年", "牛年", "虎年", "兔年", "龙年", "蛇年", "马年", "羊年", "猴年", "鸡年", "狗年", "猪年"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fade);
        Toolbar tl_title = findViewById(R.id.tl_title);
        setSupportActionBar(tl_title);
        CollapsingToolbarLayout ctl_title = findViewById(R.id.ctl_title);
        ctl_title.setTitle(getString(R.string.toolbar_name));
        ctl_title.setExpandedTitleColor(Color.BLACK);
        ctl_title.setCollapsedTitleTextColor(Color.RED);
        RecyclerView rv_mian = findViewById(R.id.rv_main);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_mian.setLayoutManager(llm);
        RecyclerCollapseAdapter adapter = new RecyclerCollapseAdapter(this, yearArray);
        rv_mian.setAdapter(adapter);
    }
}
