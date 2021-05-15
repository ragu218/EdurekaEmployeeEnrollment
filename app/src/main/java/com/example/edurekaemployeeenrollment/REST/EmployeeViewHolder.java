package com.example.edurekaemployeeenrollment.REST;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edurekaemployeeenrollment.R;


public class EmployeeViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView Id,Name,Age,Dob;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView= (ImageView)itemView.findViewById(R.id.thumbnail);
        Id=(TextView)itemView.findViewById(R.id.ID);
        Name=(TextView)itemView.findViewById(R.id.name);
        Age=(TextView)itemView.findViewById(R.id.age);
        Dob=(TextView)itemView.findViewById(R.id.dob);


    }
}
