package com.example.lookingforthecost.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Spending{


    public Spending() {}



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

        Spending spending = (Spending) o;

        if (id != spending.id) return false;

        return nameSpending != null ? nameSpending.equals(spending.nameSpending) : spending.nameSpending == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameSpending != null ? nameSpending.hashCode() : 0);
        return result;
    }



}
