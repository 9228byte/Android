package com.example.junior;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junior.util.DateUtil;

public class CaptureActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    private TextView tv_capture;        //文本视图
    private ImageView iv_capture;       //图像视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        tv_capture = findViewById(R.id.tv_capture);
        iv_capture = findViewById(R.id.iv_capture);
        //开启文本视图tv_capture的绘图缓存
        tv_capture.setDrawingCacheEnabled(true);
        Button btn_chat = findViewById(R.id.btn_chat);
        //给btn_chat设置点击监听器
        btn_chat.setOnClickListener(this);
        //给btn_caht设置长按监听器
        btn_chat.setOnLongClickListener(this);
        Button btn_capture = findViewById(R.id.btn_capture);
        //给btn_capture设置点击监听器
        btn_capture.setOnClickListener(this);
    }

    public  String[] mChatStr = {"你吃饭了吗？","今天天气真好呀！","我中奖了！","我们去看电影吧。","晚上干什么好呢？"};

    @Override
    public boolean onLongClick(View v) {
        //长按清空文本
        if (v.getId() == R.id.btn_chat) {
            tv_capture.setText("");
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_chat) {
            int random = (int)(Math.random()*10)%5;
            String newStr = String.format("%s\n%s %s",
                    tv_capture.getText().toString(), DateUtil.getNowTime(),mChatStr[random]);
            tv_capture.setText(newStr);
        } else if (v.getId() == R.id.btn_capture) {
            //从文本视图tv_capture的绘图 缓存中获取位图对象
            Bitmap bitmap = tv_capture.getDrawingCache();
            //给图像视图iv_capture设置位图图像
            iv_capture.setImageBitmap(bitmap);
            //这里截图完毕后不能马上关闭绘图缓存，因为换面渲染需要时间
            mHandler.postDelayed(mResetCache, 200);
        }
    }

    private Handler mHandler = new Handler();       //声明一个任务管理器
    private  Runnable mResetCache = new Runnable() {
        @Override
        public void run() {
            //关闭文本视图tv_capture的绘图缓存
            tv_capture.setDrawingCacheEnabled(false);
            //开启文本视图tv_capture的绘图缓存
            tv_capture.setDrawingCacheEnabled(true);
        }
    };
}
