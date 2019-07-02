package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Vehicle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaxientryActivity extends AppCompatActivity {
    EditText edittextvechiclename,edittextvichcleno,edittextphone,edittextdrivername;
    Button Addbutton,exit;
    String Vehiclename,Vechicleno,phoneno,key,name,date;
    DatabaseReference databaseReference;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxientry);
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        if(studentAttendance.getLang(TaxientryActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(TaxientryActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(TaxientryActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(TaxientryActivity.this, "el");
            resources = context.getResources();
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Taxi");
        edittextvechiclename= (EditText) findViewById(R.id. edittextvechiclename);
        edittextvechiclename.setHint(resources.getString(R.string.VehicleName));
        edittextvichcleno= (EditText) findViewById(R.id. edittextvichcleno);
        edittextvichcleno.setHint(resources.getString(R.string.VehicleNo));
        edittextphone= (EditText) findViewById(R.id.edittextphone);
        edittextphone.setHint(resources.getString(R.string.phoneno));
        edittextdrivername= (EditText) findViewById(R.id.edittextdrivername);
        edittextdrivername.setHint(resources.getString(R.string.DriverName));
        Addbutton= (Button) findViewById(R.id.Addbutton);
        Addbutton.setText(resources.getString(R.string.Add));

        exit = findViewById(R.id.exitbutton);
        exit.setText(resources.getString(R.string.exit));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               insert();

            }
        });
    }

    private void insert()
    {

      Vehiclename=edittextvechiclename.getText().toString();
        Vechicleno=edittextvichcleno.getText().toString();
        phoneno=edittextphone.getText().toString();
        name=edittextdrivername.getText().toString();

        if (TextUtils.isEmpty(Vehiclename))
        {
            Toast.makeText(TaxientryActivity.this,"Please Enter Taxi Name",Toast.LENGTH_SHORT).show();
        }

        else
        {
            String key=databaseReference.push().getKey();
            Vehicle vehicle =new Vehicle(Vehiclename,Vechicleno,phoneno,name,key);
            databaseReference.child(userid).child(key).setValue(vehicle);
            Toast.makeText(this, "Taxi Information Added Successfully", Toast.LENGTH_SHORT).show();
            clr();

        }


    }
    public void clr()
    {
        edittextvechiclename.setText("");
        edittextvichcleno.setText("");
        edittextphone.setText("");
        edittextdrivername.setText("");
    }
}
