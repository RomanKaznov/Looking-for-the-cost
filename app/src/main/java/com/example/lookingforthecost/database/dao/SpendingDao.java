package com.example.lookingforthecost.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.model.Spending;

import java.util.List;

@Dao
public interface SpendingDao  {

    @Query("SELECT * FROM spending")
    LiveData<List<Spending>> getAllLiveData();

    @Query("SELECT * FROM spending")
    List<Spending> getAll();


    @Query( "SELECT * FROM spending WHERE id IN (:userIds)")
    List<Spending> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM spending WHERE id = :uid LIMIT 1")
    Spending findByName(int uid);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Spending spending);

    @Update
    void update(Spending spending);

    @Delete
    void delete(Spending spending);

    @Query("DELETE  FROM Spending WHERE nameCategorySpending = :str")
    void delFromName(String str);





    @Query("DELETE FROM spending ")
    public void nukeTable();



}
