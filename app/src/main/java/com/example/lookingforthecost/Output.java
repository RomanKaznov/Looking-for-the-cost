package com.example.lookingforthecost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.FinPlan;
import com.example.lookingforthecost.database.model.Spending;

import java.util.ArrayList;
import java.util.List;

public class Output extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        listView = findViewById(R.id.list);
        Intent intent = getIntent();
        Category category = intent.getParcelableExtra("CATEGORY");
        new getAr().execute(category);
    }


    private class getAr extends AsyncTask<Category, Void, List<Spending>> {


        @Override
        protected List<Spending> doInBackground(Category... categories) {

            List<Spending> list = App.getInstance().getSpendingDao().selectFromName(categories[0].nameCategory);


            return list;
        }

        @Override
        protected void onPostExecute(List<Spending> spendings) {
            ArrayList<String> spendingName = new ArrayList<>();
            for (int i = 0; i < spendings.size(); i++) {
                spendingName.add(spendings.get(i).nameSpending + "\n" + spendings.get(i).spendMoney);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, spendingName);


            listView.setAdapter(adapter);


            super.onPostExecute(spendings);
        }
    }
}
