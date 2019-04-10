package com.example.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SwitchDefaultActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch sw_ststus;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_default);
        sw_ststus = findViewById(R.id.sw_status);
        tv_result = findViewById(R.id.tv_result);
        sw_ststus.setOnCheckedChangeListener(this);
        refreshResult();;
    }

    private void refreshResult(){
        String result = String.format("Switch按钮的状态是%s",
        (sw_ststus.isChecked()) ? "开" : "关");
        tv_result.setText(result);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        refreshResult();
    }
}
