package com.example.lookingforthecost.screens.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;
import com.example.lookingforthecost.screens.create_category.CreateCategory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView addCategory;
    private AdapterListCategory adapterListCategory;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        addCategory = findViewById(R.id.addCategory);
        MainActivity mainActivity = new MainActivity();









        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Context context = new MainActivity().getBaseContext();

        Intent intent = new Intent(this, CreateSpending.class);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        adapterListCategory = new AdapterListCategory(context, mainViewModel, intent);
        recyclerView.setAdapter(adapterListCategory);









        mainViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapterListCategory.setItems(categories);
            }
        });


        final Intent intent2 = new Intent(this, CreateCategory.class);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });
    }







    public static void start(Activity caller,String str) {
        Intent intent = new Intent(caller, CreateSpending.class);
        intent.putExtra("name",str);
        caller.startActivity(intent);
    }


    }
