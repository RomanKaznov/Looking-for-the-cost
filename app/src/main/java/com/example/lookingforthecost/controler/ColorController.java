package com.example.lookingforthecost.controler;


import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;

public class ColorController {


   public void setCategoryBackground(Category category, TextView nameCategory, LinearLayout linearLayout){
        if(category.importance==0){
            linearLayout.setBackgroundResource(R.color.colorOtional);
            nameCategory.setBackgroundResource(R.color.colorOtional);
        }else {
            linearLayout.setBackgroundResource(R.color.colorNecassary);
            nameCategory.setBackgroundResource(R.color.colorNecassary);
        }
    }

    public void setColorTextView(int sum,TextView textView){
       if (sum<0){
           textView.setTextColor(Color.RED);
       }
    }

    public void setExpenses(Category category, ListView listView){
       if (category.importance==0){
           listView.setBackgroundResource(R.color.colorOtional);
       }else {
          listView.setBackgroundResource(R.color.colorNecassary);
       }
    }











}
