package com.example.senior;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.senior.bean.ScheduleArrange;
import com.example.senior.database.DbHelper;
import com.example.senior.database.ScheduleArrangeHelper;

import java.util.Calendar;
import java.util.List;

public class ScheduleDetailActivity extends AppCompatActivity implements
        OnClickListener, OnTimeSetListener {
    private static final String TAG = "ScheduleDetailActivity";
    private Button btn_back, btn_edit, btn_save;
    private TextView schedule_date, schedule_time;
    private Spinner schedule_alarm;
    private EditText schedule_title, schedule_content;
    private String month, day, week, holiday, solar_date, lunar_date, detail_date;
    private ScheduleArrange mArrange;
    private ScheduleArrangeHelper mScheduleHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        btn_back = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);
        btn_save = findViewById(R.id.btn_save);
        btn_back.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        schedule_date = findViewById(R.id.schedule_date);
        schedule_time = findViewById(R.id.schedule_time);
        schedule_title = findViewById(R.id.schedule_title);
        schedule_content = findViewById(R.id.schedule_content);
        schedule_time.setText("00:00");
        schedule_time.setOnClickListener(this);
        getBundleInfo();
        initAlarmSpinner();
    }
    
    private void getBundleInfo() {
        Bundle req = getIntent().getExtras();
        day = req.getString("day");
        solar_date = req.getString("solar_date");   //公历
        lunar_date = req.getString("lunar_date");   //农历
        month = day.substring(0, 6);
        week = req.getString("week");
        holiday = req.getString("holiday");
        detail_date = String.format("%s %s\n%s", solar_date, lunar_date, week);
        if (!TextUtils.isEmpty(holiday)) {
            detail_date = String.format("%s, 今天是 %s", detail_date, holiday);
        }
        schedule_date.setText(detail_date);
        Log.d(TAG, "getBundleInfo: ");
        
    }
    
    private int alarmType = 0;
    private String[] alarmArray= {"不提醒", "提前5分钟", "提前10分钟", "提前15分钟", "提前半小时", "提前1小时", "当前时间后10秒"};
    
    private void initAlarmSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                R.layout.item_select, alarmArray);
        schedule_alarm = findViewById(R.id.schedule_alarm);
        schedule_alarm.setPrompt("请选择提醒间隔");
        schedule_alarm.setAdapter(adapter);
        schedule_alarm.setSelection(0);
        schedule_alarm.setOnItemSelectedListener(new AlarmSelectedListener());
    }
    
    private int[] advanceArray = {0, 5, 10, 15, 30, 60, 10};
    
    class AlarmSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            alarmType = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScheduleHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArrange = new ScheduleArrange();
        mScheduleHelper = new ScheduleArrangeHelper(this, DbHelper.db_name, null, 1);
        List<ScheduleArrange> mArrangeList = (List<ScheduleArrange>) mScheduleHelper.queryInfoByDay(day);
        if (mArrangeList.size() >= 1) {     //已有日程安排
            enableEdit(false);      //关闭编辑模式
            mArrange = mArrangeList.get(0);
            schedule_time.setText(mArrange.hour + ":" + mArrange.minute);
            Log.d(TAG, String.format("onResume: 日程时间是：%s:%s", mArrange.hour, mArrange.minute));
            schedule_alarm.setSelection(mArrange.alarm_type);
            schedule_title.setText(mArrange.title);
            schedule_content.setText(mArrange.content);
        } else {
            enableEdit(true);       //开启编辑模式
        }
    }
    
    //是否开启编辑模式
    private void enableEdit(boolean enabled) {
        schedule_time.setEnabled(enabled);
        schedule_alarm.setEnabled(enabled);
        schedule_title.setEnabled(enabled);
        if (enabled) {
            schedule_time.setBackgroundResource(R.drawable.editext_selector);
            schedule_title.setBackgroundResource(R.drawable.editext_selector);
            schedule_content.setBackgroundResource(R.drawable.editext_selector);
        } else {
            schedule_time.setBackgroundDrawable(null);
            schedule_title.setBackgroundDrawable(null);
            schedule_content.setBackgroundDrawable(null);
        }
        btn_edit.setVisibility(enabled ? View.GONE : View.VISIBLE);
        btn_save.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.format("%02d:%02d", hourOfDay, minute);
        schedule_time.setText(time);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.schedule_time) {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog dialog = new TimePickerDialog(this, this,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        } else if (v.getId() == R.id.btn_back) {
            finish();
        } else if (v.getId() == R.id.btn_edit) {
            enableEdit(true);
        } else if (v.getId() == R.id.btn_save) {
            if (TextUtils.isEmpty(schedule_title.getText())) {
                Toast.makeText(this, "请输入日程标题", Toast.LENGTH_SHORT).show();
                return;
            }
            enableEdit(false);      //关闭编辑模式
            saveArrange();
        }
    }
    
    //声明一个闹钟广播事件的标识串
    private String ALARM_EVENT = "com.example.senior.ScheduleDetailActivity.AlarmReceiver";
    
    //保存编辑好的日程数据
    private void saveArrange() {
        String[] time_split = schedule_time.getText().toString().split(":");
        mArrange.hour = time_split[0];
        mArrange.minute = time_split[1];
        Log.d(TAG, "saveArrange: minute:" + time_split[1]);
        mArrange.alarm_type = alarmType;        //提醒时间
        mArrange.title = schedule_title.getText().toString();
        mArrange.content = schedule_content.getText().toString();
        mArrange.update_time = schedule_time.getText().toString();
        Log.d(TAG, "saveArrange: schedule_content=" + mArrange.content);
        if (mArrange.xuhao <= 0) {
            mArrange.month = month;
            mArrange.day = day;
            mScheduleHelper.add(mArrange);      //添加新的日程记录
        } else  {       //已存在日程表中
            mScheduleHelper.update(mArrange);       //更新旧的记录
        }
        Toast.makeText(this, "保存日程成功", Toast.LENGTH_SHORT).show();
        //设置闹钟提醒
        if (alarmType > 0) {
            //创建一个广播事件的意图
            Intent intent = new Intent(ALARM_EVENT);
            //创建一个用于广播的延迟意图
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //从系统服务中获取闹钟管理器
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); 
            Calendar calendar = Calendar.getInstance();
            if (alarmType == 6) {   //以当前时间为准
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, advanceArray[alarmType]);
            } else {
                int day_int = Integer.parseInt(day);
                calendar.set(day_int / 10000, day_int % 10000 / 100 - 1, day_int %100,
                        Integer.parseInt(mArrange.hour), Integer.parseInt(mArrange.minute), 0);
                calendar.add(Calendar.SECOND, -advanceArray[alarmType] * 60);
            }
            //开始设定闹钟，延迟若干秒后，携带延迟意图发送闹钟广播
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    

    //不能定义为私有
    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Log.d(TAG, "onReceive: ");
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(3000);
            }
        }
    }
    
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            alarmReceiver = new AlarmReceiver();
            IntentFilter filter = new IntentFilter(ALARM_EVENT);
            registerReceiver(alarmReceiver, filter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //9.0适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            unregisterReceiver(alarmReceiver);
        }
    }
}
