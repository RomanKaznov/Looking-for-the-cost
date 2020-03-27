package com.example.lookingforthecost.database.model;

import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity
public class Category implements Parcelable  {

    public Category() {

    }

    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "nameCategory")
    public String nameCategory;


    @ColumnInfo(name = "importance")
    public int importance;

  @ColumnInfo(name = "amoutSpending")
    public int  amountSpending;


   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        if (getClass() != o.getClass()) return false;
        if (id!=category.id) return false;
        if (nameCategory!=category.nameCategory) return  false;//
        if (amountSpending!=category.amountSpending)return false;
       return nameCategory != null ? nameCategory.equals(category.nameCategory) : category.nameCategory == null;
    }



    @Override
    public int hashCode() {
        int result = id;
        return result;
    }



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
