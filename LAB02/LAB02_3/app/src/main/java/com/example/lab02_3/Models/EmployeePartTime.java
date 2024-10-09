package com.example.lab02_3.Models;

import androidx.annotation.NonNull;

public class EmployeePartTime extends Employee {

    public EmployeePartTime(String id, String name) {
        super(id, name);
    }

    @Override
    public double TinhLuong() {
        return 150;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + ", Type: PartTime, Salary: " + TinhLuong();
    }
}
