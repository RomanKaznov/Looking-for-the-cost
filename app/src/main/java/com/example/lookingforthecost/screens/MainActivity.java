package com.example.lookingforthecost.screens;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.screens.controler.TextController;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.screens.expenses.ExpensesActivity;
import com.example.lookingforthecost.value_expenses.ValueExpenses;
import com.example.lookingforthecost.view_model.ModelFactory;
import com.example.lookingforthecost.notification.AlarmManagerCenter;
import com.example.lookingforthecost.screens.controler.ColorController;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.FinPlan;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.view_model.MainViewModel;

import java.util.List;

import javax.inject.Inject;

import static com.example.lookingforthecost.R.id.option;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button myFinances, myExpenses;
    TextView optionalBudget, necessaryBudget;
    TextView remainsOptional, remainsNecessary;
    Toolbar toolbar;
    FinPlan finPlan;
    Context context;
    AlarmManagerCenter AlarmManagerCenter;
    @Inject
    AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);

        AlarmManagerCenter = new AlarmManagerCenter();
        AlarmManagerCenter.launchNotifications(getApplicationContext());
        context = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        optionalBudget = findViewById(R.id.optionalBudget);
        remainsOptional = findViewById(R.id.remainsOptional);
        necessaryBudget = findViewById(R.id.NecassaryBudget);
        remainsNecessary = findViewById(R.id.remainsNecassary);

        myFinances = findViewById(R.id.myPlan);
        myExpenses = findViewById(R.id.mySpending);
        myFinances.setOnClickListener(this);
        myExpenses.setOnClickListener(this);

        final MainViewModel mainViewModel = ViewModelProviders.of(this, new ModelFactory(appDataBase)).get(MainViewModel.class);
//работа с планом
        mainViewModel.getFinPlan().observe(this, new Observer<List<FinPlan>>() {//работа с планом
            @Override
            public void onChanged(final List<FinPlan> finPlans) {//получим план
                if (finPlans.size() > 0) {
                    finPlan = finPlans.get(0);//т.к он всегда один,запрашиваем по 0 индексу
                    necessaryBudget.setText(TextController.getFormatStr((String) getResources().getText(R.string.nameVariable3), finPlan.necessaryBudget));//выведем обязательный бюджет
                    optionalBudget.setText(TextController.getFormatStr((String) getResources().getText(R.string.nameVariable3), finPlan.optionalBudget));//выведем необязательный бюджет
                    mainViewModel.getCategoryNecessaryExpenses().observe((LifecycleOwner) context, new Observer<List<Category>>() {//получаем категории с весом 1(необходимые расходы)
                        @Override
                        public void onChanged(final List<Category> categories) {
                            if (finPlan != null) {
                                int remains = ValueExpenses.getRemainsBudget(categories, finPlan.necessaryBudget);// остаток от бюджета
                                remainsNecessary.setText(TextController.getFormatStr((String) getResources().getText(R.string.nameVariable4), remains));
                                ColorController.setColorTextView(remains, remainsNecessary);//метод выведет текст, если остаток окажется отрицательным,зададаст ему красный цвет
                            }
                        }
                    });
                    mainViewModel.getCategoryOptionalExpenses().observe((LifecycleOwner) context, new Observer<List<Category>>() {//получаем категории с весом 0(необязательные расходы)
                        @Override
                        public void onChanged(List<Category> categories) {
                            if (finPlan != null) {
                                int remains = ValueExpenses.getRemainsBudget(categories, finPlan.optionalBudget);
                                ColorController.setColorTextView(remains, remainsOptional);
                                remainsOptional.setText(TextController.getFormatStr((String) getResources().getText(R.string.nameVariable4), remains));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), NotificationSettingsActivity.class);
        if (item.getItemId() == option) {
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.myPlan:
                    TransferActivity.startActivityWithTransferPlan(this, new MyPlanActivity(), finPlan, MyPlanActivity.TAG_INTENT_PLAN);
                    break;
                case R.id.mySpending:
                    Intent intent = new Intent(this, ExpensesActivity.class);
                    startActivity(intent);
                    break;
            }


    }

}
