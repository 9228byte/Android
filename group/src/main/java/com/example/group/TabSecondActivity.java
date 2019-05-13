package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TabSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_second);
        String desc = String.format("我是%s页面，来自%s", "首页", getIntent().getExtras().get("tag"));
        TextView tv_second = findViewById(R.id.tv_second);
        tv_second.setText(desc);
    }
}
