package com.example.embibeassignment.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.embibeassignment.R;

public class MainActivity extends AppCompatActivity {

    Button btn_fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_fetch = findViewById(R.id.btnFetch);

        btn_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FetchResultsActivity.class);
                startActivity(i);
            }
        });
    }
}
