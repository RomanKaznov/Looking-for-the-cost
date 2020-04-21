package com.example.lookingforthecost.screens.spending.adapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;
import com.example.lookingforthecost.screens.spending.SpendingActivity;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Category>> categoryLiveData = App.getInstance().getCategoryDao().getAllLiveData();
    private LiveData<List<Spending>> spendingLiveData = App.getInstance().getSpendingDao().getAllLiveData();
    private AdapterListSpending adapterListSpending = new AdapterListSpending();

    public AdapterListSpending getAdapterListSpending(SpendingActivity spendingActivity, Observer<List<Spending>> observer) {
        return adapterListSpending;
    }

   public LiveData<List<Spending>> getSpendingLiveData() {
        return spendingLiveData;
    }

    public  LiveData<List<Category>> getCategoryLiveData() {
        return categoryLiveData;
    }
}
