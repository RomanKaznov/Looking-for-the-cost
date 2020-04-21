package com.example.lookingforthecost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;

import com.example.lookingforthecost.notification.NotificationMessage;
import com.example.lookingforthecost.screens.main.MainActivity;

import java.util.Calendar;

import static com.example.lookingforthecost.R.id.option;
import static com.example.lookingforthecost.R.id.save;

public class OptionActivity extends AppCompatActivity {
    private AlarmManager am;
    private int hour = 12;
    private int minute = 26;
    private Spinner spinnerHours;
    private Spinner spinnerMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        spinnerHours = findViewById(R.id.hour);
        spinnerMinute = findViewById(R.id.minute);




        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this, NotificationMessage.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

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
            hour =Integer.parseInt((String) spinnerHours.getSelectedItem());
            minute =Integer.parseInt((String) spinnerMinute.getSelectedItem());


            startActivity(intent);
        }
        return true;
    }




}
