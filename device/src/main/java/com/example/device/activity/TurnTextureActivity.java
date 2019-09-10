package com.example.device.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.device.R;
import com.example.device.widget.TurnTextureView;

/**
 * TurnTextureActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class TurnTextureActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TurnTextureView ttv_circle;
    private CheckBox ck_control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_texture);
        ttv_circle = findViewById(R.id.ttv_circle);
        ttv_circle.setSurfaceTextureListener(ttv_circle);
        ck_control = findViewById(R.id.ck_control);
        ck_control.setOnCheckedChangeListener(this);
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this, R.layout.item_select, alaphArray);
        starAdapter.setDropDownViewResource(R.layout.item_select);
        Spinner sp_alaph = findViewById(R.id.sp_alpha);
        sp_alaph.setPrompt("请选择透明度");
        sp_alaph.setAdapter(starAdapter);
        sp_alaph.setSelection(1);
        sp_alaph.setOnItemSelectedListener(new MySelectedListener());
    }

    private String[] alaphArray = {"0.0", "0.2", "0.4", "0.6", "0.8", "1.0"};

    class MySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ttv_circle.setAlpha(Float.parseFloat(alaphArray[position]));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_control) {
            if (isChecked) {
                ck_control.setText("停止");
                ttv_circle.start();
            } else {
                ck_control.setText("转动");
                ttv_circle.stop();
            }
        }
    }
}
