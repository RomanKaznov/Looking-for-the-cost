package com.example.lookingforthecost.screens.controler;

import android.app.Application;
import android.graphics.Color;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.entity.Category;

public class ColorController extends Application {

    public static void setExpenses(Category category, ListView listView) {//метод задаст фон listView,в зависимости от важности категории
        if (category.importance == 0) {
            listView.setBackgroundResource(R.color.colorOptionalCategory);
        } else {
            listView.setBackgroundResource(R.color.colorNecessaryCategory);
        }
    }

    public static void setColorTextView(int sum, TextView textView) {//метод задает цвет текста,в зависимости от суммы
        if (sum < 0) {
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(Color.BLACK);
        }
    }

    public static void setCategoryBackground(int importance, ImageView nameCategory, ImageView layoutCategory) {//метод меняет цвет категории,в зависимости от ее важности
        if (importance == 1) {
            nameCategory.setBackgroundResource(R.drawable.back_name_necessary_category);
            layoutCategory.setBackgroundResource(R.color.colorNecessaryCategory);
        } else {//обязателен блок else,тк как без него цвета элементов в recyclerview отображаются не коректно
            nameCategory.setImageDrawable(null);
            layoutCategory.setImageDrawable(null);
        }

        if (importance == 0) {
            nameCategory.setBackgroundResource(R.drawable.back_name_optional_category);
            layoutCategory.setBackgroundResource(R.color.colorOptionalCategory);
        } else {
            nameCategory.setImageDrawable(null);
            layoutCategory.setImageDrawable(null);
        }
    }


}
