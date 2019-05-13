package com.example.custom.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.custom.MainActivity;
import com.example.custom.MainApplication;
import com.example.custom.R;
import com.example.custom.bean.AppInfo;
import com.example.custom.util.AppUtil;
import com.example.custom.util.DateUtil;
import com.example.custom.util.SharedUtil;
import com.example.custom.util.StringUtil;

import java.util.ArrayList;

/**
 * TrafficService
 *
 * @author lao
 * @date 2019/4/27
 */

public class TrafficService extends Service {
    private static final String TAG = "TrafficService";
    private MainApplication app;
    private int limit_day;
    private int mNowDay;
    private Notification mNotify;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取当前应用的实例
        app = MainApplication.getInstance();
        limit_day = SharedUtil.getIntance(this).readInt("limit_day", 50);
        mHandler.post(mRefresh);
        return START_STICKY;
    }

    private Handler mHandler = new Handler();
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            //更新流量数据库
            refreshData();
            //刷新流量通知栏
            refreshNotify();
            //延迟刷新流量任务
            mHandler.postDelayed(this, 1000);
        }
    };

    private void refreshData() {
        mNowDay = Integer.parseInt(DateUtil.getNowDateTime("yyyyMMdd"));
        //获取应用队列
        ArrayList<AppInfo> appinfoList = AppUtil.getAppInfo(this, 1);
        for (int i = 0; i < appinfoList.size(); i++) {
            AppInfo item = appinfoList.get(i);
            item.traffic = TrafficStats.getUidRxBytes(item.uid);
            item.month = mNowDay / 100;
            item.day = mNowDay;
            appinfoList.set(i, item);
        }
        //往数据库中插入最新的应用流量
        app.mTrafficHelper.insert(appinfoList);
    }

    private void refreshNotify() {
        String lastDate = DateUtil.getAddDate("" + mNowDay, -1);
        ArrayList<AppInfo> lastArray = app.mTrafficHelper.query("day=" + lastDate);
        ArrayList<AppInfo> thisArray = app.mTrafficHelper.query("day=" + mNowDay);
        long traffic_day = 0;
        for (int i = 0; i < thisArray.size(); i++) {
            AppInfo item = thisArray.get(i);
            for (int j = 0; j < lastArray.size(); j++) {
                if (item.uid == lastArray.get(j).uid) {
                    item.traffic -= lastArray.get(j).traffic;
                    break;
                }
            }
            traffic_day += item.traffic;
        }
        String desc = "今日已用流量" + StringUtil.formatData(traffic_day);

        int progress;
        int layoutId = R.layout.notify_traffic_green;
        float trafficM = traffic_day / 1024.0f / 1024.0f;
        if (trafficM > limit_day * 2) { // 超出两倍限额，则展示红色进度条
            progress = (int) ((trafficM > limit_day * 3) ? 100 : (trafficM - limit_day * 2) * 100 / limit_day);
            layoutId = R.layout.notify_traffic_red;
        } else if (trafficM > limit_day) { // 超出一倍限额，则展示橙色进度条
            progress = (int) ((trafficM > limit_day * 2) ? 100 : (trafficM - limit_day) * 100 / limit_day);
            layoutId = R.layout.notify_traffic_yellow;
        } else { // 未超出限额，则展示绿色进度条
            progress = (int) (trafficM * 100 / limit_day);
        }
        Log.d(TAG, "progress=" + progress);
        // 显示流量通知
        showFlowNotify(layoutId, desc, progress);
    }

    private void showFlowNotify(int layoutId, String desc, int progress) {
        RemoteViews notify_traffic = new RemoteViews(this.getPackageName(), layoutId);
        notify_traffic.setTextViewText(R.id.tv_flow, desc);
        notify_traffic.setProgressBar(R.id.pb_flow, 100, progress, false);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent clickIntent = PendingIntent.getActivity(this,
                R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, getString(R.string.app_name));
        }
        builder.setContentIntent(clickIntent)
                .setContent(notify_traffic)
                .setTicker("手机安全助手运行中")
                .setSmallIcon(R.drawable.ic_app);
        mNotify = builder.build();
        startForeground(9, mNotify);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNotify != null) {
            //停止前台展示。清除通知栏
            stopForeground(true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
