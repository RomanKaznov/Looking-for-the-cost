package com.example.lookingforthecost.screens.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.OptionActivity;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.FinPlan;

import static com.example.lookingforthecost.R.id.option;
import static com.example.lookingforthecost.R.id.save;


public class MyPlan extends AppCompatActivity {
    Toolbar toolbar;
    Button addPlan;
    EditText optionalSpending, necessarySpending, result;
    FinPlan finPlan;
    static boolean update = false;
    private String TOAST_MESSAGE = "Поля не заполнены";
    private String TOAST_MESSAGE2 = "План изенен";
    private String TOAST_MESSAGE3 = "План создан";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        createToolbar();
        optionalSpending = findViewById(R.id.optionalSpending);
        necessarySpending = findViewById(R.id.nessarySpending);
        result = findViewById(R.id.result);



        final Intent intent = getIntent();

        if (intent.hasExtra(MainActivity.TAG_INTENT_PLAN)) {//если пришел интент с планом,присвоим update true и заполним поля содержимым
            update = true;
            finPlan = intent.getParcelableExtra(MainActivity.TAG_INTENT_PLAN);
            necessarySpending.setText(String.valueOf(finPlan.necessaryBudget));
            optionalSpending.setText(String.valueOf(finPlan.optionalBudget));
            result.setText(String.valueOf(finPlan.numberDays));
        }


    }


    private boolean cheсkLengthTextInput(EditText editText1, EditText editText2, EditText editText3) {
        if (editText1.getText().length() > 0 && editText2.getText().length() > 0 && editText3.getText().length() > 0) {
            return true;
        }
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_option, menu);
        return true;
    }

    private void createToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
                onBackPressed();
            }
        });
        toolbar.setTitle("Мой план");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
        if (item.getItemId() == save) {

            if (update) {//обновим план

                if (cheсkLengthTextInput(optionalSpending, necessarySpending, result)) {//метод cheсkLengthTextInput проверит,заполнены ли поля,указанные в аргументах
                    finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()));
                    finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()));
                    new updatePlan().execute(finPlan);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE2, Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE, Toast.LENGTH_SHORT);
                    toast.show();
                }

            } else {//если в переменной update значение false,создаем план

                if (cheсkLengthTextInput(optionalSpending, necessarySpending, result)) {
                    finPlan = new FinPlan();
                    finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()));
                    finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()));

                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE3, Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                    new addPlan().execute(finPlan);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        return true;
    }


    private class addPlan extends AsyncTask<FinPlan, Void, Void> {//Добавим план в бд

        @Override
        protected Void doInBackground(FinPlan... finPlans) {

            App.getInstance().getFinPlanDao().insert(finPlans[0]);

            return null;
        }
    }


    private class updatePlan extends AsyncTask<FinPlan, Void, Void> {//обновим план в бд

        @Override
        protected Void doInBackground(FinPlan... finPlans) {

            App.getInstance().getFinPlanDao().update(finPlans[0]);

            return null;
        }
    }


}
