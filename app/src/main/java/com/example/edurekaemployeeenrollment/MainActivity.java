package com.example.edurekaemployeeenrollment;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edurekaemployeeenrollment.Model.Employee;
import com.example.edurekaemployeeenrollment.REST.APIInterface;
import com.example.edurekaemployeeenrollment.REST.ServiceGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ShapeableImageView imageView;
    private EditText Name,Age,DOB;
    private Button addEmp;
    final Calendar     myCalendar = Calendar.getInstance();
    public  static final String MYTAG="EmployeeEnroll";

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int RESULT_LOAD_IMAGE = 1;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ShapeableImageView) findViewById(R.id.empPhoto);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/


                Intent cameraIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(cameraIntent, RESULT_LOAD_IMAGE);

            }
        });

        //
        Name=(EditText)findViewById(R.id.name);
        Age=(EditText)findViewById(R.id.age);
        DOB=(EditText)findViewById(R.id.dob);

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

             date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
    }
    private void updateLabel() {
        String myFormat = "dd-MM-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DOB.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);


//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//
//            cursor.close();
//            // String picturePath contains the path of selected Image
        }

        getImageViewtoBase64();
    }

    private String getImageViewtoBase64() {
        String base64Image = "";
        if(imageView.getDrawable()!=null) {
            Drawable drawable = imageView.getDrawable();
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);

            base64Image = Base64.getEncoder().encodeToString(imageInByte);

        }return  base64Image;
    }


    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);


        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }
    APIInterface apiInterface;
    Gson gson = new Gson();
    public void btnAddEmployee(View view) throws ParseException {
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        Employee employee=new Employee();
        employee.setEmployeeName(Name.getText().toString());
        employee.setAge(Integer.parseInt( Age.getText().toString()));
        employee.setID(0);

        String string = DOB.getText().toString();
        DateFormat format = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH);
        Date date = format.parse(string);

        employee.setDOB(date);

        employee.setProfilePhoto(getImageViewtoBase64());


        String userJson = gson.toJson(employee);
try {


    apiInterface = ServiceGenerator.getClient().create(APIInterface.class);
    final Call<String> call = apiInterface.AddEmployee(userJson);
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String s = response.body();
            progressBar.setVisibility(View.INVISIBLE) ;
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onFailure(Call<String> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE) ;
            Toast.makeText(MainActivity.this, "Error Loading" + t.fillInStackTrace().toString(), Toast.LENGTH_SHORT).show();
            Log.d(MYTAG, t.fillInStackTrace().toString());

        }
    });
}catch (Exception e)
{
    progressBar.setVisibility(View.INVISIBLE) ;
    Log.d(MYTAG, e.fillInStackTrace().toString());
}
        progressBar.setVisibility(View.INVISIBLE) ;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.lisEmployees){
            Intent intent=new Intent(MainActivity.this,EmployeeList.class);
            startActivity(intent);
        }
//
//        if (item.getItemId()==R.id.instructions){
//            Toast.makeText(this, "Hnstructions Item", Toast.LENGTH_SHORT).show();
//        }
//
//        if (item.getItemId()==R.id.hotels){
//            Toast.makeText(this, "Hotels Item", Toast.LENGTH_SHORT).show();
//        }

        return super.onOptionsItemSelected(item);
    }


}
