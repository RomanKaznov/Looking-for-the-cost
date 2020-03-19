package com.example.lookingforthecost.database.model;

import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity
public class Category  {

    public Category() {

    }

    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "nameCategory")
    public String nameCategory;


    @ColumnInfo(name = "importance")
    public int importance;


   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        if (getClass() != o.getClass()) return false;
        if (id!=category.id) return false;
        if (nameCategory!=category.nameCategory) return  false;//
       return nameCategory != null ? nameCategory.equals(category.nameCategory) : category.nameCategory == null;
    }


 /*   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (uid != note.uid) return false;
        if (timestamp != note.timestamp) return false;
        if (done != note.done) return false;
        return text != null ? text.equals(note.text) : note.text == null;
    }*/



    @Override
    public int hashCode() {
        int result = id;

        return result;

    }




}
