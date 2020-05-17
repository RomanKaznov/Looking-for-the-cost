package com.example.lookingforthecost.screens.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.lookingforthecost.Output;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Expenses;
import com.example.lookingforthecost.screens.expenses.adapters.AdapterListCategory;
import com.example.lookingforthecost.screens.expenses.view_model.MainViewModel;
import com.example.lookingforthecost.screens.expenses.create_caterory_and_spending.CreateCategoryActivity;
import com.example.lookingforthecost.screens.expenses.create_caterory_and_spending.CreateSpendingActivity;
import com.example.lookingforthecost.screens.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SpendingActivity extends AppCompatActivity {

    TextView addCategory;
    private AdapterListCategory adapterListCategory;
    RecyclerView recyclerView;
    ArrayList<Expenses> arrayList;
    Toolbar toolbar;


    public static final String INTENT_TAG_NAME_CATEGORY = "ИМЯ КАТЕГОРИИ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spending);
        createToolbarExpenses();


        recyclerView = findViewById(R.id.list);
        addCategory = findViewById(R.id.addCategory);

        arrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        adapterListCategory = new AdapterListCategory(getApplicationContext());
        recyclerView.setAdapter(adapterListCategory);


        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapterListCategory.setItems(categories);
            }
        });

        mainViewModel.getSpendingLiveData().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                adapterListCategory.setItemsAdapterSpending(expenses);
            }
        });


        final Intent intent2 = new Intent(this, CreateCategoryActivity.class);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

    }



    /*запуск activity для создания траты,передадим категорию,чтобы получить и записать в Expenses имя категории в которой она была создана.
    Для категории прибавим к существующим расходам новые.
    */
    public static void startCreateCategory(Activity caller, Category category) {

        Intent intent = new Intent(caller, CreateSpendingActivity.class);
        intent.putExtra("name", category);
        caller.startActivity(intent);
    }
    //

    public static void startOutputActivity(Activity activity, Category category) {//запуск activity которое выведет расходы.В интенте лежит категория,по которой мы поймем какие расходы выводить

        Intent intent = new Intent(activity.getApplicationContext(), Output.class);
        intent.putExtra(INTENT_TAG_NAME_CATEGORY,category);
        activity.startActivity(intent);
    }

    public static void reloadActivity(Activity activity) {//перезапустить activity.Метод создан для того,чтобы данные коректно выводились при удалении и добавлении категории
        Intent intent = new Intent(activity, SpendingActivity.class);
        activity.finish();
        activity.startActivity(intent);
    }

    private void createToolbarExpenses(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Мои расходы");
        final Intent intent = new Intent(this,MainActivity.class);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                onBackPressed();
            }
        });

    }

}
