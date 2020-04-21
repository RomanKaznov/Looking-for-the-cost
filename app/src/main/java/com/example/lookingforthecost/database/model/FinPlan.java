package com.example.lookingforthecost.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FinPlan  implements Parcelable {

    public FinPlan(){};

    @PrimaryKey(autoGenerate = true)
    public int id;



    @ColumnInfo(name = "optionalBudget")
    public int optionalBudget;


    @ColumnInfo(name = "necessaryBudget")
    public int necessaryBudget;


    @ColumnInfo(name = "numberDays")
    public int numberDays;



    protected FinPlan(Parcel in) {
        id = in.readInt();
        optionalBudget = in.readInt();
        necessaryBudget = in.readInt();
        numberDays = in.readInt();


    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(optionalBudget);
        dest.writeInt(necessaryBudget);
        dest.writeInt(numberDays);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FinPlan> CREATOR = new Creator<FinPlan>() {
        @Override
        public FinPlan createFromParcel(Parcel in) {
            return new FinPlan(in);
        }

        @Override
        public FinPlan[] newArray(int size) {
            return new FinPlan[size];
        }
    };



}
