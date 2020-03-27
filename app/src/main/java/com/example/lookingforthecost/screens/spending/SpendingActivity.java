package com.example.lookingforthecost.screens.spending;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;
import com.example.lookingforthecost.screens.spending.adapters.AdapterListCategory;
import com.example.lookingforthecost.screens.spending.adapters.MainViewModel;
import com.example.lookingforthecost.screens.spending.create_caterory_and_spending.CreateCategoryActivity;
import com.example.lookingforthecost.screens.spending.create_caterory_and_spending.CreateSpendingActivity;

import java.util.List;

public class SpendingActivity extends AppCompatActivity {

    TextView addCategory;
    private AdapterListCategory adapterListCategory;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spending);
        recyclerView = findViewById(R.id.list);
        addCategory = findViewById(R.id.addCategory);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Context context = new SpendingActivity().getBaseContext();
        Intent intent = new Intent(this, CreateSpendingActivity.class);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Activity activity = new Activity();

        adapterListCategory = new AdapterListCategory(context, mainViewModel,intent);


        recyclerView.setAdapter(adapterListCategory);


        mainViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapterListCategory.setItems(categories);
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


    public static void start(Activity caller, Category category) {
        Intent intent = new Intent(caller, CreateSpendingActivity.class);
        intent.putExtra("name", category);
        caller.startActivity(intent);
    }

    public static void reloadActivity(Activity caller) {
        Intent intent = new Intent(caller, SpendingActivity.class);
        caller.finish();
        caller.startActivity(intent);
    }



}
