package com.example.lab02_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab02_6.Models.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
    private final Context context;
    private final List<Employee> employees;

    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Where we inflate the layout for each item (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_employee, parent, false);

        return new EmployeeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.MyViewHolder holder, int position) {
        // Assigning values to the views we created in the item_employee layout file
        // based on the position of the recycler view

        var currentEmployee = employees.get(position);

        holder.tvEmployeeName.setText(employees.get(position).getFullName());
        holder.tvEmployeePosition.setText(currentEmployee.isManager() ? "" : "Staff");
        holder.ivEmployeeIcon.setVisibility(currentEmployee.isManager() ? View.VISIBLE : View.GONE);

        // Show different color backgrounds for 2 continuous employees


        int backgroundColor = (position % 2 == 0) ?
                ContextCompat.getColor(context, R.color.white) :
                ContextCompat.getColor(context, R.color.light_blue);

        int elevation = (position % 2 == 0) ? 8 : 0;

        holder.cvEmployeeInfoContainer.setCardBackgroundColor(backgroundColor);

        holder.cvEmployeeInfoContainer.setCardElevation(elevation);

        holder.itemView.setOnLongClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                employees.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, employees.size());

                Toast.makeText(context, "Removed successfully", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        // Recycler view wants to know how many items we have
        return employees.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from the item_employee layout file
        // kinda like in the onCreate method

        CardView cvEmployeeInfoContainer;
        TextView tvEmployeeName;
        TextView tvEmployeePosition;
        ImageView ivEmployeeIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cvEmployeeInfoContainer = itemView.findViewById(R.id.cv_rc_employee_container);
            tvEmployeeName = itemView.findViewById(R.id.tv_rc_employee_name);
            tvEmployeePosition = itemView.findViewById(R.id.tv_rc_employee_position);
            ivEmployeeIcon = itemView.findViewById(R.id.iv_rc_employee_icon);
        }
    }
}