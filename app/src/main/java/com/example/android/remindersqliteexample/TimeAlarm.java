package com.example.android.remindersqliteexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by android on 5/31/2017.
 */

public class TimeAlarm extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        String event = extra.getString("event");


        Bitmap bigIcon = BitmapFactory.decodeResource(context.getResources() , R.mipmap.ic_launcher);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent1 = new Intent(context , ShowReminderMessage.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("ReminderMsg" , event);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.small_notify_icon)
                //.setLargeIcon(bigIcon)

                .setColor(46254)
                .setAutoCancel(true)
                .setContentTitle("Reminder")
                .setContentText(event)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());



    }
}
