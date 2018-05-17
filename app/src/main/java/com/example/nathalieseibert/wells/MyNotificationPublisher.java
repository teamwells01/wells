package com.example.nathalieseibert.wells;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.graphics.BitmapFactory;

public class MyNotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, Repeating_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher_round))
                .setContentTitle("Wells benachrichtigt!")
                .setContentText("Du musst heute mehr Wasser trinken!")
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());

    }
}