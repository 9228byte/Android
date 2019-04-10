package com.example.junior;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IconActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_icon;        //声明一个按钮对象
    private Drawable drawable;      //声明一个绘图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        btn_icon = findViewById(R.id.btn_icon);
        //从资源文件ic_launcher.png中获取图像
        drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //设置图像对象的矩形边界大小，必须设置图片大小，否则不会显示图片
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_top).setOnClickListener(this);
        findViewById(R.id.btn_bottom).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_left) {
            btn_icon.setCompoundDrawables(drawable, null, null, null);
        } else if (v.getId() == R.id.btn_top) {
            btn_icon.setCompoundDrawables(null, drawable, null, null);
        } else if (v.getId() == R.id.btn_right) {
            btn_icon.setCompoundDrawables(null, null, drawable, null);
        } else if (v.getId() == R.id.btn_bottom) {
            btn_icon.setCompoundDrawables(null, null, null, drawable);
        }
    }
}
