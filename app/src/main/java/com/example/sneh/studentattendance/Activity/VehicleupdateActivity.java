package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.sneh.studentattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class VehicleupdateActivity extends AppCompatActivity {

    private EditText edittextdrivername,edittextvechiclename,edittextvechicleno,edittextphoneno;
    private Button deletebutton,updatebutton;
    private String key,vehiclename,vehicleno,drivername, currentDateTimeString,phoneno;
    DatabaseReference databaseReference;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicalupate);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(VehicleupdateActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(VehicleupdateActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(VehicleupdateActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(VehicleupdateActivity.this, "el");
            resources = context.getResources();
        }

        key=getIntent().getStringExtra("key");
        vehiclename=getIntent().getStringExtra("vehiclename");
        vehicleno=getIntent().getStringExtra("vehiclenum");
        drivername=getIntent().getStringExtra("name");
        phoneno=getIntent().getStringExtra("phonenum");
         Log.e("key","buskey"+key);

        edittextdrivername= (EditText) findViewById(R.id.edittextdrivername);
        edittextdrivername.setHint(resources.getString(R.string.DriverName));
        edittextdrivername.setText(drivername);
        edittextvechiclename= (EditText) findViewById(R.id.edittextvehiclename);
        edittextvechiclename.setHint(resources.getString(R.string.VehicleName));
        edittextvechiclename.setText(vehiclename);
        edittextvechicleno= (EditText) findViewById(R.id. edittextvehicleno);
        edittextvechicleno.setHint(resources.getString(R.string.VehicleNo));
        edittextvechicleno.setText(vehicleno);
        edittextphoneno=(EditText)findViewById(R.id.edittextphoneno) ;
        edittextphoneno.setHint(resources.getString(R.string.phoneno));
        edittextphoneno.setText(phoneno);

        deletebutton= (Button) findViewById(R.id.deletebutton);
        deletebutton.setText(resources.getString(R.string.Delete));

        updatebutton= (Button) findViewById(R.id.updatebutton);
        updatebutton.setText(resources.getString(R.string.Update));

        updatebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               update();

            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String userid = studentAttendance.getUserid();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bus");
                Log.e("My Tag","Keyy for Delete = "+key);
                databaseReference.child(userid).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(VehicleupdateActivity.this,"Bus Information Delete Successfully",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });


            }
        });


    }

    private void update()
    {
        if (edittextvechiclename.length() == 0) {
            Toast.makeText(VehicleupdateActivity.this,"Please Enter Bus Name",Toast.LENGTH_SHORT).show();
        }
        else{
            databaseReference= FirebaseDatabase.getInstance().getReference("Bus");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.e("Mytag", "data==" + dataSnapshot.getValue());
                    if(dataSnapshot != null)
                    {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            Log.e("Mytag", "data1==" + dataSnapshot1.getValue());
                            if(dataSnapshot1 !=null)
                            {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                                {
                                    Log.e("Mytag", "data2==" + dataSnapshot2.getValue());
                                    Log.e("Mytag", "keyyfromdata" + dataSnapshot2.getKey());
                                    if (dataSnapshot2.getKey().equals(key)) {
                                        Map<String, Object> taskMap = new HashMap<String, Object>();

                                        taskMap.put("name",edittextdrivername.getText().toString());
                                        taskMap.put("vichclename",edittextvechiclename.getText().toString());
                                        taskMap.put("phoneno",edittextphoneno.getText().toString());
                                        taskMap.put("vichcleno",edittextvechicleno.getText().toString());

                                        dataSnapshot2.getRef().updateChildren(taskMap);
                                        Toast.makeText(VehicleupdateActivity.this,"Bus Information Updated Successfully",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }







}


