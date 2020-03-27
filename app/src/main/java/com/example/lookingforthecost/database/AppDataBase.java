package com.example.lookingforthecost.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lookingforthecost.database.dao.CategoryDao;
import com.example.lookingforthecost.database.dao.DateNotificationDao;
import com.example.lookingforthecost.database.dao.FinPlanDao;
import com.example.lookingforthecost.database.dao.SpendingDao;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.DateNotification;
import com.example.lookingforthecost.database.model.FinPlan;
import com.example.lookingforthecost.database.model.Spending;

@Database(entities = {Category.class, DateNotification.class, Spending.class, FinPlan.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract SpendingDao spendingDao();

    public abstract DateNotificationDao dateNotificationDao();

    public abstract FinPlanDao finPlanDao();


}