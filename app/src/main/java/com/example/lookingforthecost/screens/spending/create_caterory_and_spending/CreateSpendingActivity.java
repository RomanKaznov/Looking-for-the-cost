package com.example.lookingforthecost.screens.spending.create_caterory_and_spending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;
import com.example.lookingforthecost.screens.main.MainActivity;
import com.example.lookingforthecost.screens.spending.SpendingActivity;

public class CreateSpendingActivity extends AppCompatActivity {
    EditText input, input2;
    Button add;
    Spending spending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_spending);
        spending = new Spending();
        add = findViewById(R.id.add);
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                Category category = getIntent().getParcelableExtra("name");
                String nameSpending = String.valueOf(input.getText());
                String sumSpending = String.valueOf(input2.getText());



                category.amountSpending = category.amountSpending + Integer.parseInt(sumSpending);
                int x = category.importance;
                category.importance = x;
                spending.nameSpending = nameSpending;
                spending.spendMoney = Integer.parseInt(sumSpending);
                spending.nameCategorySpending = category.nameCategory;

                new update().execute(category);
                new addNameCategory().execute(spending);

                Intent intent2 = new Intent(getApplicationContext(),SpendingActivity.class);
                startActivity(intent2);

            }
        });

    }


    private class addNameCategory extends AsyncTask<Spending, Void, Void> {


        @Override
        protected Void doInBackground(Spending... spendings) {

            App.getInstance().getSpendingDao().insert(spendings[0]);
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
