package com.example.reminder_sefa;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class myChannel extends BroadcastReceiver {

    private static final long TIME_OUT = 1000;

    public static final String CHANNEL_ID = "channel1";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("myChannel", "123");

        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "The Reminder Notification",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences("deneme2", Context.MODE_PRIVATE);
        String www = sharedPreferences.getString("NAME", "Kayıt Yok");
        Uri www2 = Uri.parse(www);


        Notification notification = builder.setContentTitle("Time's Up! Come Here..")
                .setSound(null)
                .setContentText("For more information please tap here")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .build();
        RingtoneManager.getRingtone(context, www2).play();

        //Toast.makeText(context.getApplicationContext(), www, Toast.LENGTH_SHORT).show();
        Log.i("settings", String.valueOf(www2));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        notificationManager.notify(1, notification);

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { }}, TIME_OUT);
        RingtoneManager.getRingtone(context, www2).stop();*/
    }
}
