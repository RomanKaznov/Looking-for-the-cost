package com.example.lookingforthecost.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.database.model.Category;

import java.util.ArrayList;

public class ControlDay extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new DelutAll().execute();
    }



    static class DelutAll extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            App.getInstance().getFinPlanDao().nukeTable();
            return null;
        }
    }


}
