package com.example.lookingforthecost.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.entity.FinPlan;

import java.util.List;

@Dao
public interface FinPlanDao {

    @Query("SELECT * FROM finplan")
    LiveData<List<FinPlan>> getFinPlanLiveDate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FinPlan finPlan);

    @Update
    void update(FinPlan finPlan);

}
