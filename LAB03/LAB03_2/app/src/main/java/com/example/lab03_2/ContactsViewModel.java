package com.example.lab03_2;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ContactsViewModel {
    private final DatabaseHandler db;

    public final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();

    public ContactsViewModel(DatabaseHandler db) {
        this.db = db;
        loadContacts();
    }

    public MutableLiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void loadContacts() {
        contacts.setValue(db.getAllContacts());
    }

    public Contact addContact(Contact contact) {
        db.addContact(contact);
        loadContacts();
        return contact;
    }

    public Contact deleteContact(Contact contact) {
        db.deleteContact(contact);
        loadContacts();
        return contact;
    }

    public List<Contact> getAllContacts() {
        return db.getAllContacts();
    }
}
