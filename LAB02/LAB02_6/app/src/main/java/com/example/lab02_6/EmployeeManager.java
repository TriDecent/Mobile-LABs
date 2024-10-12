package com.example.lab02_6;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab02_6.Models.Employee;

import java.util.List;

public class EmployeeManager {

    private final Context context;
    private final List<Employee> employees;
    private final EmployeeAdapter adapter;
    private final CheckBox cbIsAManager;
    private final EditText etId;
    private final EditText etFullName;
    private final RecyclerView rcEmployees;

    public EmployeeManager(Context context, List<Employee> employees, EmployeeAdapter adapter,
                           CheckBox cbIsAManager, EditText etId, EditText etFullName, RecyclerView rcEmployees) {
        this.context = context;
        this.employees = employees;
        this.adapter = adapter;
        this.cbIsAManager = cbIsAManager;
        this.etId = etId;
        this.etFullName = etFullName;
        this.rcEmployees = rcEmployees;

        // Set LayoutManager and Adapter once during initialization
        rcEmployees.setLayoutManager(new LinearLayoutManager(context));
        rcEmployees.setAdapter(adapter);
    }

    public void addNewEmployee() {
        String id = etId.getText().toString();
        String name = etFullName.getText().toString();

        Employee employee = cbIsAManager.isChecked() ?
                new Employee(id, name, true) :
                new Employee(id, name, false);

        employees.add(employee);
        adapter.notifyItemInserted(employees.size() - 1);

        Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
    }

    public void removeEmployee(int position) {
        employees.remove(position);
        adapter.notifyItemRemoved(position);

        Toast.makeText(context, "Removed successfully", Toast.LENGTH_SHORT).show();
    }
}