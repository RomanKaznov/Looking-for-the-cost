package com.example.lookingforthecost.controler;

import android.widget.EditText;

import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Expenses;

import java.util.ArrayList;

public class SortingValue {



    public ArrayList<Expenses> getSortExpenses(Category category, ArrayList<Expenses> arr) {

        ArrayList<Expenses> sortExpenses = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).nameCategorySpending.equals(category.nameCategory)) {
                sortExpenses.add(arr.get(i));
            }
        }
        return sortExpenses;
    }

    


}
