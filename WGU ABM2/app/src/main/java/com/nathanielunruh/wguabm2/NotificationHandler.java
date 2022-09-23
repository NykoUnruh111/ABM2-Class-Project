package com.nathanielunruh.wguabm2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {

    public void scheduleNotification (Notification notification , long delay, Context context) {

        Intent notificationIntent = new Intent(context, NotificationPublisher.class );
        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION_ID , 1 );
        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION , notification);
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 0, notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT );
        long futureInMillis = delay - System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent);
    }
    public Notification getNotification (String content, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context, "0");
        builder.setContentTitle( "WGU Scheduler!" );
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId("0");
        return builder.build();
    }

}
