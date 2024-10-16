package com.example.lab02_6;

import androidx.lifecycle.LiveData;

import com.example.lab02_6.Models.Employee;

import java.util.List;

public interface IEmployeesViewModel {
    LiveData<List<Employee>> getEmployees();
    LiveData<Employee> getEmployeeAdded();
    LiveData<Integer> getEmployeeRemoved();
    void addEmployee(Employee employee);
    void removeEmployee(int position);
}
