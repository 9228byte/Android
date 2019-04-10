package com.example.middle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.middle.Util.DateUtil;

public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_request;
    private EditText et_request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_request);
        findViewById(R.id.btn_act_request).setOnClickListener(this);
        et_request = findViewById(R.id.et_request);
        tv_request = findViewById(R.id.tv_request);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_act_request) {
            Intent intent = new Intent();
            //设置跳转的活动类
            intent.setClass(this, ActResponseActivity.class);
            //存入字符串
            intent.putExtra("request_time", DateUtil.getNowTime());
            intent.putExtra("request_content",et_request.getText().toString());
            startActivityForResult(intent, 0);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 接收返回数据
        if (data != null) {
            // 从意图中取出名叫response_time的字符串
            String response_time = data.getStringExtra("response_time");
            // 从意图中取出名叫response_content的字符串
            String response_content = data.getStringExtra("response_content");
            String desc = String.format("收到返回消息：\n应答时间为%s\n应答内容为%s",
                    response_time, response_content);
            // 把返回消息的详情显示在文本视图上
            tv_request.setText(desc);
        }
    }
}
