package com.example.lookingforthecost.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import com.example.lookingforthecost.OptionActivity;

import java.util.Calendar;

public class AlaramMeneger {

    private String statusAlaram = "Нет задач";


    public void launchNotifications(Context context) {

        SharedPreferences  mSettings = context.getSharedPreferences(OptionActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        if (mSettings.contains(OptionActivity.APP_ALARAM_MENAGER_STATUS)) {
            statusAlaram = mSettings.getString(OptionActivity.APP_ALARAM_MENAGER_STATUS, "Нет задач");
        }


        if(!statusAlaram.equals("выполняется")) {
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NotificationMessage.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = getCalendar(context);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), am.INTERVAL_DAY, pendingIntent);
            editor.putString(OptionActivity.APP_ALARAM_MENAGER_STATUS, "выполняется");
            editor.apply();

            Toast toast = Toast.makeText(context,
                    String.valueOf("DSD"   ), Toast.LENGTH_LONG);
            toast.show();
        }

    }









    public Calendar getCalendar(Context context){
        SharedPreferences mSettings = context.getSharedPreferences(OptionActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor meditor = mSettings.edit();

        int hour = 0;
        int minute = 0;
        if (mSettings.contains(OptionActivity.APP_PREFERENCES_HOUR)) {
            hour = mSettings.getInt(OptionActivity.APP_PREFERENCES_HOUR, 19);
            minute = mSettings.getInt(OptionActivity.APP_PREFERENCES_MINUTE, 30);
        } else {
            hour = 19;
            minute = 30;
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void startControlDay(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(OptionActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor meditor = mSettings.edit();
        AlarmManager am = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE,51);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(context,ControlDay.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 200, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);



    }



}







      /* if (android.os.Build.VERSION.SDK_INT >= 19) {
           assert alarmManager != null;
           alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

       } else {
           assert alarmManager != null;
           alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
       }**/


