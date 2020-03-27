package com.example.lookingforthecost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lookingforthecost.database.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MyPlan extends AppCompatActivity {


    TextView add;

    TextView sumNecessary, necessaryBudget, remains1;
    TextView sumMinor, minorBudget, remains2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        add = findViewById(R.id.add);

        sumNecessary = findViewById(R.id.sumNecessary);
        necessaryBudget = findViewById(R.id.necessaryBudget);
        remains1 = findViewById(R.id.remains1);

        sumMinor = findViewById(R.id.sumMinor);
        minorBudget = findViewById(R.id.minorBudget);
        remains2 = findViewById(R.id.remains2);


        new SetterList().execute();


        final Intent intent = new Intent(this, create_plan.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);


            }
        });


    }


    private class SetterList extends AsyncTask<Void, Void, int[]> {


        @Override
        protected int[] doInBackground(Void... voids) {
            int sumOptional = 0;
            int sumNecessary = 0;
            int[] sum = new int[2];


            List<Category> list = App.getInstance().getCategoryDao().getAll();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).importance == 1) {
                    sumNecessary +=list.get(i).amountSpending;
                }else if (list.get(i).importance == 0) {
                    sumOptional +=list.get(i).amountSpending;
                }
            }

            sum[0] = sumOptional;
            sum[1] = sumNecessary;

            return sum;
        }


        @Override
        protected void onPostExecute(int[] sum) {

            sumNecessary.setText(String.valueOf(sum[1]));
            sumMinor.setText(String.valueOf(sum[0]));

            super.onPostExecute(sum);
        }
    }


}
