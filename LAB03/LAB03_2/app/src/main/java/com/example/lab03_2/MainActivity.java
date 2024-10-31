package com.example.lab03_2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "22521528 TRAN VU ANH TRI";
    private ContactsViewModel viewModel;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try (var db = new ContactDatabaseHandler(this)) {
            // Inserting Contacts
            Log.d(TAG + "Insert: ", "Inserting ..");
            db.add(new Contact(22521528, "Tran Vu Anh Tri", "22521528"));
            db.add(new Contact("Srinivas", "9199999999"));
            db.add(new Contact("Tommy", "9522222222"));
            db.add(new Contact("Karthik", "9533333333"));

            // Reading all contacts
            Log.e(TAG + "Reading: ", "Reading all contacts..");
            List<Contact> contacts = db.getAll();

            for (Contact cn : contacts) {
                String log = "Id: " + cn.Id() + " ,Name: " + cn.Name() + ",Phone:" + cn.PhoneNumber();
                // Writing Contacts to log
                Log.e(TAG + "Name: ", log);
            }
        }

        viewModel = new ContactsViewModel(new ContactDatabaseHandler(this));
        recyclerView = (RecyclerView) findViewById(R.id.rv_contacts);
        adapter = new ContactAdapter(this, viewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.getContacts().observe(this, contacts -> adapter.notifyDataSetChanged());
    }
}