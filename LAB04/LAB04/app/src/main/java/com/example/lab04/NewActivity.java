package com.example.lab04;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //        getSupportActionBar().setTitle("My Activity Title");

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());
    }
}
