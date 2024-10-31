package com.example.lab03_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student.db";
    private static final Integer DATABASE_VERSION = 1;
    private static final String STUDENT_TABLE = "STUDENT";
    private static final String COLUMN_STUDENT_ID = "ID";
    private static final String COLUMN_STUDENT_NAME = "NAME";
    private static final String COLUMN_STUDENT_EMAIL = "EMAIL";
    private static final String COLUMN_STUDENT_DATE = "DATE";
    private static final String COLUMN_STUDENT_STATUS = "STATUS";

    public StudentsDatabaseHelper(
            @Nullable Context context
    ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement =
                "CREATE TABLE " + STUDENT_TABLE +
                        "(" + COLUMN_STUDENT_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_STUDENT_NAME + " TEXT, " +
                        COLUMN_STUDENT_EMAIL + " TEXT, " +
                        COLUMN_STUDENT_DATE + " TEXT, " +
                        COLUMN_STUDENT_STATUS + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);

        // Create tables again
        onCreate(db);
    }

    public boolean add(Student student) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            var cv = createStudentContentValues(student);
            var insert = db.insert(STUDENT_TABLE, null, cv);

            return insert != -1;
        }
    }

    public Student getById(int id) {
        String stringQuery = "SELECT * FROM " + STUDENT_TABLE + " WHERE " + COLUMN_STUDENT_ID + " = ?";
        return queryBasedOnNeed(stringQuery, new String[]{String.valueOf(id)}).get(0);
    }

    public List<Student> getAll() {
        var stringQuery = "SELECT * FROM " + STUDENT_TABLE;
        return queryBasedOnNeed(stringQuery, null);
    }

    // Updating single student
    public int update(Student student) {
        try (var db = getWritableDatabase()) {
            var cv = createStudentContentValues(student);

            return db.update(STUDENT_TABLE,
                    cv,
                    COLUMN_STUDENT_ID + "= ?",
                    new String[]{String.valueOf(student.Id())}
            );
        }
    }

    public boolean delete(Student student) {
        try (var db = getWritableDatabase()) {
            var result = db.delete(
                    STUDENT_TABLE,
                    COLUMN_STUDENT_ID + "= ?",
                    new String[]{String.valueOf(student.Id())}
            );

            return result > 0;
        }
    }

    private ContentValues createStudentContentValues(Student student) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_ID, student.Id().toString());
        cv.put(COLUMN_STUDENT_NAME, student.Name());
        cv.put(COLUMN_STUDENT_EMAIL, student.Email());
        cv.put(COLUMN_STUDENT_DATE, student.DateOnly().toString());
        cv.put(COLUMN_STUDENT_STATUS, student.Status().toString());
        return cv;
    }

    private List<Student> queryBasedOnNeed(String queryString, String[] selectionArgs) {
        var result = new ArrayList<Student>();
        try (var db = getReadableDatabase();
             var cursor = db.rawQuery(queryString, selectionArgs)) {

            if (cursor.moveToFirst()) {
                do {
                    var idIndex = cursor.getColumnIndex(COLUMN_STUDENT_ID);
                    var nameIndex = cursor.getColumnIndex(COLUMN_STUDENT_NAME);
                    var emailIndex = cursor.getColumnIndex(COLUMN_STUDENT_EMAIL);
                    var dateIndex = cursor.getColumnIndex(COLUMN_STUDENT_DATE);
                    var statusIndex = cursor.getColumnIndex(COLUMN_STUDENT_STATUS);

                    if (idIndex >= 0 && nameIndex >= 0 && statusIndex >= 0 &&
                            emailIndex >= 0 && dateIndex >= 0) {
                        var studentId = cursor.getString(idIndex);
                        var studentName = cursor.getString(nameIndex);
                        var studentEmail = cursor.getString(emailIndex);
                        var studentDate = cursor.getString(dateIndex);
                        var studentStatus = cursor.getString(statusIndex);

                        result.add(
                                new Student(
                                        new BigInteger(studentId),
                                        studentName,
                                        studentEmail,
                                        LocalDate.parse(studentDate),
                                        Status.valueOf(studentStatus)
                                )
                        );
                    }
                } while (cursor.moveToNext());
            }
        }
        return result;
    }
}