package com.example.middle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.middle.Util.Utils;

public class FrameActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout fl_content;     //声明一个框架布局对象
    private int[] mColorArray = {
            Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN,
            Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        fl_content = findViewById(R.id.fl_content);
        findViewById(R.id.btn_add_frame).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_frame) {
            int random = (int)(Math.random() * 10 % 10);
            //创建一个新的视图对象
            View vv = new View(this);
            vv.setBackgroundColor(mColorArray[random]);
            //声明一个布局参数，其中宽度与上级持平，高度随机
            LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(this ,(random + 1) * 30));
            //给该视图设置布局参数
            vv.setLayoutParams(ll_params);
            //设置该视图长按监听器
            vv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View vvv) {
                    //一旦监听到长按事件，就从相对布局中删除视图
                    fl_content.removeView(vvv);
                    return true;
                }
            });
            //往框架布局中添加该视图
            fl_content.addView(vv);
        }
    }
}
