package com.example.lookingforthecost.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.database.entity.FinPlan;

import java.util.List;

public class MainViewModel extends ViewModel {
    private DataBaseRequest dataBaseRequest;
    private LiveData<List<Category>> categoryLiveData;
    private LiveData<List<Expenses>> expensesLiveData;
    private LiveData<List<Category>> categoryNecessaryExpenses;
    private LiveData<List<Category>> categoryOptionalExpenses;
    private LiveData<List<FinPlan>> finPlan;
    private static AppDataBase dataBase;

    public MainViewModel(AppDataBase db) {
        dataBase = db;
        dataBaseRequest = new DataBaseRequest(db);
        categoryLiveData = db.categoryDao().getAllCategoriesLiveData();
        expensesLiveData = db.expensesDao().getAllExpensesLiveData();
        categoryNecessaryExpenses = db.categoryDao().LiveDataFindByImportance(1);
        categoryOptionalExpenses = db.categoryDao().LiveDataFindByImportance(0);
        finPlan = db.finPlanDao().getFinPlanLiveDate();
    }

    public LiveData<List<Expenses>> getExpensesLiveData() {
        return expensesLiveData;
    }

    public LiveData<List<Category>> getCategoryLiveData() {
        return categoryLiveData;
    }

    public LiveData<List<Category>> getCategoryNecessaryExpenses() {
        return categoryNecessaryExpenses;
    }

    public LiveData<List<Category>> getCategoryOptionalExpenses() {
        return categoryOptionalExpenses;
    }

    public LiveData<List<Expenses>> getExpensesByNameCategory(String nameCategory, AppDataBase dataBase) {
        return dataBase.expensesDao().findExpensesByNameCategory(nameCategory);
    }

    public LiveData<List<FinPlan>> getFinPlan() {
        return finPlan;
    }

    public void addCategory(Category category) {
        dataBaseRequest.addCategory(category);
    }

    public LiveData<List<Expenses>> getExpensesByName(String name) {
        return dataBase.expensesDao().findExpensesByName(name);
    }

    public void deleteCategory(Category category) {
        dataBaseRequest.deleteCategory(category);
    }

    public void updateCategory(Category category) {
        dataBaseRequest.updateCategory(category);
    }

    public void addExpenses(Expenses expenses) {
        dataBaseRequest.addExpenses(expenses);
    }

    public void addPlan(FinPlan finPlan) {
        dataBaseRequest.addPlan(finPlan);
    }

    public void updatePlan(FinPlan finPlan) {
        dataBaseRequest.updatePlan(finPlan);
    }


}