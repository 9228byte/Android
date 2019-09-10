package com.example.network.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.network.R;
import com.example.network.task.GetImageCodeTask;
import com.example.network.task.GetImageCodeTask.OnImageCodeListener;

public class HttpImageActivity extends AppCompatActivity implements
        OnClickListener, OnImageCodeListener {
    private static final String TAG = "HttpImageActivity";
    private ImageView iv_image_code;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_image);
        iv_image_code = findViewById(R.id.iv_image_code);
        //点击图片重新获取验证码超
        iv_image_code.setOnClickListener(this);
        getImageCode();     //获取验证码
    }

    //获取验证码
    private void getImageCode() {
        if (!isRunning) {
            Log.d(TAG, "getImageCode: ");
            isRunning = true;
            //创建验证码获取线程
            GetImageCodeTask codeTask = new GetImageCodeTask();
            //设置验证码获取线程
            codeTask.setOnImageListener(this);
            //把验证码获取线程加入到处理队列
            codeTask.execute();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_image_code) {
            getImageCode();
        }
    }

    @Override
    public void onGetCode(String path) {
        //把指定路径的验证码图片显示在图片视图上面
        iv_image_code.setImageURI(Uri.parse(path));
        isRunning = false;
    }
}
