package com.example.edurekaemployeeenrollment.REST;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {
    public static final String BASE_URL="http://13.127.90.185:8089/api/Employee/";
    private  static  Retrofit retrofit=null;



    public static Retrofit getClient()
    {
        if(retrofit==null)
        {
            GsonBuilder gsonBuilder=new GsonBuilder();
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            gsonBuilder.setLenient();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                     .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))

                    .build();
        }
        return  retrofit;
    }
}
