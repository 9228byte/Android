package com.example.device;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_turn_view).setOnClickListener(this);
        findViewById(R.id.btn_turn_surface).setOnClickListener(this);
        findViewById(R.id.btn_camera_info).setOnClickListener(this);
        findViewById(R.id.btn_photograph).setOnClickListener(this);
        findViewById(R.id.btn_turn_texture).setOnClickListener(this);
        findViewById(R.id.btn_shooting).setOnClickListener(this);
        findViewById(R.id.btn_seekbar).setOnClickListener(this);
        findViewById(R.id.btn_volume).setOnClickListener(this);
        findViewById(R.id.btn_audio).setOnClickListener(this);
        findViewById(R.id.btn_video).setOnClickListener(this);
        findViewById(R.id.btn_sensor).setOnClickListener(this);
        findViewById(R.id.btn_acceleration).setOnClickListener(this);
        findViewById(R.id.btn_light).setOnClickListener(this);
        findViewById(R.id.btn_direction).setOnClickListener(this);
        findViewById(R.id.btn_step).setOnClickListener(this);
        findViewById(R.id.btn_gyroscope).setOnClickListener(this);
        findViewById(R.id.btn_location_setting).setOnClickListener(this);
        findViewById(R.id.btn_location_begin).setOnClickListener(this);
        findViewById(R.id.btn_nfc).setOnClickListener(this);
        findViewById(R.id.btn_infrared).setOnClickListener(this);
        findViewById(R.id.btn_bluetooth).setOnClickListener(this);
        findViewById(R.id.btn_navigation).setOnClickListener(this);
        findViewById(R.id.btn_wechat).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
