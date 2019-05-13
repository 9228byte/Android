package com.example.custom;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * NotifySimpleActivity
 *
 * @author lao
 * @date 2019/4/21
 */

public class NotifySimpleActivity extends AppCompatActivity implements OnClickListener {
    private EditText et_title;
    private EditText et_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_simple);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);
        findViewById(R.id.btn_send_simple).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_simple) {
            String title = et_title.getText().toString();
            String message = et_message.getText().toString();
            sendSimpleNotify(title, message);
        }
    }

    private void sendSimpleNotify(String title, String message) {
        Intent clickIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, R.string.app_name, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建一个通知消息的构造器
        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*String channelID = "1";
            String channelName = "channel_name";
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            //创建通知时指定channelID
            builder.setChannelId(channelID);*/
            builder = new Notification.Builder(this, getString(R.string.app_name));
        }
        builder.setContentIntent(contentIntent)        //设置内容点击意图
                .setSmallIcon(R.drawable.ic_app)    //设置状态栏里的图标
                //.setSubText("这里是副本")    //设置状态栏里面的附加说明文本
                .setTicker("提示消息来了")        //状态栏提示消息
                .setWhen(System.currentTimeMillis())        //设置退总时间
                //设置通知栏里面的大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_app))
                .setContentTitle(title)
                .setContentText(message);
        //根据消息构造器构建一个通知对象
        Notification notify = builder.build();
        NotificationManager notificationManager  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(R.string.app_name, notify);
    }

}
