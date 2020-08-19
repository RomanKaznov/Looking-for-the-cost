package com.example.lookingforthecost.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Expenses {

    public Expenses() {
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nameCategoryExpenses")
    public String nameCategoryExpenses;

    @ColumnInfo(name = "nameSpending")
    public String nameSpending;

    @ColumnInfo(name = "dateCreateExpenses")
    public String dateCreateExpenses;


    @ColumnInfo(name = "importance")
    public int importance;

    @ColumnInfo(name = "spendMoney")
    public int spendMoney;

}
