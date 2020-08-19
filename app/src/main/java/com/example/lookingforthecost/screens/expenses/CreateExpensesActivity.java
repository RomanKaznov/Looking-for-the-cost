package com.example.lookingforthecost.screens.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.DateToday;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.controler.TextController;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.view_model.MainViewModel;
import com.example.lookingforthecost.view_model.ModelFactory;

import javax.inject.Inject;

import static com.example.lookingforthecost.R.id.save;

public class CreateExpensesActivity extends AppCompatActivity {
    private EditText input, input2;
    public static final String TAG_CREATE_EXPENSES = "EXPENSES";
    private Expenses expenses;
    private Category category;
    private MainViewModel mainViewModel;
    @Inject
    AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setContentView(R.layout.activity_create_spending);
        category = getIntent().getParcelableExtra(TAG_CREATE_EXPENSES);

        Button add = findViewById(R.id.add);
        input = findViewById(R.id.nameExpenses);
        input2 = findViewById(R.id.sumExpenses);

        TextController textController = new TextController();
        mainViewModel = ViewModelProviders.of(this, new ModelFactory(appDataBase)).get(MainViewModel.class);
        expenses = new Expenses();

        textController.formatInput(input2);
        createToolbarCreateCategory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == save && input.length() != 0 && input2.length() != 0) {
            String nameSpending = String.valueOf(input.getText());
            String sumSpending = String.valueOf(input2.getText());
            category.amountSpending = category.amountSpending + Integer.parseInt(sumSpending.replaceAll(" ", ""));
            expenses.nameSpending = nameSpending;
            expenses.spendMoney = Integer.parseInt(sumSpending.replaceAll(" ", ""));
            expenses.nameCategoryExpenses = category.nameCategory;
            expenses.dateCreateExpenses = DateToday.getDateToday();
            expenses.importance = category.importance;
            mainViewModel.updateCategory(category);
            mainViewModel.addExpenses(expenses);
            Intent intent2 = new Intent(getApplicationContext(), ExpensesActivity.class);
            startActivity(intent2);
            finish();
        }

        return true;
    }

    private void createToolbarCreateCategory() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(category.nameCategory);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpensesActivity.class);
                startActivity(intent);
            }
        });
    }

}
