package com.example.network.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.network.R;
import com.example.network.util.DateUtil;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_message;
    private boolean isPlaying = false;
    private int BEGIN = 0, SCROLL = 1, END = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        tv_message = findViewById(R.id.tv_message);
        tv_message.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        tv_message.setLines(8);
        tv_message.setMaxLines(8);
        //设置内部文本移动方式为滚动形式
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.btn_start_message).setOnClickListener(this);
        findViewById(R.id.btn_stop_message).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start_message) {
            if (!isPlaying) {
                isPlaying = true;
                new PlayThread().start();
            }
        } else if (v.getId() == R.id.btn_stop_message) {
            isPlaying = false;
        }
    }

    private String[] mNewsArray = { "北斗三号卫星发射成功，定位精度媲美GPS",
            "美国赌城拉斯维加斯发生重大枪击事件", "日本在越南承建的跨海大桥未建完已下沉",
            "南水北调功在当代，数亿人喝上长江水", "马克龙呼吁重建可与中国匹敌的强大欧洲"
    };

    //定义一个新闻播放线程
    private class PlayThread extends Thread {
        @Override
        public void run() {
            //向处理器发送播放开始的空消息
            mHandler.sendEmptyMessage(BEGIN);
            while (isPlaying) {
                try {
                    sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();     //获得一个默认的消息对象
                message.what = SCROLL;      //消息类型
                message.obj = mNewsArray[(int) (Math.random() * 30 % 5)];        //消息描述
                mHandler.sendMessage(message);      //向处理区发送消息
            }
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendEmptyMessage(END);
        }
    }


    private Handler mHandler = new Handler() {
      //收到消息时触发
        @Override
        public void handleMessage(Message msg) {
            String desc = tv_message.getText().toString();
            if (msg.what == BEGIN) {
                desc = String.format("%s\n%s %s", desc, DateUtil.getNowTime(), "开始播放新闻");
            } else if (msg.what == SCROLL) {
                desc = String.format("%s\n%s %s", desc, DateUtil.getNowTime(), msg.obj);
            } else if (msg.what == END) {
                desc = String.format("%s\n%s %s", desc, DateUtil.getNowTime(), "新闻播放结束");
            }
            tv_message.setText(desc);
        }
    };

}
