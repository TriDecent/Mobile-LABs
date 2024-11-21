package com.example.lab04;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("LAB04_3");
        }

        Button btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
        });
    }
}
