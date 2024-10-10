package com.example.lab02_4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab02_4.Models.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EmployeeManager employeeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> employees = new ArrayList<>();
        EmployeeAdapter adapter = new EmployeeAdapter(this, R.layout.item_employee, employees);
        CheckBox cbIsAManager = findViewById(R.id.cb_is_manager);
        EditText etId = findViewById(R.id.et_employee_id);
        EditText etName = findViewById(R.id.et_employee_full_name);
        ListView lvEmployees = findViewById(R.id.lv_employees);

        employeeManager = new EmployeeManager(employees, adapter, cbIsAManager, etId, etName, lvEmployees);

        Button btnEmployeeSubmit = findViewById(R.id.btn_employee_submit);
        btnEmployeeSubmit.setOnClickListener(v -> employeeManager.addNewEmployee());

        lvEmployees.setOnItemLongClickListener((parent, view, position, id) -> {
            employeeManager.removeEmployee(position);
            return true;
        });
    }
}