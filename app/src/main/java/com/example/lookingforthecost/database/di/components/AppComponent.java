package com.example.lookingforthecost.database.di.components;

import com.example.lookingforthecost.database.di.module.DataBaseModule;
import com.example.lookingforthecost.notification.NotificationReceiver;
import com.example.lookingforthecost.value_expenses.ValueExpenses;
import com.example.lookingforthecost.screens.expenses.ExpensesActivity;
import com.example.lookingforthecost.screens.expenses.CreateCategoryActivity;
import com.example.lookingforthecost.screens.expenses.CreateExpensesActivity;
import com.example.lookingforthecost.screens.MainActivity;
import com.example.lookingforthecost.screens.OutputExpensesActivity;
import com.example.lookingforthecost.screens.MyPlanActivity;
import com.example.lookingforthecost.view_model.DataBaseRequest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataBaseModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(ExpensesActivity activity);

    void inject(CreateCategoryActivity activity);

    void inject(CreateExpensesActivity activity);

    void inject(OutputExpensesActivity activity);

    void inject(MyPlanActivity activity);

    void inject(NotificationReceiver activity);

    void inject(ValueExpenses myClass);

    void inject(DataBaseRequest myClass);
}
