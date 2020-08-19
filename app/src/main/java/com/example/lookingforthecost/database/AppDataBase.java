package com.example.lookingforthecost.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lookingforthecost.database.dao.CategoryDao;
import com.example.lookingforthecost.database.dao.ExpensesDao;
import com.example.lookingforthecost.database.dao.FinPlanDao;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.database.entity.FinPlan;


@Database(entities = {Category.class, Expenses.class, FinPlan.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ExpensesDao expensesDao();

    public abstract FinPlanDao finPlanDao();

}