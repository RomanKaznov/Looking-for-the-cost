package com.example.lookingforthecost.screens.expenses;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.view_model.MainViewModel;
import com.example.lookingforthecost.view_model.ModelFactory;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.lookingforthecost.R.id.save;

public class CreateCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Category category;
    private EditText nameCategory;
    private TextView tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9, tag10, tag11;
    private CheckBox optional, necessary;
    private LinearLayout mainLayout;
    private ArrayList<Category> oldNames;
    private boolean checkRepeatCategory = false;
    private int weight;
    private static final String TAG_CREATE_CATEGORY = "Name Category";
    private final String MESSAGE = "категория уже создана";
    private final String MESSAGE2 = "Введите имя категории";
    private final String MESSAGE3 = "Выберите,насколько важные расходы будут содержаться в категории";
    private MainViewModel mainViewModel;
    @Inject
    AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        createToolbarCreateCategory();
        App.getComponent().inject(this);

        nameCategory = findViewById(R.id.nameExpenses);
        optional = findViewById(R.id.optional);
        necessary = findViewById(R.id.optionalSpending);
        mainLayout = findViewById(R.id.mainLayout);

        mainViewModel = ViewModelProviders.of(this, new ModelFactory(appDataBase)).get(MainViewModel.class);
        oldNames = new ArrayList<>();
        category = new Category();

        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3 = findViewById(R.id.tag3);
        tag4 = findViewById(R.id.tag4);
        tag5 = findViewById(R.id.tag5);
        tag6 = findViewById(R.id.tag6);
        tag7 = findViewById(R.id.tag7);
        tag8 = findViewById(R.id.tag8);
        tag9 = findViewById(R.id.tag9);
        tag10 = findViewById(R.id.tag10);
        tag11 = findViewById(R.id.tag11);

        tag1.setOnClickListener(this);
        tag2.setOnClickListener(this);
        tag3.setOnClickListener(this);
        tag4.setOnClickListener(this);
        tag5.setOnClickListener(this);
        tag6.setOnClickListener(this);
        tag7.setOnClickListener(this);
        tag8.setOnClickListener(this);
        tag9.setOnClickListener(this);
        tag10.setOnClickListener(this);
        tag11.setOnClickListener(this);

        nameCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameCategory.setText("");
            }
        });
        mainViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                oldNames = (ArrayList<Category>) categories;
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tag1:
                nameCategory.setText(tag1.getText());
                break;
            case R.id.tag2:
                nameCategory.setText(tag2.getText());
                break;
            case R.id.tag3:
                nameCategory.setText(tag3.getText());
                break;
            case R.id.tag4:
                nameCategory.setText(tag4.getText());
                break;
            case R.id.tag5:
                nameCategory.setText(tag5.getText());
                break;
            case R.id.tag6:
                nameCategory.setText(tag6.getText());
                break;
            case R.id.tag7:
                nameCategory.setText(tag7.getText());
                break;
            case R.id.tag8:
                nameCategory.setText(tag8.getText());
                break;
            case R.id.tag9:
                nameCategory.setText(tag9.getText());
                break;
            case R.id.tag10:
                nameCategory.setText(tag10.getText());
                break;
            case R.id.tag11:
                nameCategory.setText(tag11.getText());
                break;
        }
    }


    public void onCheckBoxListener(View view) {//в зависимости от выбора определяется важность категории
        switch (view.getId()) {
            case R.id.optionalSpending:
                if (optional.isChecked()) {
                    optional.setChecked(false);
                }
                mainLayout.setBackgroundResource(R.color.colorNecessaryCategory);
                weight = 1;
                break;
            case R.id.optional:
                if (necessary.isChecked()) {
                    necessary.setChecked(false);
                }
                weight = 0;
                mainLayout.setBackgroundResource(R.color.colorOptionalCategory);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_category, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == save) {
            String newCategory = String.valueOf(nameCategory.getText());
            for (int i = 0; i < oldNames.size(); i++) {//проверяем на повторение
                if (oldNames.get(i).nameCategory.equals(newCategory)) {
                    checkRepeatCategory = true;
                    break;
                } else {
                    checkRepeatCategory = false;
                }
            }
            if (nameCategory.length() != 0 && !checkRepeatCategory) {
                if (optional.isChecked() || necessary.isChecked()) {//проверка на установку checkbox
                    category.nameCategory = newCategory;
                    category.amountSpending = 0;
                    category.importance = weight;
                    mainViewModel.addCategory(category);
                    Intent intent = new Intent(this, ExpensesActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    showMessage(MESSAGE3);
                }
            } else if (nameCategory.getText().length() == 0) {
                showMessage(MESSAGE2);
            } else {
                showMessage(MESSAGE);
            }
        }
        return true;
    }


    private void createToolbarCreateCategory() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Новая категория");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showMessage(String MESSAGE) {
        Toast toast = Toast.makeText(getApplicationContext(),
                MESSAGE, Toast.LENGTH_SHORT);
        toast.show();
    }
}
