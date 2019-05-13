package com.example.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.custom.service.NormalService;
import com.example.custom.util.DateUtil;

/**
 * ServiceNormalActivity
 *
 * @author lao
 * @date 2019/4/21
 */

public class ServiceNormalActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "ServiceNormalActivity";
    private static TextView tv_normal;
    private Intent mIntent;
    private static String mDesc = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_normal);
        tv_normal = findViewById(R.id.tv_normal);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        mIntent = new Intent(this, NormalService.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            startService(mIntent);  //开启服务
        } else if (v.getId() == R.id.btn_stop) {
            stopService(mIntent);   //停止服务
        }
    }

    public static void showText(String desc) {
        if (tv_normal != null) {
            mDesc = String.format("%s%s %s\n", mDesc, DateUtil.getNowDateTime("HH:mm:ss"), desc);
            tv_normal.setText(mDesc);
        }
    }
}
