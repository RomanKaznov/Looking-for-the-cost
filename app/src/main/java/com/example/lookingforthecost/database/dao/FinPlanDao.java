package com.example.lookingforthecost.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.model.FinPlan;

import java.util.List;

@Dao
public interface FinPlanDao {

    @Query("SELECT * FROM finplan")
    LiveData<List<FinPlan>> getFinPlan();


    @Query("SELECT * FROM finplan")
    List<FinPlan> getAll();


    @Query( "SELECT * FROM finplan WHERE id IN (:userIds)")
    List<FinPlan> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM finplan WHERE id = :uid LIMIT 1")
    FinPlan findByName(int uid);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FinPlan finPlan);

    @Update
    void update(FinPlan finPlan);

    @Delete
    void delete(FinPlan finPlan);

    @Query("DELETE FROM finplan")
    public void nukeTable();





}
