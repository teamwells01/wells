package com.example.nathalieseibert.wells;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class MyNotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, Repeating_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.NotificationN))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher_round))
                .setContentTitle("Wells benachrichtigt!")
                .setContentText("Du musst heute mehr Wasser trinken!")
                .setAutoCancel(true).setWhen(when)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        try {
            notificationManager.notify(100, builder.build());
        } catch (NullPointerException e) {
            Toast.makeText(context, "Error",
                    Toast.LENGTH_LONG).show();
        }

    }
}
