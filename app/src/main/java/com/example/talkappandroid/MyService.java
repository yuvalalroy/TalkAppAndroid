package com.example.talkappandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "1";

    public MyService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null){
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            int notificationId = 1;
            notificationManager.notify(notificationId, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            CharSequence name = getString(R.string.channel_name);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            String description = getString((R.string.channel_description));
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService((NotificationManager.class));
            notificationManager.createNotificationChannel(channel);
        }
    }
}