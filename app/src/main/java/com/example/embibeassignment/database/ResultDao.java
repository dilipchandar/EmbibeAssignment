package com.example.embibeassignment.database;

import com.example.embibeassignment.model.Result;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ResultDao {

    @Query("SELECT * FROM results")
    List<Result> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAll(Result result);

    @Delete
    void delete(Result result);

    @Query("SELECT * FROM results WHERE title=:title")
    int checkIfItemExists(String title);

}
