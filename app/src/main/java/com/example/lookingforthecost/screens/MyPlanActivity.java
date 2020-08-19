package com.example.lookingforthecost.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.controler.TextController;
import com.example.lookingforthecost.database.entity.FinPlan;
import com.example.lookingforthecost.notification.AlarmManagerStatus;
import com.example.lookingforthecost.view_model.MainViewModel;
import com.example.lookingforthecost.view_model.ModelFactory;

import javax.inject.Inject;

import static com.example.lookingforthecost.R.id.save;


public class MyPlanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText optionalSpending, necessarySpending, result;
    private FinPlan finPlan;
    private boolean update = false;
    public static String TAG_INTENT_PLAN = "PLAN";
    private final String TOAST_MESSAGE = "Поля не заполнены";
    private final String TOAST_MESSAGE2 = "План изенен";
    private final String TOAST_MESSAGE3 = "План создан";
    private TextController textController;
    private MainViewModel mainViewModel;
    @Inject
    AppDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        App.getComponent().inject(this);
        createToolbar();
        optionalSpending = findViewById(R.id.optionalSpending);
        necessarySpending = findViewById(R.id.necassaryBudget);
        textController = new TextController();
        mainViewModel = ViewModelProviders.of(this, new ModelFactory(dataBase)).get(MainViewModel.class);

        textController.formatInput(optionalSpending);
        textController.formatInput(necessarySpending);
        final Intent intent = getIntent();
        if (intent.hasExtra(TAG_INTENT_PLAN)) {//если пришел интент с планом,присвоим update true и заполним поля содержимым
            update = true;
            finPlan = intent.getParcelableExtra(TAG_INTENT_PLAN);
            necessarySpending.setText(String.valueOf(finPlan.necessaryBudget));
            optionalSpending.setText(String.valueOf(finPlan.optionalBudget));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (item.getItemId() == save) {
            if (update) {//обновим план
                if (optionalSpending.length() != 0 && necessarySpending.length() != 0) {
                    String s = String.valueOf(optionalSpending.getText()).replaceAll(" ", "");
                    String d = String.valueOf(necessarySpending.getText()).replaceAll(" ", "");
                    finPlan.optionalBudget = Integer.parseInt(s);
                    finPlan.necessaryBudget = Integer.parseInt(d);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE2, Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                    mainViewModel.updatePlan(finPlan);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE, Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {//если в переменной update значение false,создаем план
                if (optionalSpending.length() != 0 && necessarySpending.length() != 0) {
                    finPlan = new FinPlan();
                    finPlan.optionalBudget = Integer.parseInt(String.valueOf(optionalSpending.getText()).replaceAll(" ", ""));
                    finPlan.necessaryBudget = Integer.parseInt(String.valueOf(necessarySpending.getText()).replaceAll(" ", ""));
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE3, Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                    mainViewModel.addPlan(finPlan);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            TOAST_MESSAGE, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_category, menu);
        return true;
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                onBackPressed();
            }
        });
        toolbar.setTitle("Мой план");
    }

}
