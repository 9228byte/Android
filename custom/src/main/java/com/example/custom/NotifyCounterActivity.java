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
 * NotifyCounterActivity
 *
 * @author lao
 * @date 2019/4/21
 */

public class NotifyCounterActivity extends AppCompatActivity implements OnClickListener {
    private EditText et_title;
    private EditText et_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_counter);
        et_message = findViewById(R.id.et_message);
        et_title = findViewById(R.id.et_title);
        findViewById(R.id.btn_send_counter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_counter) {
            String title = et_title.getText().toString();
            String message = et_message.getText().toString();
            sendCounterNotify(title, message);
        }
    }

    private void sendCounterNotify(String title, String messase) {
        Intent cancelIntent = new Intent(this, MainActivity.class);
        PendingIntent deleteIntent = PendingIntent.getActivity(this, R.string.app_name, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*String channelID = "1";
            String channelName = getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            //创建通知时指定channelID
            builder.setChannelId(channelID);*/
            builder = new Notification.Builder(this, getString(R.string.app_name));
        }
        builder.setDeleteIntent(deleteIntent)       //设置内容删除意图
                .setAutoCancel(true)        //设置自动删除
                .setUsesChronometer(true)       //设置是否显示计数器
                .setProgress(100, 99,false)     //设置进度条与当前进度
                .setNumber(99)          //设置通知栏右下方的数字
                .setSmallIcon(R.drawable.ic_app)        //设置图标
                .setTicker("提示消息来啦")        //设置状态栏里面的标题文本
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_app))//设置大图标
                .setContentTitle(title)     //标题文本
                .setContentText(messase);       //内容文本
        Notification notify = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(R.string.app_name, notify);
    }
}
