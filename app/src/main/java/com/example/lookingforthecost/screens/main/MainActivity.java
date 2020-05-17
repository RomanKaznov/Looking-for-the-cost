package com.example.lookingforthecost.screens.main;

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

import com.example.lookingforthecost.OptionActivity;
import com.example.lookingforthecost.controler.ColorController;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.FinPlan;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.notification.AlaramMeneger;
import com.example.lookingforthecost.screens.expenses.view_model.MainViewModel;
import com.example.lookingforthecost.screens.expenses.SpendingActivity;

import java.util.List;

import static com.example.lookingforthecost.R.id.option;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ColorController colorController;
    Button myFinances, myExpenses;
    TextView optionalBudget, necassaryBudget;
    TextView remainsOptional, remainsNecassary;
    Toolbar toolbar;
    FinPlan finPlan;
    Context context;
    AlaramMeneger alaramMeneger;

    public static String TAG_INTENT_PLAN = "PLAN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alaramMeneger = new AlaramMeneger();
        alaramMeneger.launchNotifications(getApplicationContext());
        //alaramMeneger.startControlDay(getApplicationContext());
        context = this;
        colorController = new ColorController();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        optionalBudget = findViewById(R.id.optionalBudget);
        remainsOptional = findViewById(R.id.remainsOptional);

        necassaryBudget = findViewById(R.id.NecassaryBudget);
        remainsNecassary = findViewById(R.id.remainsNecassary);

        myFinances = findViewById(R.id.myFinances);
        myExpenses = findViewById(R.id.mySpendings);

        myFinances.setOnClickListener(this);
        myExpenses.setOnClickListener(this);


        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//работа с планом
        mainViewModel.getFinPlan().observe(this, new Observer<List<FinPlan>>() {//работа с планом
            @Override
            public void onChanged(List<FinPlan> finPlans) {//получим план
                    finPlan = finPlans.get(0);//т.к он всегда один,запрашиваем по 0 индексу
                    necassaryBudget.setText(String.valueOf(finPlan.necessaryBudget));//выведем обязательный бюджет
                    optionalBudget.setText(String.valueOf(finPlan.optionalBudget));//выведем необязательный бюджет

                    mainViewModel.getCategoryNecessaryExpenses().observe((LifecycleOwner) context, new Observer<List<Category>>() {//получаем категории с весом 1(необходимые расходы)
                    @Override
                    public void onChanged(final List<Category> categories) {
                       int remains = getRemainsBudget(categories,finPlan.necessaryBudget);//метод возвращает остаток от бюджета
                        remainsNecassary.setText(String.valueOf(remains));
                        colorController.setColorTextView(remains,remainsNecassary);//метод выведет текст, если остаток окажется отрицательным,зададаст ему красный цвет
                    }
                });
                    mainViewModel.getCategoryOptionalExpenses().observe((LifecycleOwner) context, new Observer<List<Category>>() {//получаем категории с весом 0(необязательные расходы)
                        @Override
                        public void onChanged(List<Category> categories) {
                           int remains = getRemainsBudget(categories,finPlan.optionalBudget);
                            remainsOptional.setText(String.valueOf(remains));
                            colorController.setColorTextView(remains,remainsOptional);
                        }
                    });

            }
        });
//






    }

   private int getRemainsBudget(List<Category> categories, int budget) {
        int sumExpenses = 0;
        for (int i = 0; i < categories.size(); i++) {
            sumExpenses += categories.get(i).amountSpending;//получим сумму расходов из всех категорий определенного веса
        }
        return budget - sumExpenses;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
        if (item.getItemId() == option) {
            startActivity(intent);
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SpendingActivity.class);
        Intent intent2 = new Intent(this, MyPlan.class);

        if (finPlan != null)
            intent2.putExtra(TAG_INTENT_PLAN, finPlan);

        switch (view.getId()) {
            case R.id.myFinances:
                startActivity(intent2);
                break;

            case R.id.mySpendings:
                startActivity(intent);
                break;
        }


    }

}
