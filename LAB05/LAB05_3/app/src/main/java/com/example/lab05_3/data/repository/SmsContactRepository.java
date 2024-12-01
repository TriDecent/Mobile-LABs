package com.example.lab05_3.data.repository;

import com.example.lab05_3.domain.ChangedContactListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SmsContactRepository {
    private final ReentrantLock lock;
    private final List<String> contacts;
    private ChangedContactListener listener;

    public SmsContactRepository() {
        this.lock = new ReentrantLock();
        this.contacts = new ArrayList<>();
    }

    public void setOnContactsChangeListener(ChangedContactListener listener) {
        this.listener = listener;
    }

    public void addContact(String address) {
        lock.lock();
        try {
            if (!contacts.contains(address)) {
                contacts.add(address);
                notifyContactsChanged();
            }
        } finally {
            lock.unlock();
        }
    }

    public List<String> getContacts() {
        lock.lock();
        try {
            return new ArrayList<>(contacts);
        } finally {
            lock.unlock();
        }
    }

    public void removeContact(String address) {
        lock.lock();
        try {
            if (contacts.remove(address)) {
                notifyContactsChanged();
            }
        } finally {
            lock.unlock();
        }
    }

    private void notifyContactsChanged() {
        if (listener != null) {
            listener.onContactsChanged(getContacts());
        }
    }
}