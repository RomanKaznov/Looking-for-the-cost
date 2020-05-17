package com.example.lookingforthecost.screens.expenses.create_caterory_and_spending;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.screens.expenses.SpendingActivity;


import java.util.ArrayList;

import static com.example.lookingforthecost.R.id.save;

public class CreateCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Category category;

    EditText editText;
    Button add;
    TextView tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9, tag10, tag11;
    CheckBox optional, necessary;
    ArrayList<Category> oldNames;
    private boolean checkRepeatCategory;
    private String NameCategory;

    private int weigt;
    private final String MESSAGE = "категория уже создана";
    private final String MESSAGE2 = "Введите имя категории";
    private final String MESSAGE3 = "Выберите,насколько важные расходы будут содержаться в категории";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        createToolbarCreateCategory();

        editText = findViewById(R.id.input);

        oldNames = new ArrayList<>();

        category = new Category();

        optional = findViewById(R.id.optional);
        necessary = findViewById(R.id.optionalSpending);


        new checkOldName().execute();//запустим поток,получим массив имен существующих категорий,и при создании новой проверим на повторение

        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3 = findViewById(R.id.tag3);
        tag4 = findViewById(R.id.tag4);
        tag5 = findViewById(R.id.tag5);
        tag6 = findViewById(R.id.tag6);
        tag7 = findViewById(R.id.tag7);
        tag8 = findViewById(R.id.tag8);
        tag9 = findViewById(R.id.tag9);
        tag10 = findViewById(R.id.tag10);
        tag11 = findViewById(R.id.tag11);

        tag1.setOnClickListener(this);
        tag2.setOnClickListener(this);
        tag3.setOnClickListener(this);
        tag4.setOnClickListener(this);
        tag5.setOnClickListener(this);
        tag6.setOnClickListener(this);
        tag7.setOnClickListener(this);
        tag8.setOnClickListener(this);
        tag9.setOnClickListener(this);
        tag10.setOnClickListener(this);
        tag11.setOnClickListener(this);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });



    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tag1:
                editText.setText(tag1.getText());
                break;
            case R.id.tag2:
                editText.setText(tag2.getText());
                break;
            case R.id.tag3:
                editText.setText(tag3.getText());
                break;
            case R.id.tag4:
                editText.setText(tag4.getText());
                break;
            case R.id.tag5:
                editText.setText(tag5.getText());
                break;
            case R.id.tag6:
                editText.setText(tag6.getText());
                break;
            case R.id.tag7:
                editText.setText(tag7.getText());
                break;
            case R.id.tag8:
                editText.setText(tag8.getText());
                break;
            case R.id.tag9:
                editText.setText(tag9.getText());
                break;
            case R.id.tag10:
                editText.setText(tag10.getText());
                break;
            case R.id.tag11:
                editText.setText(tag11.getText());
                break;


        }
    }


    public void onCheckBoxListener(View view) {
        switch (view.getId()) {
            case R.id.optionalSpending:
                if (optional.isChecked()) {
                    optional.setChecked(false);
                }
                weigt = 1;
                break;
            case R.id.optional:
                if (necessary.isChecked()) {
                    necessary.setChecked(false);
                }
                weigt = 0;
                break;
        }

    }


    private void showMessage(String MESSAGE) {
        Toast toast = Toast.makeText(getApplicationContext(),
                MESSAGE, Toast.LENGTH_SHORT);
        toast.show();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_category, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == save) {
            new checkOldName().execute();
            NameCategory = String.valueOf(editText.getText());
            //
            //проверяем на повторение
            for (int i = 0; i < oldNames.size(); i++) {
                if (oldNames.get(i).nameCategory.equals(NameCategory)) {
                    checkRepeatCategory = true;
                    break;
                } else {
                    checkRepeatCategory = false;
                }
            }
            //

            if (editText.getText().length() > 0 && !checkRepeatCategory) {
                if (optional.isChecked() || necessary.isChecked()) {
                    category.nameCategory = NameCategory;
                    category.amountSpending = 0;
                    category.importance = weigt;
                    new addCategory().execute(category);
                    Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);
                    startActivity(intent);
                } else {
                    showMessage(MESSAGE3);
                }

            } else if (editText.getText().length() == 0) {
                showMessage(MESSAGE2);
            } else {
                showMessage(MESSAGE);
            }

        }
        return true;
    }



    private void createToolbarCreateCategory() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Новая категория");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    static class addCategory extends AsyncTask<Category, Void, Void> {
        @Override
        protected Void doInBackground(Category... categories) {
            // App.getInstance().getCategoryDao().nukeTable();
            App.getInstance().getCategoryDao().insert(categories[0]);

            return null;
        }
    }


    class checkOldName extends AsyncTask<Category, Void, ArrayList<Category>> {


        @Override
        protected ArrayList<Category> doInBackground(Category... categories) {
            oldNames = (ArrayList<Category>) App.getInstance().getCategoryDao().getAll();
            return oldNames;
        }

        @Override
        protected void onPostExecute(ArrayList<Category> categories) {
            super.onPostExecute(categories);
        }
    }


}
