package com.example.custom;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RemoteViews;

/**
 * NotifyCustomActivity
 *
 * @author lao
 * @date 2019/4/21
 */

public class NotifyCustomActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "NotifyCustomActivity";
    private EditText et_song;
    private String PAUSE_EVENT = "";   //暂停、继续

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_custom);
        et_song = findViewById(R.id.et_song);
        findViewById(R.id.btn_send_custom).setOnClickListener(this);
        //从资源文件中获取
        PAUSE_EVENT = getResources().getString(R.string.pause_event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_custom) {
            //获取自定义通知对象
            Notification notify = getNotify(this, PAUSE_EVENT, et_song.getText().toString(), true, 50, SystemClock.elapsedRealtime());
            //从系统服务中获取通知管理器
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //使用通知管理器推送通知，在手机通知栏显示
            notificationManager.notify(R.string.app_name, notify);
        }
    }


    private Notification getNotify(Context ctx, String event, String song, boolean isPlaying, int progress, long time) {
        //广播意图
        Intent intent1 = new Intent(event);
        //延迟广播意图
        PendingIntent broadIntent = PendingIntent.getActivity(ctx, R.string.app_name, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        //根据布局文件生成远程视图
        RemoteViews notify_music = new RemoteViews(ctx.getPackageName(), R.layout.notify_music);
        if (isPlaying) {
            notify_music.setTextViewText(R.id.btn_play, "暂停");      //设置按钮文字
            notify_music.setTextViewText(R.id.tv_play, song + "正在播放");      //设置文本文字
            notify_music.setChronometer(R.id.chr_play, time , "%s" , true);     //设置计数器
        } else {
            notify_music.setTextViewText(R.id.btn_play, "继续");
            notify_music.setTextViewText(R.id.tv_play, song + "暂停播放");
            notify_music.setChronometer(R.id.chr_play, time , "%s" , false);
        }
        //设置远程视图进度条属性
        notify_music.setProgressBar(R.id.pb_play, 100, progress, false);
        //点击控件，就发出对应事件的广播
        notify_music.setOnClickPendingIntent(R.id.btn_play, broadIntent);
        //创建用于页面跳转到活动页的意图
        Intent intent2 = new Intent(ctx, MainActivity.class);
        //创建用于页面跳转的意图
        PendingIntent clickIntent = PendingIntent.getActivity(ctx, R.string.app_name, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建通知消息的构造器
        Notification .Builder builder = new Notification.Builder(ctx);
        //安卓8.0以上适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*String channelID = "Channel";
            String channelName = getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            //创建通知时指定channelID
            builder.setChannelId(channelID);*/
            builder = new Notification.Builder(ctx, getString(R.string.app_name));
            Log.d(TAG, "getNotify: 安装8.0以上适配");
        }
        builder.setContentIntent(clickIntent)
                .setContent(notify_music)
                .setTicker(song)
                .setSmallIcon(R.drawable.tt_s);
        Notification notify = builder.build();
        return notify;
    }

}
