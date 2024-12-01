package com.example.lab05_3.domain;

import java.util.List;

public interface ChangedContactListener {
    void onContactsChanged(List<String> contacts);
}