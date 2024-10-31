package com.example.lab03_3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tvStatus;
    EditText etId, etName, etEmail, etDate;
    SwitchCompat swStatus;
    StudentsViewModel viewModel;
    RecyclerView recyclerView;
    Button btnAdd, btnViewAll;

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

        tvStatus = findViewById(R.id.tv_status);
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etDate = findViewById(R.id.et_date);
        swStatus = findViewById(R.id.sw_status);
        recyclerView = findViewById(R.id.rv_students);
        btnAdd = findViewById(R.id.btn_add);
        btnViewAll = findViewById(R.id.btn_view_all);
        viewModel = new StudentsViewModel(new StudentsDatabaseHelper(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        var adapter = new StudentAdapter(this, viewModel);
        recyclerView.setAdapter(adapter);

        swStatus.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        tvStatus.setText(isChecked ? "graduate" : "undergraduate")
        );

        showCalendar();

        btnAdd.setOnClickListener(v -> {
            var student = new Student(
                    new BigInteger(etId.getText().toString()),
                    etName.getText().toString(),
                    etEmail.getText().toString(),
                    LocalDate.parse(etDate.getText(), DateTimeFormatter.ofPattern("yyyy-M-d")),
                    swStatus.isChecked() ? Status.Graduate : Status.Undergraduate
            );
            viewModel.add(student);

            Toast.makeText(this, "Added new student", Toast.LENGTH_SHORT).show();
            clearFields();
        });

        viewModel.getObservableAddedStudent().observe(this, student -> {
            if (student != null) {
                adapter.notifyItemInserted(viewModel.getAll().size() - 1);
            }
        });

        viewModel.getObservableDeletedStudent().observe(this, adapter::notifyItemRemoved);
    }

    private void showCalendar() {
        etDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        etDate.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });
    }

    private void clearFields() {
        etId.setText("");
        etName.setText("");
        etEmail.setText("");
        etDate.setText("");
        swStatus.setChecked(false);
    }
}