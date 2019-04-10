package com.example.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.middle.Util.DateUtil;

public class ActRotateActivity extends AppCompatActivity {
    private final static String TAG = "ActRotateActivity";
    private TextView tv_life;
    private String mStr = "";

    private void refreshLife(String desc) {
        Log.d(TAG, desc);
        mStr = String.format("%s%s %s %s\n", mStr, DateUtil.getNowTime(), TAG, desc);
        tv_life.setText(mStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_rotate);
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
    protected void onResume() {
        refreshLife("onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        refreshLife("onRestart");
        super.onRestart();
    }
}
