package com.example.lookingforthecost.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllLiveData();

    @Query("SELECT * FROM category")
    List<Category > getAll();


    @Query("SELECT * FROM category WHERE id IN (:userIds)")
    List<Category > loadAllByIds(int[] userIds);

    @Query("SELECT * FROM category WHERE id = :uid LIMIT 1")
    Category findByName(int uid);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category  category);

    @Update
    void update(Category  category);

    @Delete
    void delete(Category  category);


    @Query("DELETE FROM category ")
    public void nukeTable();


}
