package com.example.middle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.middle.Util.DateUtil;

public class ActResponseActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_response;
    private EditText et_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_response);
        findViewById(R.id.btn_act_response).setOnClickListener(this);
        et_response = findViewById(R.id.et_response);
        tv_response = findViewById(R.id.tv_response);
        //从前一个页面传来的意图中获取快递包裹
        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String desc = String.format("收到请求消息：\n请求时间为%s\n请求内容为%s", request_time, request_content);
        tv_response.setText(desc);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_act_response) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("response_time", DateUtil.getNowTime());
            bundle.putString("response_content", et_response.getText().toString());
            intent.putExtras(bundle);       //打包包裹给意图
            setResult(Activity.RESULT_OK, intent);  //携带意图返回
            finish();   //关闭页面
        }
    }
}
