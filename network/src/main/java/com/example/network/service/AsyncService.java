package com.example.network.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.network.util.DateUtil;

public class AsyncService extends IntentService {
    private static final String TAG = "AsyncService";

    public AsyncService() {
        super("com.example.network.service.AsyncService");
    }

    //onStartCommand运行于主线程
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");

//        try {
//            Thread.sleep(5 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    //onHandlerIntent运行分主线程
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "begin onHandleIntent: " + DateUtil.getNowDateTime());
        //在这里执行耗时任务，不会影响页面的处理
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "end onHandleIntent: " + DateUtil.getNowDateTime());
    }
}
