package com.example.lab02_3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab02_3.Models.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EmployeeManager employeeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> employees = new ArrayList<>();
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        RadioGroup rgType = findViewById(R.id.rg_employee_type);
        EditText etId = findViewById(R.id.et_employee_id);
        EditText etName = findViewById(R.id.et_employee_name);
        ListView lvEmployees = findViewById(R.id.lv_employees);

        employeeManager = new EmployeeManager(employees, adapter, rgType, etId, etName, lvEmployees);

        Button btnEmployeeSubmit = findViewById(R.id.btn_employee_submit);
        btnEmployeeSubmit.setOnClickListener(v -> employeeManager.addNewEmployee());

        lvEmployees.setOnItemLongClickListener((parent, view, position, id) -> {
            employeeManager.removeEmployee(position);
            return true;
        });
    }
}

