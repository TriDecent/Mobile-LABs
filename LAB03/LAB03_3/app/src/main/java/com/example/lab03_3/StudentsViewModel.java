package com.example.lab03_3;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StudentsViewModel {
    private final StudentsDatabaseHelper db;

    // MutableLiveData for the list of students and specific changes
    public final MutableLiveData<List<Student>> students = new MutableLiveData<>();
    public final MutableLiveData<Student> addedStudent = new MutableLiveData<>();
    public final MutableLiveData<Integer> studentDeletedPosition = new MutableLiveData<>();
    public final MutableLiveData<Integer> updatedStudent = new MutableLiveData<>();

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
        return studentDeletedPosition;
    }

    public MutableLiveData<Integer> getObservableUpdatedStudent() {
        return updatedStudent;
    }

    // Load initial list of students
    public void loadStudents() {
        students.setValue(db.getAll());
    }

    public void add(Student student) {
        db.add(student); // Add student to database
        addedStudent.setValue(student); // Set addedStudent to trigger observer
        notifyObserversAddedOrDeletedStudent(student, true);
    }

    public void delete(Student student) {
        db.delete(student); // Remove student from database
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            int position = currentList.indexOf(student);
            if (position != -1) {
                studentDeletedPosition.setValue(position); // Set the position of the deleted student
            }
        }
        notifyObserversAddedOrDeletedStudent(student, false);
    }

    public void update(Student currentStudent, Student newStudent) {
        db.update(newStudent); // Update student in database
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            int position = currentList.indexOf(currentStudent);
            if (position != -1) {
                updatedStudent.setValue(position); // Set the position of the updated student
            }
        }

        notifyObserversUpdatedStudent(currentStudent, newStudent);
    }

    public Student get(Student student) {
        return db.getById(student.Id());
    }

    public List<Student> getAll() {
        return db.getAll();
    }

    // Notify observers of changes
    private void notifyObserversAddedOrDeletedStudent(Student student, boolean isAdded) {
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            if (isAdded) {
                currentList.add(student);
            } else {
                currentList.remove(student);
            }
            students.setValue(currentList); // Update LiveData with modified list
        }
    }

    private void notifyObserversUpdatedStudent(Student currentStudent, Student newStudent) {
        List<Student> currentList = students.getValue();
        if (currentList != null) {
            int position = currentList.indexOf(currentStudent);
            if (position != -1) {
                currentList.set(position, newStudent);
                students.setValue(currentList); // Update LiveData with modified list
            }
        }
    }
}

