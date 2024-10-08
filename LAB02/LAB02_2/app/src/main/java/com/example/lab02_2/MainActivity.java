// Trần Vũ Anh Trí - 22521528

package com.example.lab02_2;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListViewManager listViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewManager = new ListViewManager(this);
        listViewManager.setupListView();

        var btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(
                v -> {
                    var name = ((TextView) findViewById(R.id.et_enter_name)).getText().toString();
                    listViewManager.addName(name);
                }
        );
    }
}

