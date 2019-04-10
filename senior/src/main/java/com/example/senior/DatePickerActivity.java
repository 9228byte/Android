package com.example.senior;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 *  Created by lao on 2019/4/6
 */

//该页面实现了接口onDateSetListener,意味着要重写日期监听器的onDateSet方法
public class DatePickerActivity extends AppCompatActivity implements OnClickListener, OnDateSetListener {
    private TextView tv_date;
    private DatePicker dp_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        tv_date = findViewById(R.id.tv_date);
        dp_date = findViewById(R.id.dp_date);
        findViewById(R.id.btn_date).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    //一旦点击日期对话框上的确定按钮，救会触发监听器的onDateSet方法
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //获取日期对话框设定的年月份
        String desc = String.format("您选择的日期是%d年%d月%d日",
                year, month + 1, dayOfMonth);
        tv_date.setText(desc);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date) {
            //获取日历的一个实例，里面内包含当前的年月日
            Calendar calendar = Calendar.getInstance();
            //构建一个日期对话框，该对话框已经集成了日期选择器
            //DatePickerDialog 的第二个构造参数指定了日期监听器
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            //把日期对话框显示在界面上
            dialog.show();
        } else if (v.getId() == R.id.btn_ok) {
            //获取日期选择器上dp_date设定的年月份
            String desc = String.format("您选择的日期是%d年%d月%d日",
                    dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
            tv_date.setText(desc);
        }
    }
}
