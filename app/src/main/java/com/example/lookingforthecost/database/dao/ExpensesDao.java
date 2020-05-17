package com.example.lookingforthecost.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.model.Expenses;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Query("SELECT * FROM Expenses")
    LiveData<List<Expenses>> getAllLiveData();

    @Query("SELECT * FROM Expenses")
    List<Expenses> getAll();


    @Query( "SELECT * FROM Expenses WHERE id IN (:userIds)")
    List<Expenses> loadAllByIds(int[] userIds);

    @Query( "SELECT * FROM Expenses WHERE nameCategorySpending IN (:name)")
    List<Expenses> selectFromName(String name);




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expenses expenses);

    @Update
    void update(Expenses expenses);

    @Delete
    void delete(Expenses expenses);

    @Query("DELETE  FROM Expenses WHERE nameCategorySpending = :str")
    void delFromName(String str);





    @Query("DELETE FROM Expenses ")
    public void nukeTable();



}
