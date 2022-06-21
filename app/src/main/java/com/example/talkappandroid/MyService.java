package com.example.talkappandroid;

import android.app.NotificationChannel;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.api.ContactAPI;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.repositories.ContactRepository;
import com.example.talkappandroid.repositories.MessageRepository;
import com.example.talkappandroid.utils.FirebaseNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyService extends FirebaseMessagingService {
    private final ContactRepository contactRepository;
    private final MessageRepository messageRepository;

    public MyService() {
        this.contactRepository = new ContactRepository(TalkAppApplication.contactsApi);
        this.messageRepository = new MessageRepository(TalkAppApplication.messageApi);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage messageReceived) {
        if (messageReceived.getNotification() != null) {
            // Create new notification to device.
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(this, "1")
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle(messageReceived.getNotification().getTitle())
                    .setContentText(messageReceived.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());

            // Parse received message by fields.
            String contactID = messageReceived.getData().get("contactID");
            String content = messageReceived.getData().get("content");
            String created = messageReceived.getData().get("created");

            // Update appropriate contact with pushed message content and date.
            FirebaseNotification notificationUpdate = new FirebaseNotification(contactID, content, created);
            contactRepository.updateContactOnNewMessage(notificationUpdate);

            // Push received message to dao.
            MessageItem message = new MessageItem(content, created, true);
            message.setContactID(contactID);
            messageRepository.pushMessageToDAO(message);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "myChannel", importance);
            channel.setDescription("Demo Channel");
            android.app.NotificationManager notificationManager = getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
