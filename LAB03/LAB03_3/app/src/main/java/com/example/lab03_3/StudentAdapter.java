package com.example.lab03_3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

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

        holder.cvStudent.setOnClickListener(v -> {
            Log.d("StudentAdapter", "Item clicked: " + currentStudent.Id());
            showStudentDetailsDialog(currentStudent);
        });
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
        Log.d("StudentAdapter", "showStudentDetailsDialog called");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog, null);
        builder.setView(dialogView);

        // Ánh xạ các TextView từ dialog
        TextView tvDialogId = dialogView.findViewById(R.id.tv_dialog_id);
        TextView tvDialogName = dialogView.findViewById(R.id.tv_dialog_name);
        TextView tvDialogEmail = dialogView.findViewById(R.id.tv_dialog_email);
        TextView tvDialogDate = dialogView.findViewById(R.id.tv_dialog_date);
        TextView tvDialogStatus = dialogView.findViewById(R.id.tv_dialog_status);

        // Thiết lập thông tin cho các TextView
        tvDialogId.setText(String.format("ID: %d", currentStudent.Id()));
        tvDialogName.setText(String.format("Name: %s", currentStudent.Name()));
        tvDialogEmail.setText(String.format("Email: %s", currentStudent.Email()));
        tvDialogDate.setText(String.format("Date: %s", currentStudent.DateOnly()));
        tvDialogStatus.setText(String.format("Status: %s", currentStudent.Status().toString()));

        builder.setPositiveButton("OK", (dialog, which) -> {

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
