package com.example.lab03_3;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StudentsViewModel {
    private final StudentsDatabaseHelper db;

    public final MutableLiveData<List<Student>> students = new MutableLiveData<>();
    public final MutableLiveData<Student> addedStudent = new MutableLiveData<>();
    public final MutableLiveData<Integer> deletedPosition = new MutableLiveData<>();
    public final MutableLiveData<Integer> updatedPosition = new MutableLiveData<>();

    public StudentsViewModel(StudentsDatabaseHelper db) {
        this.db = db;
        loadStudents(); // Initialize the students list
    }

    public MutableLiveData<List<Student>> getObservableStudents() {
        return students;
    }

    public MutableLiveData<Student> getObservableAddedStudent() {
        return addedStudent;
    }

    public MutableLiveData<Integer> getObservableDeletedStudent() {
        return deletedPosition;
    }

    public MutableLiveData<Integer> getObservableUpdatedStudent() {
        return updatedPosition;
    }

    // Load initial list of students
    public void loadStudents() {
        students.setValue(db.getAll());
    }

    public void add(Student student) {
        db.add(student); // Add student to database
        addedStudent.setValue(student); // Set addedStudent to trigger observer
        addStudentToList(student);
    }

    public void delete(Student student) {
        db.delete(student); // Remove student from database
        removeStudentFromList(student);
    }

    public void update(Student currentStudent, Student newStudent) {
        db.update(newStudent); // Update student in database
        updateStudentInList(currentStudent, newStudent);
    }

    public Student get(Student student) {
        return db.getById(student.Id());
    }

    public List<Student> getAll() {
        return db.getAll();
    }

    // Update students list by adding a new student
    private void addStudentToList(Student student) {
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            currentList.add(student);
            students.setValue(currentList);
        }
    }

    // Update students list by removing a student
    private void removeStudentFromList(Student student) {
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            int position = currentList.indexOf(student);
            if (position != -1) {
                currentList.remove(position);
                deletedPosition.setValue(position); // Notify observers of the deleted student position
                students.setValue(currentList);
            }
        }
    }

    // Update students list by modifying an existing student
    private void updateStudentInList(Student currentStudent, Student newStudent) {
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            int position = currentList.indexOf(currentStudent);
            if (position != -1) {
                currentList.set(position, newStudent);
                updatedPosition.setValue(position); // Notify observers of the updated student position
                students.setValue(currentList);
            }
        }
    }
}


