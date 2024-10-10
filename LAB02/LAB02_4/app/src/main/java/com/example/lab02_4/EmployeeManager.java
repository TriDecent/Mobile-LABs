package com.example.lab02_4;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lab02_4.Models.Employee;

import java.util.ArrayList;

public class EmployeeManager {

    private final ArrayList<Employee> employees;
    private final ArrayAdapter<Employee> adapter;
    private final CheckBox cbIsAManager;
    private final EditText etId;
    private final EditText etFullName;
    private final ListView lvEmployees;

    public EmployeeManager(ArrayList<Employee> employees, ArrayAdapter<Employee> adapter,
                           CheckBox cbIsAManager, EditText etId, EditText etFullName, ListView lvEmployees) {
        this.employees = employees;
        this.adapter = adapter;
        this.cbIsAManager = cbIsAManager;
        this.etId = etId;
        this.etFullName = etFullName;
        this.lvEmployees = lvEmployees;
    }

    public void addNewEmployee() {
        String id = etId.getText().toString();
        String name = etFullName.getText().toString();

        Employee employee = cbIsAManager.isChecked() ?
                new Employee(id, name, true) :
                new Employee(id, name, false);

        employees.add(employee);

        lvEmployees.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void removeEmployee(int position) {
        employees.remove(position);
        adapter.notifyDataSetChanged();
    }
}
