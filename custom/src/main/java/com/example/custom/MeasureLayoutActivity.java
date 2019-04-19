package com.example.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.custom.util.MeasureUtil;

public class MeasureLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_layout);
        LinearLayout ll_head = findViewById(R.id.ll_header);
        TextView tv_desc = findViewById(R.id.tv_desc);
        float height = MeasureUtil.getRealHeight(ll_head);
        String desc = String.format("上面下拉刷新头部的高度是%f", height);
        tv_desc.setText(desc);
    }
}
