package com.example.lab03_3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab03_3.R;
import com.example.lab03_3.dialogs.StudentDetailsDialog;
import com.example.lab03_3.models.Student;
import com.example.lab03_3.viewmodels.StudentsViewModel;

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
        holder.bindData(currentStudent);

        int backgroundColor = (position % 2 == 0) ?
                ContextCompat.getColor(context, R.color.white) :
                ContextCompat.getColor(context, R.color.light_blue);

        // Show different color backgrounds for 2 continuous employees

        int elevation = (position % 2 == 0) ? 8 : 0;

        holder.cvStudent.setCardBackgroundColor(backgroundColor);
        holder.cvStudent.setCardElevation(elevation);

        holder.cvStudent.setOnLongClickListener(v -> {
            viewModel.delete(currentStudent);
            notifyItemRangeChanged(position, getItemCount());

            return true;
        });

        holder.cvStudent.setOnClickListener(v ->
                new StudentDetailsDialog(context, currentStudent, viewModel).show()
        );
    }

    @Override
    public int getItemCount() {
        return viewModel.getAll().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId, tvName, tvStatus;
        private final CardView cvStudent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_student_status);
            cvStudent = itemView.findViewById(R.id.cv_contact);
        }

        public void bindData(Student student) {
            tvId.setText(String.format(Locale.getDefault(), "ID: %d", student.Id()));
            tvName.setText(String.format(Locale.getDefault(), "Name: %s", student.Name()));
            tvStatus.setText(String.format(Locale.getDefault(), "Status: %s", student.Status().toString()));
        }
    }
}
