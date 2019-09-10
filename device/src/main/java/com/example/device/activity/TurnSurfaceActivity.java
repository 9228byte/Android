package com.example.device.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.device.R;
import com.example.device.widget.TurnSurfaceView;

/**
 * TurnSurfaceActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class TurnSurfaceActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TurnSurfaceView tfv_circle;
    private CheckBox ck_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_surface);
        tfv_circle = findViewById(R.id.tfv_circle);
        ck_control = findViewById(R.id.ck_control);
        ck_control.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_control) {
            if (isChecked){
                ck_control.setText("停止");
                tfv_circle.start();
            } else {
                ck_control.setText("开始");
                tfv_circle.stop();
            }
        }
    }
}
