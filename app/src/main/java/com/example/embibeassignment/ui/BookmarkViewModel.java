package com.example.embibeassignment.ui;

import android.app.Application;
import android.content.Context;

import com.example.embibeassignment.database.AppDatabase;
import com.example.embibeassignment.database.ResultDao;
import com.example.embibeassignment.model.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class BookmarkViewModel extends ViewModel {

    ResultDao resultDao;
    private ExecutorService executorService;

    public BookmarkViewModel() {

    }

    public BookmarkViewModel(Application application) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        resultDao = appDatabase.resultDao();
        executorService = Executors.newSingleThreadExecutor();
    }


    void insertItem(final Result result) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                resultDao.insertAll(result);
            }
        });
    }

}
