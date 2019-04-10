package com.example.storage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.storage.util.DateUtil;

public class ActNextActivity extends AppCompatActivity implements OnClickListener{
    private  final static String TAG = "ActNextActivity";
    private TextView tv_life;
    private String mStr = "";


    private void refreshLife(String desc) {
        Log.d(TAG, desc);
        mStr = String.format("%s    %s %s %s\n", mStr, DateUtil.getNowTimeDetail(), TAG, desc);
        tv_life.setText(mStr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_next);
        tv_life = findViewById(R.id.tv_life);
        findViewById(R.id.btn_act_pre).setOnClickListener(this);
        refreshLife("onCreate");
    }

    @Override
    protected void onStart() {
        refreshLife("onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        refreshLife("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        refreshLife("onDestory");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        refreshLife("onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        refreshLife("onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        refreshLife("onRestart");
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_act_pre) {
            Intent intent = new Intent();
            intent.putExtra("life", mStr);      //把字符参数塞给意图
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }
}
