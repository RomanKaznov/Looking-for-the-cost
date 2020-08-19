package com.example.lookingforthecost.screens;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.controler.ColorController;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.screens.expenses.ExpensesActivity;
import com.example.lookingforthecost.view_model.MainViewModel;
import com.example.lookingforthecost.view_model.ModelFactory;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OutputExpensesActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> spendingName;
    Category category;
    @Inject
    AppDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        App.getComponent().inject(this);

        listView = findViewById(R.id.list);

        Intent intent = getIntent();
        category = intent.getParcelableExtra(ExpensesActivity.INTENT_TAG_NAME_CATEGORY);

        Toolbar toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(category.nameCategory);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spendingName = new ArrayList<>();

        ColorController.setExpenses(category, listView);//в зависимости от веса(важности) категории,установим фон

        MainViewModel mainViewModel = ViewModelProviders.of(this, new ModelFactory(dataBase)).get(MainViewModel.class);
        mainViewModel.getExpensesByNameCategory(category.nameCategory, dataBase).observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                for (int i = 0; i < expenses.size(); i++) {
                    spendingName.add(expenses.get(i).nameSpending + "\n" + expenses.get(i).spendMoney + " р." + "\n" + expenses.get(i).dateCreateExpenses);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, spendingName);
                listView.setAdapter(adapter);
            }
        });
    }

}