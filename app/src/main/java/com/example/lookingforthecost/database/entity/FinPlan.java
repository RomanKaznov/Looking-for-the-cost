package com.example.lookingforthecost.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class FinPlan implements Parcelable {

    public FinPlan() {
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "optionalBudget")
    public int optionalBudget;

    @ColumnInfo(name = "necessaryBudget")
    public int necessaryBudget;

    // реализуем Parcel для возможности передачи объекта через intent
    protected FinPlan(Parcel in) {
        id = in.readInt();
        optionalBudget = in.readInt();
        necessaryBudget = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(optionalBudget);
        dest.writeInt(necessaryBudget);
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
