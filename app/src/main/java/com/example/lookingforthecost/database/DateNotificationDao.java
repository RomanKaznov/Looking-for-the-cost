package com.example.lookingforthecost.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lookingforthecost.database.model.DateNotification;
import com.example.lookingforthecost.database.model.Spending;

import java.util.List;

@Dao
public interface DateNotificationDao {
    @Query("SELECT * FROM datenotification")
    List<DateNotification> getAll();


    @Query( "SELECT * FROM datenotification WHERE id IN (:userIds)")
    List<DateNotification> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM datenotification WHERE id = :uid LIMIT 1")
    DateNotification findByName(int uid);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DateNotification dateNotification);


    @Delete
    void delete(DateNotification dateNotification);


}
