package com.example.lookingforthecost.screens.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lookingforthecost.MyPlan;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.spending.SpendingActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button myFinances,mySpendings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFinances = findViewById(R.id.myFinances);
        mySpendings = findViewById(R.id.mySpendings);

        myFinances.setOnClickListener(this);
        mySpendings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,SpendingActivity.class);
        Intent intent2 = new Intent(this, MyPlan.class);
        switch (view.getId()){
            case R.id.myFinances:
                startActivity(intent2);
                break;

                case R.id.mySpendings:
                startActivity(intent);
                break;
        }
    }
}
