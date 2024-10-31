package com.example.lab03_2;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ContactsViewModel {
    private final ContactDatabaseHandler db;

    public final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();

    public ContactsViewModel(ContactDatabaseHandler db) {
        this.db = db;
        loadContacts();
    }

    public MutableLiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void loadContacts() {
        contacts.setValue(db.getAll());
    }

    public Contact addContact(Contact contact) {
        db.add(contact);
        loadContacts();
        return contact;
    }

    public Contact deleteContact(Contact contact) {
        db.delete(contact);
        loadContacts();
        return contact;
    }

    public List<Contact> getAllContacts() {
        return db.getAll();
    }
}
