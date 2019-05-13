package com.example.group;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CoordinatorActivity extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout cl_main;  //声明协调布局
    private FloatingActionButton fab_btn;   //声明悬浮按钮
    private Button btn_floating;
    private boolean floating_show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        cl_main = findViewById(R.id.cl_main);
        fab_btn = findViewById(R.id.fab_btn);
        btn_floating = findViewById(R.id.btn_floating);
        btn_floating.setOnClickListener(this);
        findViewById(R.id.btn_snackbar).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_snackbar) {
            //屏幕底部弹出一行提示条，注意悬浮按钮也会跟着上浮
            Snackbar.make(cl_main, "这是个提示条", Snackbar.LENGTH_LONG).show();
        } else if (v.getId() == R.id.btn_floating) {
            if (floating_show) {
                fab_btn.hide();
                btn_floating.setText("显示悬浮按钮");
            } else {
                fab_btn.show();
                btn_floating.setText("隐藏悬浮按钮");
            }
            floating_show = !floating_show;
        }
    }
}
