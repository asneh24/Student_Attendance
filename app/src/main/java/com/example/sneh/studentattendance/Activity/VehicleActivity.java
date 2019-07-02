package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Vehicle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class VehicleActivity extends AppCompatActivity {
    private Spinner spinnervichclename;
    private EditText edittextvehicleno,edittextdrivername,edittextvehiclename,edittextphone;
    private Button Addbutton,exit;
    private String key,Vehiclename,vichleno, date,name,phoneno;
    private DatabaseReference databaseReference;
    private List<String>list=new ArrayList<>();
    StudentAttendance studentAttendance;
    String userid;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical);
        databaseReference= FirebaseDatabase.getInstance().getReference("Bus");
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        if(studentAttendance.getLang(VehicleActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(VehicleActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(VehicleActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(VehicleActivity.this, "el");
            resources = context.getResources();
        }
        edittextvehicleno=(EditText)findViewById(R.id.edittextvehicleno);
        edittextvehicleno.setHint(resources.getString(R.string.VehicleNo));
        Addbutton= (Button) findViewById(R.id.Addbutton);
        Addbutton.setText(resources.getString(R.string.Add));

        exit = findViewById(R.id.exitbutton);
        exit.setText(resources.getString(R.string.exit));

        edittextdrivername=(EditText)findViewById(R.id.edittextdrivername);
        edittextdrivername.setHint(resources.getString(R.string.DriverName));
        edittextvehiclename=(EditText)findViewById(R.id.edittextvehiclename);
        edittextvehiclename.setHint(resources.getString(R.string.VehicleName));
        edittextphone=(EditText)findViewById(R.id.edittextphone);
        edittextphone.setHint(resources.getString(R.string.phoneno));



        /* spinnervichclename= (Spinner) findViewById(R.id.spinnervichclename);
       String vichcle[]= {"Bus","Taxi","Self","Left","Self twoviler","selffourviler"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String >(this,android.R.layout.simple_spinner_item,vichcle);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnervichclename.setAdapter(arrayAdapter);
        spinnervichclename.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                vichclename=spinnervichclename.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        }); */
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Addbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              vichcleinformation();

            }
        });
    }

    private void vichcleinformation()
    {

        vichleno=edittextvehicleno.getText().toString();
        Vehiclename=edittextvehiclename.getText().toString();
        name=edittextdrivername.getText().toString();
        phoneno=edittextphone.getText().toString();


        if (TextUtils.isEmpty(Vehiclename))
        {
            Toast.makeText(VehicleActivity.this,"Please Enter Bus Name ",Toast.LENGTH_SHORT).show();
        }
        else{
            key=databaseReference.push().getKey();
            Vehicle vehicle1 =new Vehicle(Vehiclename,vichleno,phoneno,name,key);
            databaseReference.child(userid).child(key).setValue(vehicle1);
            Toast.makeText(this, "Bus Information Added Successfully", Toast.LENGTH_SHORT).show();
            clr();

        }
    }
    public void clr()
    {
        edittextvehiclename.setText("");
        edittextvehicleno.setText("");
        edittextdrivername.setText("");
        edittextphone.setText("");
    }
}
