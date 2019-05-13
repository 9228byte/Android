package com.example.custom.util;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

/**
 * NotifyUtil
 *
 * @author lao
 * @date 2019/4/25
 */
public class NotifyUtil {
    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotifyChannel(Context ctx, String channelId) {
        NotificationChannel channel = new NotificationChannel(channelId, "Channel", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setSound(null, null);
        channel.setLightColor(Color.RED);
        channel.setShowBadge(true);
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}
