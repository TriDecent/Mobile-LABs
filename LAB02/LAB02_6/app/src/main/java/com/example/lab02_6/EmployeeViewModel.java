package com.example.lab02_6;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab02_6.Models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewModel extends ViewModel implements IEmployeesViewModel  {
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Employee> employeeAdded = new MutableLiveData<>();
    private final MutableLiveData<Integer> employeeRemoved = new MutableLiveData<>();

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public LiveData<Employee> getEmployeeAdded() {
        return employeeAdded;
    }

    public LiveData<Integer> getEmployeeRemoved() {
        return employeeRemoved;
    }

    public void addEmployee(Employee employee) {
        List<Employee> currentEmployees = employees.getValue();
        currentEmployees.add(employee);
        employees.setValue(currentEmployees);
        employeeAdded.setValue(employee);
    }

    public void removeEmployee(int position) {
        List<Employee> currentEmployees = employees.getValue();
        currentEmployees.remove(position);
        employees.setValue(currentEmployees);
        employeeRemoved.setValue(position);
    }
}