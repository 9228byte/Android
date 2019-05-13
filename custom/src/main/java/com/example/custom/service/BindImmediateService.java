package com.example.custom.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.custom.BindImmediateActivity;

public class BindImmediateService extends Service {
    private static final String TAG = "BindImmediateService";
    private final IBinder mBinder = new LocalBinder();

    //定义一个当前服务的粘合剂，用于将该服务粘到活动页面的进程中
    public class LocalBinder extends Binder {
        public BindImmediateService getService() {
            return BindImmediateService.this;
        }
    }

    public void refresh(String text) {
        Log.d(TAG, text);
        BindImmediateActivity.showText(text);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "绑定服务开始");
        refresh("onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        refresh("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refresh("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        refresh("onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "绑定服务结束");
        refresh("onUnbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        refresh("onRebind");
        super.onRebind(intent);
    }
}
