package com.example.embibeassignment.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.embibeassignment.R;
import com.example.embibeassignment.adapter.BookmarkAdapter;
import com.example.embibeassignment.database.AppDatabase;
import com.example.embibeassignment.database.ResultDao;
import com.example.embibeassignment.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkActivity extends Activity {

    private RecyclerView recyclerView;
    ResultDao resultDao;
    private ExecutorService executorService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookmark);
        recyclerView = findViewById(R.id.recyclerview_bookmark);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        resultDao = appDatabase.resultDao();
        executorService = Executors.newSingleThreadExecutor();

        final List<Result> resultList = new ArrayList<>();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                resultList.addAll(resultDao.getAll());
            }
        });

        recyclerView.setAdapter(new BookmarkAdapter(this, resultList));
    }

}
