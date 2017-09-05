package com.donbosco.android.porlosjovenes.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.activities.SplashActivity;
import com.donbosco.android.porlosjovenes.constants.NotificationConstants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class PljFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if(remoteMessage != null && remoteMessage.getData() != null)
        {
            Map<String, String> data = remoteMessage.getData();
            String body = data.get(NotificationConstants.DATA_KEY_BODY);
            sendNotification(body);
        }
    }

    private void sendNotification(String body)
    {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_stat_noti_small)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationConstants.PUSH_NOTIFICATION_ID, notificationBuilder.build());
    }
}
