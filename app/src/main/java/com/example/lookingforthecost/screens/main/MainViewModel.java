package com.example.lookingforthecost.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Category>> categoryLiveData = App.getInstance().getCategoryDao().getAllLiveData();
    private LiveData<List<Spending>> spendingLiveData = App.getInstance().getSpendingDao().getAllLiveData();

    public LiveData<List<Spending>> getSpendingLiveData() {
        return spendingLiveData;
    }

    public  LiveData<List<Category>> getCategoryLiveData() {
        return categoryLiveData;
    }
}
