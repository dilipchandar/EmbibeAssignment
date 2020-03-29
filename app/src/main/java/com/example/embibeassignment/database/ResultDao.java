package com.example.embibeassignment.database;

import com.example.embibeassignment.model.Result;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ResultDao {

    @Query("SELECT * FROM results")
    List<Result> getAll();

    @Insert
    void insertAll(Result result);

    @Delete
    void delete(Result result);

}
