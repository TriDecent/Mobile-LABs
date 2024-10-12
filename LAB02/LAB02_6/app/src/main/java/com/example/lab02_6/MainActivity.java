package com.example.lab02_6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab02_6.Models.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EmployeeManager employeeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> employees = new ArrayList<>();
        EmployeeAdapter adapter = new EmployeeAdapter(this, employees);
        CheckBox cbIsAManager = findViewById(R.id.cb_is_manager);
        EditText etId = findViewById(R.id.et_employee_id);
        EditText etName = findViewById(R.id.et_employee_full_name);
        RecyclerView rcEmployees = findViewById(R.id.rc_employees);

        employeeManager = new EmployeeManager(this, employees, adapter, cbIsAManager, etId, etName, rcEmployees);

        Button btnEmployeeSubmit = findViewById(R.id.btn_employee_submit);
        btnEmployeeSubmit.setOnClickListener(view -> employeeManager.addNewEmployee());

        rcEmployees.setOnLongClickListener(view -> {
            int position = rcEmployees.getChildAdapterPosition(view);
            employeeManager.removeEmployee(position);
            return true;
        });
    }
}