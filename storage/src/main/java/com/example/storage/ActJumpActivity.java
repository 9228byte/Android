package com.example.storage;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.storage.util.DateUtil;


public class ActJumpActivity extends AppCompatActivity implements OnClickListener{
    private final static String TAG = "ActJumpActivity";
    private TextView tv_life;
    private String mStr = "";

    private void refreshLife(String desc) {
        Log.d(TAG, desc);
        mStr = String.format("%s%s %s %s\n", mStr, DateUtil.getNowTimeDetail(), TAG, desc);
        tv_life.setText(mStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_jump);
        findViewById(R.id.btn_act_next).setOnClickListener(this);
        tv_life = findViewById(R.id.tv_life);
        refreshLife("onCreate");
    }

    @Override
    protected void onStart() {
        refreshLife("onStart");     //开始页面
        super.onStart();
    }

    @Override
    protected void onStop() {   //停止页面
        refreshLife("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {    //销毁页面
        refreshLife("OnDestory");
        super.onDestroy();
    }

    @Override
    protected void onPause() {  //暂停页面
        refreshLife("onPause");
        super.onPause();
    }

    @Override
    protected void onResume() { //恢复页面
        refreshLife("onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {    //重启页面
        refreshLife("onRestart");
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_act_next) {
            Intent intent = new Intent(this, ActNextActivity.class);
            startActivityForResult(intent, 0);
        }

    }

    //接收返回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String nextLife = data.getStringExtra("life");
        refreshLife("\n" + nextLife);
        refreshLife("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
