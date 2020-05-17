package com.example.lookingforthecost;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.example.lookingforthecost.controler.ColorController;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Expenses;
import com.example.lookingforthecost.screens.expenses.SpendingActivity;
import com.example.lookingforthecost.screens.main.MainActivity;


import java.util.ArrayList;
import java.util.List;

public class Output extends AppCompatActivity {
    ListView listView;
    ColorController colorController;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorController = new ColorController();
        createToolbarExpenses();


        setContentView(R.layout.activity_output);
        listView = findViewById(R.id.list);

        Intent intent = getIntent();
        Category category = intent.getParcelableExtra(SpendingActivity.INTENT_TAG_NAME_CATEGORY);
        colorController.setExpenses(category, listView);//в зависимости от веса(важности) категории,установим фон

        new getAr().execute(category);//выберем и выведем в list список трат необходимой категории
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
                onBackPressed();
            }
        });
    }


        private class getAr extends AsyncTask<Category, Void, List<Expenses>> {


        @Override
        protected List<Expenses> doInBackground(Category... categories) {

            List<Expenses> list = App.getInstance().getExpensesDao().selectFromName(categories[0].nameCategory);//запрос вернет в list расходы,выбранные по имени категории


            return list;
        }


        @Override
        protected void onPostExecute(List<Expenses> expenses) {
            ArrayList<String> spendingName = new ArrayList<>();
            for (int i = 0; i < expenses.size(); i++) {
                spendingName.add(expenses.get(i).nameSpending + "\n" + expenses.get(i).spendMoney);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, spendingName);


            listView.setAdapter(adapter);


            super.onPostExecute(expenses);
        }
    }
}
