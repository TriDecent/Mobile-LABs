package com.example.lab03_2;

public record Contact(int Id, String Name, String PhoneNumber) {
    public Contact(String name, String phoneNumber) {
        this(0, name, phoneNumber);
    }
}
