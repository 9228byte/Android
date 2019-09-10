package com.example.device.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.device.R;

/**
 * StepActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class StepActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "StepActivity";
    private TextView tv_step;
    private SensorManager mSensorMgr;
    private int mStepDetector = 0;      //累加步行检测次数
    private int mStepCounter = 0;       //计步器统计初始的步伐数目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        tv_step = findViewById(R.id.tv_step);
        mSensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorMgr.unregisterListener(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);
/*        int suitable = 0;
        List<Sensor> sensorList = mSensorMgr.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList) {
            if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {        //步行传感器
                suitable += 1;
                Log.d(TAG, "onResume: ");
            } else if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {      //计数器
                suitable += 10;
            }
        }
        if (suitable / 10 > 0) {
            //给步行检测传感器注册传感器监听器
//            mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_NORMAL);
            //给计步器注册传感监听器
            mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tv_step.setText("当前设备不支持计步器，请检查是否存在步行检测传感器和计步器");
        }*/
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {      //步行检测
        mStepDetector++;
        /*    if (event.values[0] == 1.0f) {
                mStepDetector++;
//            }
        } else*/
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {        //计步器时间
            mStepCounter = (int) event.values[0];
        }
        String desc = String.format("设备检测到您当前走了%d步，总计数为%d步", mStepDetector, mStepCounter);
        tv_step.setText(desc);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //传感器精度发生变化时回调该方法，一般无需处理
    }
}
