package com.example.lookingforthecost.notification;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lookingforthecost.screens.NotificationSettingsActivity;

public class AlarmManagerStatus {

    private String statusAlarm = "Нет задач";
    private final String ALARM_START = "выполняется";
    private final String ALARM_STOP = "Нет задач";
    private final String ALARM_NON_REPEAT_START = "выполняется";
    private final String ALARM_NON_REPEAT_STOP = "Нет задач";
    private SharedPreferences mSettings;
    private SharedPreferences.Editor editor;
    public static final String APP_PREFERENCES_HOUR = "Hour";
    public static final String APP_PREFERENCES_MINUTE = "Minute";

    public AlarmManagerStatus(Context context) {
        mSettings = context.getSharedPreferences(NotificationSettingsActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
    }

    protected void setStatusAlarmManager(boolean statusAlarmManager) {//метод позволяет задать статус am
        mSettings.edit().remove(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS).apply();
        if (statusAlarmManager) {
            editor.putString(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS, ALARM_START);
        } else {
            editor.putString(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS, ALARM_STOP);
        }
        editor.apply();
    }

    protected void setNonRepeatAlarmManagerStatus(boolean statusAlarmManager) {//метод позволяет задать статус неповторяющего am(для работы с апи версии >=19)
        mSettings.edit().remove(ALARM_NON_REPEAT_START).apply();
        mSettings.edit().remove(ALARM_NON_REPEAT_STOP).apply();
        if (statusAlarmManager) {
            editor.putString(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS, ALARM_NON_REPEAT_START);
        } else {
            editor.putString(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS, ALARM_NON_REPEAT_STOP);
        }
        editor.apply();
    }

    protected boolean getStatusAlarmManager() {//при запуске am в файл записывается статус ALARM_START,метод вернет true если в файле имеется запись
        if (mSettings.contains(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS)) {
            statusAlarm = mSettings.getString(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS, ALARM_STOP);
        }
        return statusAlarm.equals(ALARM_START);
    }

    protected boolean getNonRepeatAlarmManagerStatus() {//при запуске am в файл записывается статус ALARM_START,метод вернет true если в файле имеется запись
        if (mSettings.contains(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS)) {
            statusAlarm = mSettings.getString(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS, ALARM_NON_REPEAT_STOP);
        }
        return statusAlarm.equals(ALARM_START);
    }

    public void setTimeNotification(int hour, int minute) {// метод позволяет задать время запуска am
        mSettings.edit().remove(NotificationSettingsActivity.APP_ALARM_MANAGER_STATUS).apply();
        editor.putInt(APP_PREFERENCES_HOUR, hour);
        editor.putInt(APP_PREFERENCES_MINUTE, minute);
        editor.apply();
        setStatusAlarmManager(false);
    }


}