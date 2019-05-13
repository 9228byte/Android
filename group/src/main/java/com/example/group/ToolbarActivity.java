package com.example.group;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;

/**
 * ToolbarActivity
 *
 * @author lao
 * @date 2019/4/29
 */

public class ToolbarActivity extends AppCompatActivity {
    private static final String TAG = "ToolbarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar tl_head = findViewById(R.id.tl_head);
        //设置工具栏左边导航图标
        tl_head.setNavigationIcon(R .drawable.ic_back);
        tl_head.setTitle("工具栏页面");
        tl_head.setTitleTextColor(Color.RED);
        //设置工具栏标志图片
        tl_head.setLogo(R.drawable.ic_app);
        tl_head.setSubtitle("Toolbar");
        tl_head.setSubtitleTextColor(Color.YELLOW);
        //设置工具栏背景
        tl_head.setBackgroundResource(R.color.blue_light);
        setSupportActionBar(tl_head);
        //给tl_heah设置导航图标的点击监听器
        //setNavigationOnClickListener必须放在setSupportActionBar之后，否则不起作用
        tl_head.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
