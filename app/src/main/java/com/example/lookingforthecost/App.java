package com.example.lookingforthecost;

import android.app.Application;

import com.example.lookingforthecost.database.di.components.AppComponent;
import com.example.lookingforthecost.database.di.components.DaggerAppComponent;
import com.example.lookingforthecost.database.di.module.DataBaseModule;

public class App extends Application {

    private static AppComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerAppComponent.builder().dataBaseModule(new DataBaseModule(this)).build();
    }

    public static AppComponent getComponent() {
        return mApplicationComponent;
    }

}
