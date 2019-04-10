package com.example.junior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.junior.util.DateUtil;

public class BbsActivity extends AppCompatActivity implements View.OnClickListener ,View.OnLongClickListener{
    private TextView tv_bbs;
    private TextView tv_control;
    private String [] mChatStr = {"你吃饭了吗？", "今天天气真好呀。",
            "我中奖啦！", "我们去看电影吧", "晚上干什么好呢？",};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);
        tv_control = findViewById(R.id.tv_control);
        tv_control.setOnClickListener(this);
        tv_control.setOnLongClickListener(this);

        tv_bbs = findViewById(R.id.tv_bbs);
        tv_bbs.setOnClickListener(this);
        tv_bbs.setOnLongClickListener(this);
        //设置tv_bbs内部文字对齐方式靠左靠下
        tv_bbs.setGravity(Gravity.LEFT|Gravity.BOTTOM);
        tv_bbs.setLines(8);
        tv_bbs.setMaxLines(8);
        //设置tv_bbs内部文字的移动方式为滚动形式
        tv_bbs.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View v) {       //点击生成聊天内容
        if (v.getId() == R.id.tv_control || v.getId() == R.id.tv_bbs){
            int random = (int)(Math.random()*10)%5;
            String newStr = String.format("%S\n%s %S",tv_bbs.getText().toString(), DateUtil.getNowTime(),mChatStr[random]);
            tv_bbs.setText(newStr);
        }
    }

    @Override
    public boolean onLongClick(View v) {        //长按后删除
        if (v.getId() ==R.id.tv_control || v.getId() == R.id.tv_bbs){
            tv_bbs.setText("");     //删除为把文本设置为空
            return true;
        }
        return false;
    }
}
