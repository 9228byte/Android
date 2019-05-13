package com.example.custom;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.example.custom.database.TrafficDBHelper;
import com.example.custom.util.NotifyUtil;

/**
 * Created by lao on 2019/4/7
 */

public class MainApplication extends Application {
    private final static String TAG = "MainApplication";
    // 声明一个当前应用的静态实例
    private static MainApplication mApp;
    public TrafficDBHelper mTrafficHelper;
    // 利用单例模式获取当前应用的唯一实例
    public static MainApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 在打开应用时对静态的应用实例赋值
        mApp = this;
        Log.d(TAG, "onCreate");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotifyUtil.createNotifyChannel(this, getString(R.string.app_name));
        }
        mTrafficHelper = TrafficDBHelper.getInstance(this, 1);
        mTrafficHelper.openWriteLink();
        Log.d(TAG, "onCreate: ");
    }
}

