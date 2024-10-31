package com.example.lab03_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class StudentDetailsDialog {
    private final Context context;
    private final Student student;
    private final StudentsViewModel viewModel;

    public StudentDetailsDialog(Context context, Student student, StudentsViewModel viewModel) {
        this.context = context;
        this.student = student;
        this.viewModel = viewModel;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog, null);
        builder.setView(dialogView);

        TextView tvDialogId = dialogView.findViewById(R.id.tv_dialog_id);
        TextView tvDialogName = dialogView.findViewById(R.id.tv_dialog_name);
        TextView tvDialogEmail = dialogView.findViewById(R.id.tv_dialog_email);
        TextView tvDialogDate = dialogView.findViewById(R.id.tv_dialog_date);
        TextView tvDialogStatus = dialogView.findViewById(R.id.tv_dialog_status);
        ImageView ivEdit = dialogView.findViewById(R.id.iv_edit);

        // Set initial student details
        updateStudentDetails(tvDialogId, tvDialogName, tvDialogEmail, tvDialogDate, tvDialogStatus);

        // Open EditStudentDialog with a callback to refresh this dialog's UI
        ivEdit.setOnClickListener(v ->
                new EditStudentDialog(context, student, viewModel, () -> {
                    // Re-fetch updated student data and refresh UI
                    updateStudentDetails(tvDialogId, tvDialogName, tvDialogEmail, tvDialogDate, tvDialogStatus);
                }).show()
        );

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void updateStudentDetails(TextView tvId, TextView tvName, TextView tvEmail, TextView tvDate, TextView tvStatus) {
        Student updatedStudent = viewModel.get(student);
        tvId.setText(String.valueOf(updatedStudent.Id()));
        tvName.setText(updatedStudent.Name());
        tvEmail.setText(updatedStudent.Email());
        tvDate.setText(updatedStudent.DateOnly().toString());
        tvStatus.setText(updatedStudent.Status().toString());
    }
}
