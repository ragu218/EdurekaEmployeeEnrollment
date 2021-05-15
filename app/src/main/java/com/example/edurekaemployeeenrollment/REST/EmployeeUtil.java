package com.example.edurekaemployeeenrollment.REST;

import android.content.Context;
import android.util.Log;

import com.example.edurekaemployeeenrollment.Model.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeUtil {


    public  EmployeeUtil( )
    {

    }

//    Gson gson = new Gson();
Gson gson = new Gson();

private List<Employee> employees=new ArrayList<Employee>();
    APIInterface apiInterface;
    public  List<Employee> getAllEmployees()
    {

        try {
            apiInterface = ServiceGenerator.getClient().create(APIInterface.class);
            final Call<List<Employee>> call = apiInterface.GetAllEmployees();
            call.enqueue(new Callback<List<Employee>>() {
                @Override
                public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                    Type employeetype = new TypeToken<ArrayList<Employee>>(){}.getType();
                    List<Employee> emplist =gson.fromJson((JsonElement) response.body(),employeetype);

                   setEmployees(emplist);

                }

                @Override
                public void onFailure(Call<List<Employee>> call, Throwable t) {

                }
            });
        }catch (Exception e)
        {
            Log.d("MYTAG",e.fillInStackTrace().toString());
        }
        return  employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
