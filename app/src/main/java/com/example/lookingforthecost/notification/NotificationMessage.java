package com.example.lookingforthecost.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.main.MainActivity;

import java.util.Calendar;

public class NotificationMessage extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    Intent ri = new Intent(context,MainActivity.class);
    ri.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(context,100,ri,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Напоминание")
                        .setContentText("Пора покормить кота")
                        .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            CharSequence channelName = "c_name";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel("3260", channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            nm.createNotificationChannel(notificationChannel);
            builder.setChannelId(notificationChannel.getId());
        }else {

        }




        nm.notify(100,builder.build());



    }




    public static void notify(final Context context, Notification build) {

    }


}
