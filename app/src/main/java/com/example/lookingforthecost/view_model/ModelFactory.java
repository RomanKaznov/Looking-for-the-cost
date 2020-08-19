package com.example.lookingforthecost.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lookingforthecost.database.AppDataBase;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppDataBase db;

    public ModelFactory(AppDataBase db) {
        super();
        this.db = db;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (MainViewModel.class == modelClass) {
            return (T) new MainViewModel(db);
        }
        return null;
    }
}
