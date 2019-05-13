package com.example.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.custom.widget.CustomDateDialog;
import com.example.custom.widget.CustomDateDialog.OnDateSetListener;
import com.example.custom.widget.CustomMonthDialog;
import com.example.custom.widget.CustomMonthDialog.OnMonthSetListener;

import java.util.Calendar;

public class DialogDateActivity extends AppCompatActivity implements OnClickListener {
    private TextView tv_date;
    private TextView tv_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_date);
        tv_date = findViewById(R.id.tv_date);
        findViewById(R.id.btn_date).setOnClickListener(this);
        tv_month = findViewById(R.id.tv_month);
        findViewById(R.id.btn_month).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date) {
            showDateDialog();
            //Toast.makeText(this, "点击了按钮", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_month) {
            showMonthDialog();
        }
    }

    //显示日期对话框
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        CustomDateDialog dialog = new CustomDateDialog(this);
        dialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DateListener());
        dialog.show();
    }

    private class DateListener implements OnDateSetListener {

        @Override
        public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
            String desc = String.format("您选择的日期是%d年%d月%d日", year, monthOfYear, dayOfMonth);
            tv_date.setText(desc);
        }
    }

    //显示月份对话框
    private void showMonthDialog() {
        Calendar calendar = Calendar.getInstance();
        CustomMonthDialog dialog = new CustomMonthDialog(this);
        dialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new MonthListener());
        dialog.show();
    }

    private class MonthListener implements OnMonthSetListener {

        @Override
        public void onMonthSet(int year, int monthOfYear) {
            String desc= String.format("您选择的月份是%d年%d月", year, monthOfYear);
            tv_month.setText(desc);
        }
    }
}
