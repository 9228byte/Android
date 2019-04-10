package com.example.junior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ShapeActivity extends AppCompatActivity implements  View.OnClickListener{
    private View v_content;     //声明一个视图对象
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        v_content = findViewById(R.id.v_content);
        findViewById(R.id.btn_rect).setOnClickListener(this);
        findViewById(R.id.btn_oval).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rect) {
            //吧矩形形状设置为v_content的背景
            v_content.setBackgroundResource(R.drawable.shape_rect_gold);
        } else if (v.getId() == R.id.btn_oval) {
            //把椭圆形状设置为v_content的背景
            v_content.setBackgroundResource(R.drawable.shape_oval_rose);
        }
    }
}
