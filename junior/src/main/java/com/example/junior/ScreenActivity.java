package com.example.junior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.junior.util.Utils;

public class ScreenActivity extends AppCompatActivity {
    private TextView tv_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        tv_screen = findViewById(R.id.tv_screen);
        showScreenInfo();
    }

    //显示当前手机屏幕参数信息
    private void showScreenInfo(){
        //获取屏幕宽度
        int width = Utils.getScreenWidth(this);
        //获取屏幕高度
        int height = Utils.getScreenHeight(this);
        //获取屏幕像素密度
        float density = Utils.getScreenDensity(this);
        String info = String.format("当前屏幕的宽度是：%dpx，高度是：%dpx，像素密度是：%f",width,height,density);
        tv_screen.setText(info);
    }
}
