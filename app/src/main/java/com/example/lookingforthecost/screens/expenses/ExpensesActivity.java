package com.example.lookingforthecost.screens.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.view_model.ModelFactory;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.screens.expenses.adapters.AdapterListCategory;
import com.example.lookingforthecost.view_model.MainViewModel;
import com.example.lookingforthecost.screens.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ExpensesActivity extends AppCompatActivity {
    TextView addCategory;
    private AdapterListCategory adapterListCategory;
    RecyclerView recyclerView;
    ArrayList<Expenses> arrayList;
    Toolbar toolbar;
    @Inject
    AppDataBase appDataBase;
    public static final String INTENT_TAG_NAME_CATEGORY = "ИМЯ КАТЕГОРИИ";
    static AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setContentView(R.layout.activity_expenses);
        createToolbarExpenses();

        recyclerView = findViewById(R.id.list);
        addCategory = findViewById(R.id.addCategory);

        arrayList = new ArrayList<>();
        final MainViewModel mainViewModel = ViewModelProviders.of(this, new ModelFactory(appDataBase)).get(MainViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterListCategory = new AdapterListCategory(getApplicationContext(), appDataBase);

        recyclerView.setAdapter(adapterListCategory);
        db = appDataBase;

        mainViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories.size() != 0) {
                    adapterListCategory.setItems(categories);
                }
            }
        });

        mainViewModel.getExpensesLiveData().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                if (expenses.size() != 0) {
                    adapterListCategory.setItemsAdapterSpending(expenses);
                }
            }
        });

        final Intent intent2 = new Intent(this, CreateCategoryActivity.class);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(intent2);
            }
        });
    }


    private void createToolbarExpenses() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Мои расходы");
        final Intent intent = new Intent(this, MainActivity.class);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
                onBackPressed();
            }
        });

    }
}
