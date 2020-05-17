package com.example.lookingforthecost.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lookingforthecost.database.dao.CategoryDao;
import com.example.lookingforthecost.database.dao.DateNotificationDao;
import com.example.lookingforthecost.database.dao.ExpensesDao;
import com.example.lookingforthecost.database.dao.FinPlanDao;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.DateNotification;
import com.example.lookingforthecost.database.model.Expenses;
import com.example.lookingforthecost.database.model.FinPlan;

@Database(entities = {Category.class, DateNotification.class, Expenses.class, FinPlan.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ExpensesDao spendingDao();

    public abstract DateNotificationDao dateNotificationDao();

    public abstract FinPlanDao finPlanDao();


}