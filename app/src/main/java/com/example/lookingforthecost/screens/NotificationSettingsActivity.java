package com.example.lookingforthecost.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import com.example.lookingforthecost.R;
import com.example.lookingforthecost.notification.AlarmManagerCenter;
import com.example.lookingforthecost.notification.AlarmManagerStatus;


import java.util.Calendar;

import static com.example.lookingforthecost.R.id.save;

public class NotificationSettingsActivity extends AppCompatActivity {

    private Spinner spinnerHours;
    private Spinner spinnerMinute;
    private CheckBox onNotification;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_HOUR = "Hour";
    public static final String APP_PREFERENCES_MINUTE = "Minute";
    public static final String APP_ALARM_MANAGER_STATUS = "Status";
    public AlarmManagerStatus alarmManagerStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        alarmManagerStatus = new AlarmManagerStatus(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerHours = findViewById(R.id.hour);
        spinnerMinute = findViewById(R.id.minute);
        AlarmManagerCenter am = new AlarmManagerCenter();
        Calendar calendar = am.getCalendar(this);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        spinnerHours.setSelection(hours - 1);
        spinnerMinute.setSelection(minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_option, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (item.getItemId() == save) {
            alarmManagerStatus.setTimeNotification(Integer.parseInt(String.valueOf(spinnerHours.getSelectedItem())), Integer.parseInt((String) spinnerMinute.getSelectedItem()));
            startActivity(intent);
        }
        return true;
    }

}
