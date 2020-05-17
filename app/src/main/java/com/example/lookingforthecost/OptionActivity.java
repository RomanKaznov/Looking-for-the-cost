package com.example.lookingforthecost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;
import com.example.lookingforthecost.notification.NotificationMessage;
import com.example.lookingforthecost.screens.main.MainActivity;

import java.util.Calendar;

import static com.example.lookingforthecost.R.id.option;
import static com.example.lookingforthecost.R.id.save;

public class OptionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinnerHours;
    private Spinner spinnerMinute;
    private CheckBox onNotification;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_HOUR = "Hour";
    public static final String APP_PREFERENCES_MINUTE = "Minute";
    public static final String APP_ALARAM_MENAGER_STATUS = "Status";

    SharedPreferences mSettings;
    private AlarmManager am;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);


        spinnerHours = findViewById(R.id.hour);
        spinnerMinute = findViewById(R.id.minute);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


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
            SharedPreferences.Editor editor = mSettings.edit();
            mSettings.edit().remove(OptionActivity.APP_PREFERENCES).apply();
            mSettings.edit().remove(OptionActivity.APP_ALARAM_MENAGER_STATUS).apply();
            editor.putInt(APP_PREFERENCES_HOUR, Integer.parseInt(String.valueOf(spinnerHours.getSelectedItem())));
            editor.putInt(APP_PREFERENCES_MINUTE, Integer.parseInt((String) spinnerMinute.getSelectedItem()));

            editor.apply();
            startActivity(intent);
        }
        return true;
    }



}
