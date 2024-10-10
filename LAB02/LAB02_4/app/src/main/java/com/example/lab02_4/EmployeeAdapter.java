package com.example.lab02_4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lab02_4.Models.Employee;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private final Activity context;

    public EmployeeAdapter(Activity context, int layoutID, List<Employee>
            objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
//        If convertView is null, it means there is no recycled view available,
//        so a new view is created by inflating the item_employee layout.
//        The LayoutInflater is used to create a new view from the XML layout file.

//        convertView represents the view for a single item in the list
//        parent represents the container for the views

        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(
                        R.layout.item_employee,
                        parent,
                        false);
        }

        // Get item
        Employee employee = getItem(position);
        // Get view
        TextView tvFullName =
                convertView.findViewById(R.id.item_employee_tv_full_name);
        TextView tvPosition =
                convertView.findViewById(R.id.item_employee_tv_position);
        ImageView ivManager =
                convertView.findViewById(R.id.item_employee_iv_manager);
        LinearLayout llParent =
                convertView.findViewById(R.id.item_employee_ll_parent);

        // Set fullname
        if (employee.getFullName() != null) {
            tvFullName.setText(employee.getFullName());
        } else tvFullName.setText("");
        // If this is a manager -> show icon manager. Otherwise, show Staff in

        if (employee.isManager()) {
            ivManager.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
        } else {
            ivManager.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText(context.getString(R.string.staff));
        }

        // Show different color backgrounds for 2 continuous employees
        if (position % 2 == 0) {
            llParent.setBackgroundResource(R.color.white);
        } else {
            llParent.setBackgroundResource(R.color.light_blue);
        }
        return convertView;
    }
}