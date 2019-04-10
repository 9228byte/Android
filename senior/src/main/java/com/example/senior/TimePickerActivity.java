package com.example.senior;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;


/**
 *  Created by lao on 2019/4/6
 */


public class TimePickerActivity extends AppCompatActivity implements OnClickListener, OnTimeSetListener {
    private TextView tv_time;
    private TimePicker tp_time;

    //该页面实现了接口OnTimeSetListener,意味着要重写时间监听器的OnSetTime方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        tv_time = findViewById(R.id.tv_time);
        tp_time = findViewById(R.id.tp_time);
        findViewById(R.id.btn_time).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_time) {
            //h获取日期的一个实例，里面包含当前时间的时分秒
            Calendar calendar = Calendar.getInstance();
            //TimePickerDialog的第二个构造参数指定了时间监听器
            TimePickerDialog dialog = new TimePickerDialog(this, this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);      //是否24小时制
            dialog.show();
        } else if (v.getId() == R.id.btn_ok) {
            //获取时间选择器tp_time上设定的小时和分钟
            String desc = String.format("您选择的时间是%d时%d分",
                    tp_time.getCurrentHour(), tp_time.getCurrentMinute());
            tv_time.setText(desc);
        }
    }

    //一旦点击时间对话框上的确定按钮，救会触发监听器的onTimeSet方法
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc = String.format("您选择的时间是%d时%d分", hourOfDay, minute);
        tv_time.setText(desc);
    }
}
