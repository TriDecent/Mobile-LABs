// Trần Vũ Anh Trí - 22521528

package com.example.lab02_1;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var lvPerson = (ListView) findViewById(R.id.lv_person);

        final String[] arr = {"Teo", "Ty", "Bin", "Bo"};

        var adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        lvPerson.setAdapter(adapter);

        var tvSelection = (TextView) findViewById(R.id.tv_selection);

        lvPerson.setOnItemClickListener(
                (parent, view, position, id) ->
                        tvSelection.setText(getString(R.string.selection_text, position, arr[position])
//                        tvSelection.setText("position: " + position + "; value = " + arr[position])
                        )

        );
    }

}