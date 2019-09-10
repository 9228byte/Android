package com.example.device.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.device.R;

import java.util.List;

/**
 * GyroscopeActivity
 *
 * @author lao
 * @date 2019/6/16
 */
@SuppressLint("DefaultLocale")
public class GyroscopeActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "GyroscopeActivity";
    private static final float NS2S = 1.0f / 1000000000.0f; //将纳秒转化成秒
    private TextView tv_gyroscope;
    private SensorManager mSensorMgr;
    private float mTimestamp;       //记录上次的时间戳
    private float mAngle[] = new float[3];      //记录三个方向上的旋转角度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);
        tv_gyroscope = findViewById(R.id.tv_gyroscope);
        mSensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorMgr.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensorList = mSensorMgr.getSensorList(Sensor.TYPE_ALL);
        boolean isSuitable = false;
        for (Sensor sensor : sensorList) {
            if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {    //找到陀螺仪
                isSuitable = true;
                break;
            }
        }
        if (isSuitable) {
            //给陀螺仪注册监听器
            mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_FASTEST);
            Log.d(TAG, "onResume: 注册陀螺仪监听器");
        } else {
            tv_gyroscope.setText("当前设备不支持陀螺仪，请检查是否存在陀螺仪传感器");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.d(TAG, "onSensorChanged: ");
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {      //陀螺仪角度变更事件
            Log.d(TAG, "onSensorChanged: 监听到陀螺仪变更");
            if (mTimestamp != 0 ) {
                Log.d(TAG, "onSensorChanged: 不为0");
                final float dT = (event.timestamp - mTimestamp) * NS2S;
                mAngle[0] += event.values[0] * dT;
                mAngle[1] += event.values[1] * dT;
                mAngle[2] += event.values[2] * dT;
                //x轴的旋转角度，手机平方桌上，然后绕侧边转动
                float angleX = (float) Math.toDegrees(mAngle[0]);
                //y轴的旋转角度，手机平方桌上，然后绕底边转动
                float angleY = (float) Math.toDegrees(mAngle[1]);
                //x轴的旋转角度，手机平方桌上，然后绕水平转动
                float angleZ = (float) Math.toDegrees(mAngle[2]);
                String desc = String.format("陀螺仪检测当前x轴方向的转动角度%f,陀螺仪检测当前y轴方向的转动角度%f,陀螺仪检测当前z轴方向的转动角度%f",
                        angleX, angleY, angleZ);
                tv_gyroscope.setText(desc);
            }
            mTimestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，一般无需处理
    }
}
