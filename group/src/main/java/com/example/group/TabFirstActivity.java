package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TabFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_first);
        String desc = String.format("我是%s页面，来自%s", "首页", getIntent().getExtras().get("tag"));
        TextView tv_first = findViewById(R.id.tv_first);
        tv_first.setText(desc);
    }
}
