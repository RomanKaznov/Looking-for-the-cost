package com.example.lookingforthecost.screens.main;

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

import java.net.URISyntaxException;

public class CreateSpending extends AppCompatActivity {
    EditText input,input2;
    Button add;
    Spending spending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_spending);
        spending = new Spending();
        add= findViewById(R.id.add);
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String s = intent.getStringExtra("name");
                String str = String.valueOf(input.getText());
                String str2 = String.valueOf(input2.getText());
                spending.nameSpending = str;
                spending.spendMoney = Integer.parseInt(str2);
                spending.nameCategorySpending = s;

                new addNameCategory().execute(spending);
                finish();

            }
        });






    }

    static class addNameCategory extends AsyncTask<Spending, Void, Void> {


        @Override
        protected Void doInBackground(Spending... spendings) {

            App.getInstance().getSpendingDao().insert(spendings[0]);
            return null;
        }
    }



}
