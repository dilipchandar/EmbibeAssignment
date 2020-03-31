package com.example.embibeassignment.model;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "results", indices = {@Index(value = {"title", "poster_path"},
        unique = true)})
public class Result {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private long uid;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster_path;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
