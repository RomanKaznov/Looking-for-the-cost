package com.example.lookingforthecost.view_model;

import android.os.AsyncTask;

import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.database.entity.FinPlan;


public class DataBaseRequest {

    private static AppDataBase dataBase;

    DataBaseRequest(AppDataBase appDataBase) {
        dataBase = appDataBase;
    }


    void deleteCategory(Category category) {
        new DeleteCategory().execute(category);
    }

    void addCategory(Category category) {
        new AddCategory().execute(category);
    }

    void updateCategory(Category category) {
        new UpdateCategory().execute(category);
    }

    void addExpenses(Expenses expenses) {
        new AddExpenses().execute(expenses);
    }

    void addPlan(FinPlan finPlan) {
        new AddPlan().execute(finPlan);
    }

    void updatePlan(FinPlan finPlan) {
        new UpdatePlan().execute(finPlan);
    }


    private static class AddCategory extends AsyncTask<Category, Void, Void> {
        @Override
        protected Void doInBackground(Category... categories) {
            dataBase.categoryDao().insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategory extends AsyncTask<Category, Void, Void> {
        @Override
        protected Void doInBackground(Category... categories) {
            dataBase.categoryDao().update(categories[0]);
            return null;
        }
    }

    private static class DeleteCategory extends AsyncTask<Category, Void, Void> {
        @Override
        protected Void doInBackground(Category... categories) {
            dataBase.expensesDao().deleteByName(categories[0].nameCategory);
            dataBase.categoryDao().delete(categories[0]);
            return null;
        }
    }

    private static class AddExpenses extends AsyncTask<Expenses, Void, Void> {
        @Override
        protected Void doInBackground(Expenses... expenses) {
            dataBase.expensesDao().insert(expenses[0]);
            return null;
        }
    }

    private static class AddPlan extends AsyncTask<FinPlan, Void, Void> {

        @Override
        protected Void doInBackground(FinPlan... finPlans) {
            dataBase.finPlanDao().insert(finPlans[0]);
            return null;
        }
    }

    private static class UpdatePlan extends AsyncTask<FinPlan, Void, Void> {

        @Override
        protected Void doInBackground(FinPlan... finPlans) {
            dataBase.finPlanDao().update(finPlans[0]);
            return null;
        }
    }


}
