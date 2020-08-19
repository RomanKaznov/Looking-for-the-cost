package com.example.lookingforthecost.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.widget.Toast;

import com.example.lookingforthecost.screens.NotificationSettingsActivity;

import java.util.Calendar;

public class AlarmManagerCenter {

    public void launchNotifications(Context context) {
        AlarmManagerStatus spStatus = new AlarmManagerStatus(context);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = getCalendar(context);
        if (am != null) {
            am.cancel(pendingIntent);
            if (!spStatus.getStatusAlarmManager()) {
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                spStatus.setStatusAlarmManager(true);
                Toast toast = Toast.makeText(context,
                        String.valueOf(calendar.getTime()), Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public Calendar getCalendar(Context context) {
        int hour;
        int minute;
        SharedPreferences mSettings = context.getSharedPreferences(NotificationSettingsActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (mSettings.contains(AlarmManagerStatus.APP_PREFERENCES_HOUR)) {
            hour = mSettings.getInt(NotificationSettingsActivity.APP_PREFERENCES_HOUR, 19);
            minute = mSettings.getInt(NotificationSettingsActivity.APP_PREFERENCES_MINUTE, 30);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
        }
        return calendar;
    }


}







