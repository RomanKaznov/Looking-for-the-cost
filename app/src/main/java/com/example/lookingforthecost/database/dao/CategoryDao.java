package com.example.lookingforthecost.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.entity.Category;

import java.util.List;

import dagger.Module;

@Module
@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllCategoriesLiveData();

    @Query("SELECT * FROM category WHERE importance = :importance ")
    LiveData<List<Category>> LiveDataFindByImportance(int importance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

}
