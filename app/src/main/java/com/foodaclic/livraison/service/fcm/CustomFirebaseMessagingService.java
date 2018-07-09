package com.foodaclic.livraison.service.fcm;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.activity.MainActivity;
import com.foodaclic.livraison.activity.NotificationsActivity;

import com.foodaclic.livraison.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = CustomFirebaseMessagingService.class.getName();

    public CustomFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());


                    try {
                        String title = remoteMessage.getData().get("title");
                        String body = remoteMessage.getData().get("content");

                        sendNotif(title, body);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }


    private void sendNotif(String title, String body) {

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
                .setVibrate(new long[]{10, 300, 1000, 2000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(intent);

        PendingIntent resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }
}