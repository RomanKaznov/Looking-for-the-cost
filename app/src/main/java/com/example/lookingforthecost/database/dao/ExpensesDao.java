package com.example.lookingforthecost.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lookingforthecost.database.entity.Expenses;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Query("SELECT * FROM Expenses")
    LiveData<List<Expenses>> getAllExpensesLiveData();

    @Query("SELECT * FROM Expenses WHERE nameCategoryExpenses IN (:nameCategory)")
    LiveData<List<Expenses>> findExpensesByNameCategory(String nameCategory);

    @Query("SELECT * FROM expenses WHERE dateCreateExpenses = :date ")
    List<Expenses> findExpensesByDate(String date);

    @Query("SELECT * FROM expenses WHERE nameCategoryExpenses = :name ")
    LiveData<List<Expenses>> findExpensesByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expenses expenses);

    @Query("DELETE  FROM Expenses WHERE nameCategoryExpenses = :str")
    void deleteByName(String str);

}
