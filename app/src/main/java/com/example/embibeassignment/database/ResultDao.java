package com.example.embibeassignment.database;

import com.example.embibeassignment.model.Result;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ResultDao {

    @Query("SELECT * FROM results")
    List<Result> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAll(Result result);

    @Query("DELETE FROM results WHERE title=:title")
    void delete(String title);

    @Query("SELECT * FROM results WHERE title=:title")
    int checkIfItemExists(String title);

}
