package com.example.lookingforthecost;

import android.app.Application;

import androidx.room.Room;

import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.dao.CategoryDao;
import com.example.lookingforthecost.database.dao.DateNotificationDao;
import com.example.lookingforthecost.database.dao.ExpensesDao;
import com.example.lookingforthecost.database.dao.FinPlanDao;

public class App extends Application {
    private AppDataBase dataBase;
    private CategoryDao categoryDao;
    private DateNotificationDao dateNotificationDao;
    private ExpensesDao expensesDao;
    private FinPlanDao finPlan;
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
        expensesDao = dataBase.spendingDao();
        finPlan = dataBase.finPlanDao();

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

    public ExpensesDao getExpensesDao() {
        return expensesDao;
    }

    public FinPlanDao getFinPlanDao() {
        return finPlan;
    }


}
