package com.example.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.example.custom.widget.CircleAnimation;

public class CircleAnimationActivity extends AppCompatActivity implements OnClickListener{
    private CircleAnimation mAnimation;         //定义一个圆弧动画对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_animation);
        findViewById(R.id.btn_play).setOnClickListener(this);
        LinearLayout ll_layout = findViewById(R.id.ll_layout);
        //创建一个圆弧动画
        mAnimation = new CircleAnimation(this);
//        mAnimation.setAngle(0, 360);
//        mAnimation.setRate(1, 2);
        //把圆弧动画添加到线性视图中
        ll_layout.addView(mAnimation);
        //渲染圆弧动画。渲染操作包括初始化与播放两个动作
        mAnimation.render();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play) {
            //开始播放圆弧动画
            mAnimation.play();
        }
    }
}
