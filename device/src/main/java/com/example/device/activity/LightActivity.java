package com.example.device.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.device.R;
import com.example.device.util.DateUtil;
import com.example.device.util.SwitchUtil;

/**
 * LightActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class LightActivity extends AppCompatActivity implements
        OnCheckedChangeListener, SensorEventListener {
    private TextView tv_light;
    private SensorManager mSensorMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        CheckBox ck_bright = findViewById(R.id.ck_bright);
        //检查屏幕是否为自动调节
        if (SwitchUtil.getAutoBrightStatus(this)) {
            ck_bright.setChecked(true);
        }
        ck_bright.setOnCheckedChangeListener(this);
        tv_light = findViewById(R.id.tv_light);
        mSensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销当前活动的传感监听器
        mSensorMgr.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //给光线传感器注册传感器监听器
        mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {      //光线强度变更事件
            float light_strength = event.values[0];
            tv_light.setText(DateUtil.getNowTime() + "当前光线强度为" + light_strength);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变是回调该方法，一般无需处理
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_bright) {
            //设置是否开启屏幕亮度的自动调节
            //设置系统亮度
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.System.canWrite(getApplicationContext())) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                } else {
                    //有了权限，具体的动作
                    SwitchUtil.setAutoBrightStatus(this, isChecked);
//                    Settings.System.putInt(getContentResolver(),
//                            Settings.System.SCREEN_BRIGHTNESS, progress);
//                    data2 = intToString(progress, 255);
//                    tvSunlightValue.setText(data2 + "%");
                }
            }

/*
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(!Settings.System.canWrite(this)){
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                            Uri.parse("package:" + getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, REQUEST_CODE_ASK_WRITE_SETTINGS);
                }else{
                    //有了权限，你要做什么呢？具体的动作
                }
                ｝
*/



            }
    }
}
