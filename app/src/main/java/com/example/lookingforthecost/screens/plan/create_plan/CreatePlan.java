package com.example.lookingforthecost.screens.plan.create_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.FinPlan;
import com.example.lookingforthecost.screens.plan.MyPlan;

public class create_plan extends AppCompatActivity {

    Button addPlan;
    EditText optionalSpending, necessarySpending, result;
    private FinPlan finPlan;
   static boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);


        optionalSpending = findViewById(R.id.optionalSpending);
        necessarySpending = findViewById(R.id.nessarySpending);


        result = findViewById(R.id.result);
        addPlan = findViewById(R.id.go);
        final Intent intent2 = getIntent();

        finPlan = new FinPlan();
        final Intent intent = new Intent(this, MyPlan.class);
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent2.hasExtra("Plan")){
                    FinPlan finPlan = intent2.getParcelableExtra("Plan");
                    finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()));
                    finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()));
                    update = true;
                    new updatePlan().execute(finPlan);
                    startActivity(intent);

                }else {


                    if (optionalSpending.getText().length() > 0 && necessarySpending.getText().length() > 0 && result.getText().length() > 0) {

                        finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()));
                        finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()));

                        startActivity(intent);
                        new addPlan().execute(finPlan);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Поля не заполнены", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }



            }
        });

    }


    private class addPlan extends AsyncTask<FinPlan, Void, Void> {
        @Override
        protected Void doInBackground(FinPlan... finPlans) {

                App.getInstance().getFinPlanDao().insert(finPlans[0]);

            return null;
        }
    }

    private class updatePlan extends AsyncTask<FinPlan, Void, Void> {
        @Override
        protected Void doInBackground(FinPlan... finPlans) {

                App.getInstance().getFinPlanDao().update(finPlans[0]);

            return null;
        }
    }


}
