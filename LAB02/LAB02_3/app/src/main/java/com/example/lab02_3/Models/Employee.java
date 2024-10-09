package com.example.lab02_3.Models;

import androidx.annotation.NonNull;

public abstract class Employee {
    final private String id;
    final private String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract double TinhLuong();

    @NonNull
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}