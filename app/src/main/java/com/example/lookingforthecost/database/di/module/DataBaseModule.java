package com.example.lookingforthecost.database.di.module;

import android.content.Context;

import androidx.room.Room;

import com.example.lookingforthecost.database.AppDataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    private Context context;

    public DataBaseModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public AppDataBase getDataBase() {
        return Room.databaseBuilder(context
                , AppDataBase.class, "database")
                .build();
    }

}

