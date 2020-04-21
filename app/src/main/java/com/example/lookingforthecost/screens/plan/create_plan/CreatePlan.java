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
import com.example.lookingforthecost.Controler;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.FinPlan;
import com.example.lookingforthecost.screens.main.MainActivity;

public class CreatePlan extends AppCompatActivity {
    Controler controler;
    Button addPlan;
    EditText optionalSpending, necessarySpending, result;
    FinPlan finPlan;
    static boolean update = false;
    private String TOAST_MESSAGE = "Поля не заполнены";
    private String TOAST_MESSAGE2 = "План изенен";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        controler = new Controler();

        optionalSpending = findViewById(R.id.optionalSpending);
        necessarySpending = findViewById(R.id.nessarySpending);
        result = findViewById(R.id.result);

        addPlan = findViewById(R.id.go);

        final Intent intent = new Intent(this, MainActivity.class);
        final Intent intent2 = getIntent();

        if (intent2.hasExtra(MainActivity.TAG_INTENT_PLAN)) {
            update = true;
            finPlan = intent2.getParcelableExtra(MainActivity.TAG_INTENT_PLAN);
            necessarySpending.setText(String.valueOf(finPlan.necessaryBudget));
            optionalSpending.setText(String.valueOf(finPlan.optionalBudget));
        }




        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (update) {

                    if (controler.cheakLengthTextInput(optionalSpending,necessarySpending,result)) {
                        finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()));
                        finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()));
                        new updatePlan().execute(finPlan);
                        startActivity(intent);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                TOAST_MESSAGE2, Toast.LENGTH_SHORT);
                        toast.show();

                    }else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                TOAST_MESSAGE, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } else {

                    if (controler.cheakLengthTextInput(optionalSpending,necessarySpending,result)) {
                        finPlan = new FinPlan();
                        finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()));
                        finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()));
                        startActivity(intent);
                        new addPlan().execute(finPlan);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                TOAST_MESSAGE, Toast.LENGTH_SHORT);
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
