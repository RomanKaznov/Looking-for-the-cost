package com.example.lookingforthecost.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category implements Parcelable {

    public Category() {
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nameCategory")
    public String nameCategory;

    @ColumnInfo(name = "importance")
    public int importance;//важность категории определяющая числом 0 или 1

    @ColumnInfo(name = "amountSpending")
    public int amountSpending;//сумма расходов

    // реализуем Parcel для возможности передачи объекта через intent
    protected Category(Parcel in) {
        id = in.readInt();
        nameCategory = in.readString();
        amountSpending = in.readInt();
        importance = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nameCategory);
        dest.writeInt(amountSpending);
        dest.writeInt(importance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };


}
