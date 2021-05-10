package com.example.edurekaemployeeenrollment.REST;

import com.example.edurekaemployeeenrollment.Model.Employee;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @Headers("Content-Type: application/json")
    @POST("AddEmployee")
    Call<String> AddEmployee(@Body String employee);
}
