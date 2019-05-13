package com.example.group;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.group.adapter.RecyclerCollapseAdapter;

/**
 * CollapseParallaxActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class CollapseParallaxActivity extends AppCompatActivity {
    private String[] yearArray = {"鼠年", "牛年", "虎年", "兔年", "龙年", "蛇年", "马年", "羊年", "猴年", "鸡年", "狗年", "猪年"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse_parallax);
        Toolbar tl_title = findViewById(R.id.tl_title);
        tl_title.setBackgroundColor(Color.RED);
        setSupportActionBar(tl_title);
        CollapsingToolbarLayout ctl_title = findViewById(R.id.ctl_title);
        ctl_title.setTitle(getString(R.string.toolbar_name));
        RecyclerView rv_main = findViewById(R.id.rv_main);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_main.setLayoutManager(llm);
        RecyclerCollapseAdapter adapter = new RecyclerCollapseAdapter(this, yearArray);
        rv_main.setAdapter(adapter);
    }
}
