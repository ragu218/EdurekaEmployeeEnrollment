package com.example.edurekaemployeeenrollment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edurekaemployeeenrollment.REST.EmployeeAdapter;

public class EmployeeList extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeeslist);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        EmployeeAdapter employeeAdapter=new EmployeeAdapter();
        recyclerView.setAdapter(employeeAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));



    }

}
