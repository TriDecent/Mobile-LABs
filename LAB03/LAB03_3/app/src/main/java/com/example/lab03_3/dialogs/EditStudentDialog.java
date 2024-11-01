package com.example.lab03_3.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;

import com.example.lab03_3.R;
import com.example.lab03_3.enums.Status;
import com.example.lab03_3.models.Student;
import com.example.lab03_3.viewmodels.StudentsViewModel;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.function.Consumer;

public class EditStudentDialog {
    private final Context context;
    private final Student originalStudent;
    private final StudentsViewModel viewModel;
    private final Consumer<Student> callBack;

    public EditStudentDialog(Context context, Student originalStudent, StudentsViewModel viewModel, Consumer<Student> callBack) {
        this.context = context;
        this.originalStudent = originalStudent;
        this.viewModel = viewModel;
        this.callBack = callBack;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Student");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_student, null);
        builder.setView(dialogView);

        EditText etDialogId = dialogView.findViewById(R.id.et_dialog_id);
        EditText etDialogName = dialogView.findViewById(R.id.et_dialog_name);
        EditText etDialogEmail = dialogView.findViewById(R.id.et_dialog_email);
        EditText etDialogDate = dialogView.findViewById(R.id.et_dialog_date);
        TextView tvDialogStatus = dialogView.findViewById(R.id.tv_edit_dialog_status);
        SwitchCompat swDialogStatus = dialogView.findViewById(R.id.sw_edit_dialog_status);

        etDialogDate.setOnClickListener(v -> showDatePicker(etDialogDate));

        // Pre-fill the fields
        etDialogId.setText(String.valueOf(originalStudent.Id()));
        etDialogName.setText(originalStudent.Name());
        etDialogEmail.setText(originalStudent.Email());
        etDialogDate.setText(originalStudent.DateOnly().toString());
        swDialogStatus.setChecked(originalStudent.Status() == Status.Graduate);

        swDialogStatus.setOnCheckedChangeListener((buttonView, isChecked) ->
                tvDialogStatus.setText(isChecked ? "graduate" : "undergraduate")
        );

        builder.setPositiveButton("Save", (dialog, which) -> {
            // Create edited student with new values
            var editedStudent = new Student(
                    new BigInteger(etDialogId.getText().toString()),
                    etDialogName.getText().toString(),
                    etDialogEmail.getText().toString(),
                    LocalDate.parse(etDialogDate.getText().toString(), DateTimeFormatter.ofPattern("yyyy-M-d")),
                    swDialogStatus.isChecked() ? Status.Graduate : Status.Undergraduate
            );

            // Update the student in the database and call the callback to refresh the UI in StudentDetailsDialog
            viewModel.update(originalStudent, editedStudent);
            callBack.accept(editedStudent);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showDatePicker(EditText etDialogDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    etDialogDate.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}

