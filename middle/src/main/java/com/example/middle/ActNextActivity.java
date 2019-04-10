package com.example.middle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.middle.Util.DateUtil;

public class ActNextActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "ActNextActivity";
    private TextView tv_life;
    private String mStr = "";

    private void refreshLife(String desc) {
        Log.d(TAG, desc);
        mStr = String.format("%s    %s %s %s\n", mStr, DateUtil.getNowTimeDetail(), TAG,desc);
        tv_life.setText(mStr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_next);
        findViewById(R.id.btn_act_pre).setOnClickListener(this);
        tv_life = findViewById(R.id.tv_life);
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
    protected void onRestart() {
        refreshLife("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        refreshLife("onResume");
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_act_pre) {
            Intent intent = new Intent();   //创建一个新意图
            intent.putExtra("life", mStr);  //把字符串参数给意图
            setResult(Activity.RESULT_OK, intent);  //携带意图返回前一个页面
            finish();
        }
    }
}
