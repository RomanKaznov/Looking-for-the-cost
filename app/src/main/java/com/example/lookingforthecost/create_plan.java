package com.example.lookingforthecost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.FinPlan;

public class create_plan extends AppCompatActivity {

    Button addPlan;
    EditText amount,significant,necessary,result;
    private FinPlan finPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
       // amount = findViewById(R.id.amount);
        significant = findViewById(R.id.significant);
        necessary = findViewById(R.id.necessary);
        result = findViewById(R.id.result);
        addPlan = findViewById(R.id.go);

        finPlan = new FinPlan();

        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (amount.getText().length()>0&&significant.getText().length()>0&&necessary.getText().length()>0&&result.getText().length()>0) {

                    finPlan.amount = Integer.parseInt(String.valueOf(amount.getText()));
                    finPlan.significant = Integer.parseInt(String.valueOf(significant.getText()));
                    finPlan.necessary = Integer.parseInt(String.valueOf(necessary.getText()));
                    finPlan.result = Integer.parseInt(String.valueOf(result.getText()));
                    finPlan.status = true;
                    new addPlan().execute(finPlan);
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




}
