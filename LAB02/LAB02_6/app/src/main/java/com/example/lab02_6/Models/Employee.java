package com.example.lab02_6.Models;

public record Employee(String id, String fullName, boolean isManager) {
    public String getFullName() {
        return fullName;
    }
}