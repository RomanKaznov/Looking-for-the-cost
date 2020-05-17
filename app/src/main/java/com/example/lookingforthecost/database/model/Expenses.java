package com.example.lookingforthecost.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Expenses {


    public Expenses() {}



    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nameCategorySpending")
    public String nameCategorySpending;


    @ColumnInfo(name = "nameSpending")
    public String nameSpending;





    @ColumnInfo(name = "spendMoney")
    public int spendMoney;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expenses expenses = (Expenses) o;

        if (id != expenses.id) return false;

        return nameSpending != null ? nameSpending.equals(expenses.nameSpending) : expenses.nameSpending == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameSpending != null ? nameSpending.hashCode() : 0);
        return result;
    }



}
