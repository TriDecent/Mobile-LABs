package com.example.lab03_3;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
    Button btnAdd;
    StudentAdapter adapter;

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

        initializeViews();
        setupRecyclerView();
        setupSwitchListener();
        showCalendar();
        setupAddButtonListener();
        observeViewModel();
    }

    private void initializeViews() {
        tvStatus = findViewById(R.id.tv_edit_dialog_status);
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etDate = findViewById(R.id.et_date);
        swStatus = findViewById(R.id.sw_edit_dialog_status);
        recyclerView = findViewById(R.id.rv_students);
        btnAdd = findViewById(R.id.btn_add);
        viewModel = new StudentsViewModel(new StudentsDatabaseHelper(this));
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this, viewModel);
        recyclerView.setAdapter(adapter);
    }

    private void setupSwitchListener() {
        swStatus.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        tvStatus.setText(isChecked ? "graduate" : "undergraduate")
        );
    }

    private void setupAddButtonListener() {
        btnAdd.setOnClickListener(v -> {
            if (validateFields()) {
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
            }
        });
    }


    private void observeViewModel() {
        viewModel.getObservableAddedStudent()
                .observe(this, student -> adapter.notifyItemInserted(viewModel.getAll().size() - 1));

        viewModel.getObservableDeletedStudent().observe(this, adapter::notifyItemRemoved);

        viewModel.getObservableUpdatedStudent().observe(this, adapter::notifyItemChanged);
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

    private boolean validateFields() {
        boolean isValid = true;

        if (etId.getText().toString().isEmpty()) {
            shakeField(etId);
            isValid = false;
        }
        if (etName.getText().toString().isEmpty()) {
            shakeField(etName);
            isValid = false;
        }
        if (etEmail.getText().toString().isEmpty()) {
            shakeField(etEmail);
            isValid = false;
        }
        if (etDate.getText().toString().isEmpty()) {
            shakeField(etDate);
            isValid = false;
        }

        return isValid;
    }

    private void shakeField(EditText field) {
        setFieldBorderColor(field, Color.parseColor("#E57373")); // Light red color
        ObjectAnimator animator = ObjectAnimator.ofFloat(field, "translationX", 0, 25, -25, 0);
        animator.setInterpolator(new CycleInterpolator(3));
        animator.setDuration(500);
        animator.start();
    }

    private void setFieldBorderColor(EditText field, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(5, color); // 5px border width
        field.setBackground(drawable);
    }
}