package com.example.lookingforthecost.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


import com.example.lookingforthecost.App;
import com.example.lookingforthecost.OptionActivity;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.FinPlan;
import com.example.lookingforthecost.notification.NotificationMessage;
import com.example.lookingforthecost.screens.plan.MyPlan;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.plan.create_plan.CreatePlan;
import com.example.lookingforthecost.screens.spending.SpendingActivity;
import com.example.lookingforthecost.screens.spending.create_caterory_and_spending.CreateCategoryActivity;

import java.util.Calendar;
import java.util.List;

import static com.example.lookingforthecost.R.id.option;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button myFinances, mySpendings;
    TextView necessaryBudget, optionalBudget;
    TextView remainsNecessary, remainsOptional;
    Toolbar toolbar;
    FinPlan finPlan;
    public static String TAG_INTENT_PLAN = "PLAN";

    private AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Plan().execute();


        toolbar = findViewById(R.id.toolbar);
        ;
        setSupportActionBar(toolbar);

        necessaryBudget = findViewById(R.id.necessaryBudget);
        optionalBudget = findViewById(R.id.optionalBudget);


        remainsNecessary = findViewById(R.id.remainsNecessary);
        remainsOptional = findViewById(R.id.remainsOptional);


       /* Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 12);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this, NotificationMessage.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

*/
        myFinances = findViewById(R.id.myFinances);
        mySpendings = findViewById(R.id.mySpendings);

        myFinances.setOnClickListener(this);
        mySpendings.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
        if (item.getItemId() == option) {
            startActivity(intent);
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SpendingActivity.class);
        Intent intent2 = new Intent(this, CreatePlan.class);

        if (finPlan != null)
            intent2.putExtra(TAG_INTENT_PLAN, finPlan);

        switch (view.getId()) {
            case R.id.myFinances:
                startActivity(intent2);
                break;

            case R.id.mySpendings:
                startActivity(intent);
                break;
        }


    }


    private class Plan extends AsyncTask<Void, Void, int[]> {


        @Override
        protected int[] doInBackground(Void... voids) {
            List<FinPlan> finPlans = App.getInstance().getFinPlanDao().getAll();

            int sumOptionalSpending = 0;
            int sumNecessarySpending = 0;
            int[] sum = new int[6];

            if (finPlans.size() > 0) {
                finPlan = finPlans.get(0);
                List<Category> categories = App.getInstance().getCategoryDao().getAll();

                if (categories.size() > 0) {
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).importance == 1) {//при создании категории указывается ее необходимость,если это категория необходимых расходов присваивается 1
                            sumNecessarySpending += categories.get(i).amountSpending;
                        } else if (categories.get(i).importance == 0) {// если категория необязательных расходов присваивается 0
                            sumOptionalSpending += categories.get(i).amountSpending;
                        }
                    }
                }


                sum[0] = finPlans.get(0).necessaryBudget;//бюджет на необходимые затраты
                sum[1] = sum[0] - sumNecessarySpending;//сумма необходимых расходов

                sum[2] = finPlans.get(0).optionalBudget;//бюджет на необязательные расходы
                sum[3] = sum[2] - sumOptionalSpending;//сума необязательных расходов

            }


            return sum;
        }

        @Override
        protected void onPostExecute(int[] ints) {
            necessaryBudget.setText(String.valueOf(ints[0]) + " р.");
            remainsNecessary.setText(String.valueOf(ints[1]) + " р.");

            optionalBudget.setText(String.valueOf(ints[2]) + " р.");
            remainsOptional.setText(String.valueOf(ints[3]) + " р.");

            super.onPostExecute(ints);
        }

    }


}
