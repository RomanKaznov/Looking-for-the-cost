package com.example.lookingforthecost.screens.expenses.create_caterory_and_spending;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Expenses;
import com.example.lookingforthecost.screens.expenses.SpendingActivity;

import static com.example.lookingforthecost.R.id.save;

public class CreateSpendingActivity extends AppCompatActivity {
    EditText input, input2;
    Button add;
    Expenses expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_spending);
        expenses = new Expenses();
        add = findViewById(R.id.add);
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);

        createToolbarCreateCategory();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*    Intent intent = getIntent();
                Category category = getIntent().getParcelableExtra("name");
                String nameSpending = String.valueOf(input.getText());
                String sumSpending = String.valueOf(input2.getText());



                category.amountSpending = category.amountSpending + Integer.parseInt(sumSpending);
                int x = category.importance;
                category.importance = x;
                expenses.nameSpending = nameSpending;
                expenses.spendMoney = Integer.parseInt(sumSpending);
                expenses.nameCategorySpending = category.nameCategory;

                new update().execute(category);
                new addNameCategory().execute(expenses);

                Intent intent2 = new Intent(getApplicationContext(),SpendingActivity.class);
                startActivity(intent2);*/

            }
        });

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
            Category category = getIntent().getParcelableExtra("name");
            String nameSpending = String.valueOf(input.getText());
            String sumSpending = String.valueOf(input2.getText());


            category.amountSpending = category.amountSpending + Integer.parseInt(sumSpending);
            int x = category.importance;
            category.importance = x;
            expenses.nameSpending = nameSpending;
            expenses.spendMoney = Integer.parseInt(sumSpending);
            expenses.nameCategorySpending = category.nameCategory;

            new update().execute(category);
            new addNameCategory().execute(expenses);

            Intent intent2 = new Intent(getApplicationContext(),SpendingActivity.class);
            startActivity(intent2);


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





    private class addNameCategory extends AsyncTask<Expenses, Void, Void> {


        @Override
        protected Void doInBackground(Expenses... expenses) {

            App.getInstance().getExpensesDao().insert(expenses[0]);
            return null;
        }

    }



    private class update extends AsyncTask<Category, Void, Void> {
        @Override
        protected Void doInBackground(Category... categories) {
            App.getInstance().getCategoryDao().update(categories[0]);
            return null;
        }
    }

}
