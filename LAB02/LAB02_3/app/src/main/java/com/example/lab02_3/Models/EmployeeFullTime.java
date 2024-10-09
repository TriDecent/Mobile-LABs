package com.example.lab02_3.Models;

import androidx.annotation.NonNull;

public class EmployeeFullTime extends Employee {

    public EmployeeFullTime(String id, String name) {
        super(id, name);
    }

    @Override
    public double TinhLuong() {
        return 500;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + ", Type: FullTime, Salary: " + TinhLuong();
    }
}
