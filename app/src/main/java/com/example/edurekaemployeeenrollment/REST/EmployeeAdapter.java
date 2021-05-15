package com.example.edurekaemployeeenrollment.REST;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edurekaemployeeenrollment.Model.Employee;
import com.example.edurekaemployeeenrollment.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {

    List<Employee> employees   =new ArrayList<>();;
    Context context;   LayoutInflater inflater;

    EmployeeUtil employeeUtil;
    public  EmployeeAdapter()
    {
//        this.context=context;
//        inflater= LayoutInflater.from(context);

//        employeeUtil=new EmployeeUtil(context);
//        employees=    employeeUtil.getAllEmployees() ;
//        List<Employee> allEmployees =
                getAllEmployees();

    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.album_card, parent, false);
        EmployeeViewHolder viewHolder = new EmployeeViewHolder( view );


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {

        holder.Name.setText(employees.get(position).getEmployeeName());

        holder.imageView.setTag(holder);


        if(employees.get(position).getProfilePhoto()!=null) {
            byte[] imageByteArray = Base64.getDecoder().decode(employees.get(position).getProfilePhoto());
            Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));

            holder.imageView.setImageDrawable(image);
        }
        holder.imageView.setOnClickListener(clickListener);

        holder.Name.setTag(holder); // set this so while clicking on text it calls the clickListener

        holder.Name.setOnClickListener(clickListener);


    }
//    private List<Employee> employees=new ArrayList<Employee>();
    APIInterface apiInterface;
//    Gson gson = new Gson();


    public  List<Employee> getAllEmployees()
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();
//       builder.setPrettyPrinting();
//   builder.setDateFormat("dd-M-yyyy");
        try {
            apiInterface = ServiceGenerator.getClient().create(APIInterface.class);
            final Call<List< Employee>> call = apiInterface.GetAllEmployees();
            call.enqueue(new Callback<List< Employee>>() {
                @Override
                public void onResponse(Call<List< Employee>> call, Response<List< Employee>> response) {

                    Type employeetype = new TypeToken<ArrayList<Employee>>(){}.getType();
                    String json=gson.fromJson( response.body().toString(),employeetype);
                    String jsons=response.body().toString();

//                    List<Employee> emplist =gson.fromJson(response.body(),employeetype);

//                    if(emplist.size()>0)
//                    {
//                        employees=emplist;
//                    }

                }

                @Override
                public void onFailure(Call<List< Employee>> call, Throwable t) {

                }
            });
        }catch (Exception e)
        {
            Log.d("MYTAG",e.fillInStackTrace().toString());
        }
        return  employees;
    }


    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            EmployeeViewHolder vholder = (EmployeeViewHolder) v.getTag();
            int position = vholder.getPosition();


//            Toast.makeText(context,"This is position "+listname.get(position), Toast.LENGTH_LONG ).show();

//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone[position]) );
//            context.startActivity(intent);


        }
    };
    @Override
    public int getItemCount() {
        return employees.size();
    }
}
