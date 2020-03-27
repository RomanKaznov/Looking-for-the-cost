package com.example.lookingforthecost.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FinPlan {


    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "status")
    public boolean status;


    @ColumnInfo(name = "amount")
    public int amount;


    @ColumnInfo(name = "significant")
    public int significant;


    @ColumnInfo(name = "necessary")
    public int necessary;


    @ColumnInfo(name = "result")
    public int result;


}
