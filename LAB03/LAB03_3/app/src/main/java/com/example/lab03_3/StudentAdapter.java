package com.example.lab03_3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Consumer;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    private final Context context;
    private final StudentsViewModel viewModel;

    public StudentAdapter(Context context, StudentsViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(context);
        var view = inflater.inflate(R.layout.rv_items, parent, false);

        return new StudentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder holder, int position) {
        var students = viewModel.getObservableStudents().getValue();

        if (students == null) return;

        var currentStudent = students.get(position);

        holder.tvId.setText(String.format(Locale.getDefault(), "ID: %d", currentStudent.Id()));
        holder.tvName.setText(String.format(Locale.getDefault(), "Name: %s", currentStudent.Name()));
        holder.tvStatus.setText(String.format(Locale.getDefault(), "Status: %s", currentStudent.Status().toString()));

        holder.cvStudent.setOnLongClickListener(v -> {
            viewModel.delete(currentStudent);
            return true;
        });

        holder.cvStudent.setOnClickListener(v -> showStudentDetailsDialog(currentStudent));
    }

    @Override
    public int getItemCount() {
        return viewModel.getAll().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvStatus;
        CardView cvStudent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_student_status);
            cvStudent = itemView.findViewById(R.id.cv_contact);
        }
    }

    private void showStudentDetailsDialog(Student currentStudent) {
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

        ivEdit.setOnClickListener(v -> showEditStudentDialog(currentStudent, editedStudent -> {
            // Update the dialog with the new student details
            tvDialogId.setText(String.valueOf(editedStudent.Id()));
            tvDialogName.setText(editedStudent.Name());
            tvDialogEmail.setText(editedStudent.Email());
            tvDialogDate.setText(editedStudent.DateOnly().toString());
            tvDialogStatus.setText(editedStudent.Status().toString());
        }));

        builder.setPositiveButton("OK", (dialog, which) -> {

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showEditStudentDialog(Student student, Consumer<Student> onStudentEdited) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Student");

        // Inflate the dialog with custom view
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_student, null);
        builder.setView(dialogView);

        // Initialize the dialog fields
        EditText etDialogId = dialogView.findViewById(R.id.et_dialog_id);
        EditText etDialogName = dialogView.findViewById(R.id.et_dialog_name);
        EditText etDialogEmail = dialogView.findViewById(R.id.et_dialog_email);
        EditText etDialogDate = dialogView.findViewById(R.id.et_dialog_date);
        TextView tvDialogStatus = dialogView.findViewById(R.id.tv_edit_dialog_status);
        SwitchCompat swDialogStatus = dialogView.findViewById(R.id.sw_edit_dialog_status);

        etDialogDate.setOnClickListener(v -> {
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
        });

        // Pre-fill the fields with current student data
        etDialogId.setText(String.valueOf(student.Id()));
        etDialogName.setText(student.Name());
        etDialogEmail.setText(student.Email());
        etDialogDate.setText(student.DateOnly().toString());
        swDialogStatus.setChecked(student.Status() == Status.Graduate);

        swDialogStatus.setOnCheckedChangeListener((buttonView, isChecked) -> tvDialogStatus.setText(isChecked ? "graduate" : "undergraduate"));

        // Set up the buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Update the student information
            var editedStudent = (new Student(
                    new BigInteger(etDialogId.getText().toString()),
                    etDialogName.getText().toString(),
                    etDialogEmail.getText().toString(),
                    LocalDate.parse(etDialogDate.getText().toString(), DateTimeFormatter.ofPattern("yyyy-M-d")),
                    swDialogStatus.isChecked() ? Status.Graduate : Status.Undergraduate
            ));

            viewModel.update(student, editedStudent);
            onStudentEdited.accept(editedStudent);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
