package com.example.lookingforthecost;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lookingforthecost.database.model.Category;

public class Controler {


   public void setBackrondCategory(Category category, TextView nameCategory, LinearLayout linearLayout){
        if(category.importance==0){
         // rv.setBackgroundResource(R.color.colorPrimar);
          linearLayout.setBackgroundResource(R.color.colorNecassary);
          nameCategory.setBackgroundResource(R.color.colorNecassary);
        }else {
           // rv.setBackgroundResource(R.color.green2);
            linearLayout.setBackgroundResource(R.color.colorOtional);
            nameCategory.setBackgroundResource(R.color.colorOtional);
        }
    }



    public boolean cheakLengthTextInput(EditText editText1,EditText editText2,EditText editText3){
       if( editText1.getText().length() > 0 && editText2.getText().length() > 0 && editText3.getText().length() > 0){
           return true;
        }
        return false;
    }









}
