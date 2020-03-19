package com.example.lookingforthecost.screens.create_category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;

import java.util.ArrayList;

public class CreateCategory extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    Button add;
    TextView tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9, tag10, tag11;
    ArrayList<Category> oldNames;
    boolean checkRepeatCategory;
    String NameCategory;
    private Category category;
    private final String MESSEGE = "категория уже создана";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        editText = findViewById(R.id.input);
        add = findViewById(R.id.add);
        oldNames = new ArrayList<>();
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



        new OldName().execute();



        category = new Category();





        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });











        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //получаем значение поля

                new OldName().execute();

                NameCategory = String.valueOf(editText.getText());
                //


                for (int i = 0; i < oldNames.size(); i++) {



                    if(oldNames.get(i).nameCategory.equals(NameCategory)){
                        checkRepeatCategory = true;
                        break;
                    }else {
                        checkRepeatCategory = false;
                    }




                }



                Log.i("MESE", String.valueOf(checkRepeatCategory));

                 if (editText.getText().length() > 0 && !checkRepeatCategory && !String.valueOf(editText.getText()).equals(MESSEGE)) {
                        category.nameCategory = NameCategory;
                        new addNameCategory().execute(category);
                        finish();



                }else if(editText.getText().length()==0){

                }else {
                     editText.setText(MESSEGE);
                 }
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





   static class addNameCategory extends AsyncTask<Category, Void, Void> {


       @Override
       protected Void doInBackground(Category... categories) {
         // App.getInstance().getCategoryDao().nukeTable();
           App.getInstance().getCategoryDao().insert(categories[0]);

           return null;
       }
   }



    class OldName extends AsyncTask<Category, Void, ArrayList<Category>> {


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
