package com.example.lookingforthecost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.FinPlan;

import java.util.List;

public class MyPlan extends AppCompatActivity {

    TextView add, update;
    TextView sumNecessary, necessaryBudget, remains1;
    TextView sumOptionalSpending, optionalBudget, remains2;
    LinearLayout planCreateStatus, planCreateNotStatus;
    private List<FinPlan> finPlans;
    boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

        add = findViewById(R.id.add);
        update = findViewById(R.id.update);


        sumNecessary = findViewById(R.id.sumNecessary);
        necessaryBudget = findViewById(R.id.necessaryBudget);
        remains1 = findViewById(R.id.remains1);

        sumOptionalSpending = findViewById(R.id.sumOptionalSpending);
        optionalBudget = findViewById(R.id.optionalBudget);
        remains2 = findViewById(R.id.remains2);

        planCreateStatus = findViewById(R.id.planCreate);
        planCreateNotStatus = findViewById(R.id.planNotCreate);

        new SetterList().execute();



        final Intent intent = new Intent(this, create_plan.class);
        final Intent intent2 = new Intent(this, create_plan.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2.putExtra("Plan", finPlans.get(0));
                startActivity(intent2);
            }
        });

    }


    private class SetterList extends AsyncTask<Void, Void, int[]> {


        @Override
        protected int[] doInBackground(Void... voids) {

            finPlans = App.getInstance().getFinPlanDao().getAll();


            int sumOptional = 0;
            int sumNecessary = 0;
            int[] sum = new int[6];

            if (finPlans.size()>0) {
                List<Category> list = App.getInstance().getCategoryDao().getAll();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).importance == 1) {
                        sumNecessary += list.get(i).amountSpending;
                    } else if (list.get(i).importance == 0) {
                        sumOptional += list.get(i).amountSpending;
                    }
                }

                sum[0] = sumOptional; //сумма необязательных трат
                sum[1] = finPlans.get(0).optionalBudget;
                sum[2] = sumNecessary;//сумма обязательных трат
                sum[3] = finPlans.get(0).necessaryBudget;
            }
            return sum;
        }


        @Override
        protected void onPostExecute(int[] sum) {

            status = checkCreatePlan(finPlans);

            sumNecessary.setText(String.valueOf(sum[2]));
            necessaryBudget.setText(String.valueOf(sum[3]));
            remains1.setText(String.valueOf(sum[3] - sum[2]));


            sumOptionalSpending.setText(String.valueOf(sum[0]));
            optionalBudget.setText(String.valueOf(sum[1]));
            remains2.setText(String.valueOf(sum[1] - sum[0]));


            super.onPostExecute(sum);
        }
    }


    private boolean checkCreatePlan(List<FinPlan> finPlans) {
        boolean status = false;
        if (finPlans.size() > 0) {
            add.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            planCreateStatus.setVisibility(View.VISIBLE);
            planCreateNotStatus.setVisibility(View.GONE);
            status = true;
        } else {
            add.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            planCreateStatus.setVisibility(View.GONE);
            planCreateNotStatus.setVisibility(View.VISIBLE);
        }

        return status;
    }


}
