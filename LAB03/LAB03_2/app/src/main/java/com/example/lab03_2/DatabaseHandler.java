package com.example.lab03_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT, "
                + KEY_PH_NO + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public boolean addContact(Contact contact) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            var cv = new ContentValues();
            //            cv.put(KEY_ID, contact.Id());
            cv.put(KEY_NAME, contact.Name());
            cv.put(KEY_PH_NO, contact.PhoneNumber());

            var insert = db.insert(TABLE_CONTACTS, null, cv);

            return insert != -1;
        }
    }

    // Getting single contact
    public Contact getContact(int id) {
        String stringQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE " + KEY_ID + " = ?";
        return queryContactBasedOnNeed(stringQuery, new String[]{String.valueOf(id)}).get(0);
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        var stringQuery = "SELECT * FROM " + TABLE_CONTACTS;
        return queryContactBasedOnNeed(stringQuery, null);
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        try (var db = getWritableDatabase()) {
            var cv = new ContentValues();
            cv.put(KEY_ID, contact.Id());
            cv.put(KEY_NAME, contact.Name());
            cv.put(KEY_PH_NO, contact.PhoneNumber());

            return db.update(TABLE_CONTACTS,
                    cv,
                    KEY_ID + "= ?",
                    new String[]{String.valueOf(contact.Id())}
            );
        }
    }

    // Deleting single contact
    public boolean deleteContact(Contact contact) {
        try (var db = getWritableDatabase()) {
            var result = db.delete(
                    TABLE_CONTACTS,
                    KEY_ID + "= ?",
                    new String[]{String.valueOf(contact.Id())}
            );

            return result > 0;
        }
    }

    private List<Contact> queryContactBasedOnNeed(String queryString, String[] selectionArgs) {
        var result = new ArrayList<Contact>();
        try (var db = getReadableDatabase();
             var cursor = db.rawQuery(queryString, selectionArgs)) {

            if (cursor.moveToFirst()) {
                do {
                    var idIndex = cursor.getColumnIndex(KEY_ID);
                    var nameIndex = cursor.getColumnIndex(KEY_NAME);
                    var phoneIndex = cursor.getColumnIndex(KEY_PH_NO);

                    if (idIndex >= 0 && nameIndex >= 0 && phoneIndex >= 0) {
                        var idContact = cursor.getInt(idIndex);
                        var nameContact = cursor.getString(nameIndex);
                        var phoneContact = cursor.getString(phoneIndex);

                        result.add(new Contact(idContact, nameContact, phoneContact));
                    }
                } while (cursor.moveToNext());
            }
        }
        return result;
    }
}
