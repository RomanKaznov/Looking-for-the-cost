package com.example.lookingforthecost;

import android.app.Application;

import androidx.room.Room;

import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.CategoryDao;
import com.example.lookingforthecost.database.DateNotificationDao;
import com.example.lookingforthecost.database.SpendingDao;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.DateNotification;

public class App extends Application {
    private AppDataBase dataBase;
    private CategoryDao categoryDao;
    private DateNotificationDao dateNotificationDao;
    private SpendingDao spendingDao;
    public static App instance;


    public static App getInstance() {

        return instance;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                .build();
        categoryDao = dataBase.categoryDao();
        dateNotificationDao = dataBase.dateNotificationDao();
        spendingDao = dataBase.spendingDao();

    }

    public AppDataBase getDataBase() {

        return dataBase;
    }

    public void setDataBase(AppDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public DateNotificationDao getDateNotificationDao() {
        return dateNotificationDao;
    }

    public SpendingDao getSpendingDao() {
        return spendingDao;
    }


}
