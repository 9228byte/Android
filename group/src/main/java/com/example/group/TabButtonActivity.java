package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class TabButtonActivity extends AppCompatActivity implements
        OnClickListener, OnCheckedChangeListener {
    private TextView tv_tab_button;
    private CheckBox ck_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_button);
        tv_tab_button = findViewById(R.id.tv_tab_button);
        tv_tab_button.setOnClickListener(this);
        ck_select = findViewById(R.id.ck_select);
        ck_select.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_tab_button) {
            ck_select.setChecked(!ck_select.isChecked());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_select) {
            tv_tab_button.setSelected(isChecked);
        }
    }
}
