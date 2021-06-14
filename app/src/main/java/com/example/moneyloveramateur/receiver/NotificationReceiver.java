package com.example.moneyloveramateur.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.moneyloveramateur.MyApplication;
import com.example.moneyloveramateur.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setContentTitle("Money Lover Amateur")
                .setContentText("Bạn sẵn sàng để cập nhật giao dịch hôm nay chưa?")
                .setSmallIcon(R.drawable.ic_small_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null){
            notificationManager.notify(1, notification);
        }
    }
}
