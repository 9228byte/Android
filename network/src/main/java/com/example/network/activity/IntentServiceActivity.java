package com.example.network.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.network.R;
import com.example.network.service.AsyncService;
import com.example.network.util.DateUtil;

public class IntentServiceActivity extends AppCompatActivity implements OnClickListener {
    private TextView tv_intent;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        tv_intent = findViewById(R.id.tv_intent);
        findViewById(R.id.btn_intent).setOnClickListener(this);
        //延迟100毫秒启动异步任务
        mHandler.postDelayed(mService, 100);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_intent) {
            tv_intent.setText(DateUtil.getNowTime() + "您轻轻点击一下（异步任务正在运行，不影响您的界面操作）");
        }
    }

    private Runnable mService = new Runnable() {
        @Override
        public void run() {
            //构建通往异步服务的意图
            Intent intent = new Intent(IntentServiceActivity.this, AsyncService.class);
            startService(intent);
        }
    };

}
