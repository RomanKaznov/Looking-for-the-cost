package com.example.lookingforthecost.screens.expenses.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Expenses;
import com.example.lookingforthecost.database.model.FinPlan;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Category>> categoryLiveData = App.getInstance().getCategoryDao().getAllLiveData();
    private LiveData<List<Expenses>> spendingLiveData = App.getInstance().getExpensesDao().getAllLiveData();
    private LiveData<List<Category>> categoryNecessaryExpenses = App.getInstance().getCategoryDao().findByImportance(1);
    private LiveData<List<Category>> categoryOptionalExpenses = App.getInstance().getCategoryDao().findByImportance(0);
    private LiveData<List<FinPlan>> finPlan = App.getInstance().getFinPlanDao().getFinPlan();

    public LiveData<List<Expenses>> getSpendingLiveData() {
        return spendingLiveData;
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


    public LiveData<List<FinPlan>> getFinPlan() {
        return  finPlan;
    }

}
