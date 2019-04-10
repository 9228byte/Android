package com.example.storage;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import java.util.HashMap;

/**
 * @author lao
 * @create 2019/3/31
 */

public class MainApplication extends Application {
    private final static String TAG = "MainApplication";
    private static MainApplication mApp;        //当前应用的静态变量
    //声明一个公共的信息映射对象，可当做全局变量使用
    public HashMap<String, String> mInfoMap = new HashMap<String, String>();

    //利用单例模式获取当前应用的唯一实例
    public static MainApplication getInstance() {
        Log.d(TAG, "getInstance: ");
        return mApp;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        //在打开应用时对静态的应用实例赋值
        mApp = this;
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate: ");
        super.onTerminate();
    }

    //声明一个公共的图标映射对象
    public HashMap<Long, Bitmap> mIconMap = new HashMap<Long, Bitmap>();
}
