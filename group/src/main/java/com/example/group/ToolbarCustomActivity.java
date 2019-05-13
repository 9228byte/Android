package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group.util.DateUtil;
import com.example.group.widget.CustomDateDialog;

import java.util.Calendar;

public class ToolbarCustomActivity extends AppCompatActivity implements
        View.OnClickListener,  CustomDateDialog.OnDateSetListener {
    private static final String  TAG = "ToolbarCustomActivity";
    private TextView tv_day;
    private TextView tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_custom);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        tv_day = findViewById(R.id.tv_day);
        tv_desc = findViewById(R.id.tv_desc);
        //格式不能把写错
        tv_day.setText(DateUtil.getNowDateTime("yyyy年MM月dd日"));
        tv_day.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_refresh) {
            tv_desc.setText("当前刷新时间：" + DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这是个演示的demo", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_day) {
            Calendar calendar = Calendar.getInstance();
            CustomDateDialog dialog = new CustomDateDialog(this);
            dialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) ,this);
            dialog.show();
        }
    }

    @Override
    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%d年%d月%d日", year, monthOfYear, dayOfMonth);
        tv_day.setText(date);
        tv_desc.setText("您选择的日期是：" + date);
    }
}
