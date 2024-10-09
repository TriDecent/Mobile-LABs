package com.example.lab02_3;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.lab02_3.Models.Employee;
import com.example.lab02_3.Models.EmployeeFullTime;
import com.example.lab02_3.Models.EmployeePartTime;

import java.util.ArrayList;

public class EmployeeManager {

    private final ArrayList<Employee> employees;
    private final ArrayAdapter<Employee> adapter;
    private final RadioGroup rgType;
    private final EditText etId;
    private final EditText etName;
    private final ListView lvEmployees;

    public EmployeeManager(ArrayList<Employee> employees, ArrayAdapter<Employee> adapter,
                           RadioGroup rgType, EditText etId, EditText etName, ListView lvEmployees) {
        this.employees = employees;
        this.adapter = adapter;
        this.rgType = rgType;
        this.etId = etId;
        this.etName = etName;
        this.lvEmployees = lvEmployees;
    }

    public void addNewEmployee() {
        int radioButtonId = rgType.getCheckedRadioButtonId();
        String id = etId.getText().toString();
        String name = etName.getText().toString();

        Employee employee = (radioButtonId == R.id.rb_full_time) ?
                new EmployeeFullTime(id, name) :
                new EmployeePartTime(id, name);

        employees.add(employee);

        lvEmployees.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void removeEmployee(int position) {
        employees.remove(position);
        adapter.notifyDataSetChanged();
    }
}
