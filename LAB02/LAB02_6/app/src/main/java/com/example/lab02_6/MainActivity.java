package com.example.lab02_6;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab02_6.Models.Employee;

public class MainActivity extends AppCompatActivity {
    private EmployeeViewModel viewModel;
    private EmployeeAdapter adapter;
    private CheckBox cbIsAManager;
    private EditText etId, etFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        adapter = new EmployeeAdapter(this, viewModel);
        cbIsAManager = findViewById(R.id.cb_is_manager);
        etId = findViewById(R.id.et_employee_id);
        etFullName = findViewById(R.id.et_employee_full_name);
        RecyclerView rcEmployees = findViewById(R.id.rc_employees);

        rcEmployees.setLayoutManager(new LinearLayoutManager(this));
        rcEmployees.setAdapter(adapter);

        viewModel.getEmployeeAdded().observe(this, employee -> {
            adapter.notifyItemInserted(viewModel.getEmployees().getValue().size() - 1);
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
        });

        viewModel.getEmployeeRemoved().observe(this, position -> {
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, viewModel.getEmployees().getValue().size());
            Toast.makeText(this, "Removed successfully", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_employee_submit).setOnClickListener(this::onAddEmployee);
    }

    private void onAddEmployee(View view) {
        String id = etId.getText().toString();
        String name = etFullName.getText().toString();
        boolean isManager = cbIsAManager.isChecked();

        Employee employee = new Employee(id, name, isManager);
        viewModel.addEmployee(employee);
    }
}