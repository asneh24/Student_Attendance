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

import com.example.sneh.studentattendance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class TaxiupdateActivity extends AppCompatActivity {

    private EditText edittextdrivername,edittextvehiclename,edittextvehicleno,edittextphoneno;
    private Button updatebutton,deletebutton;
    private String vehicleName,vehicleNo,key,name,phoneno;
    private DatabaseReference databaseReference;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicalupate);

        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(TaxiupdateActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(TaxiupdateActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(TaxiupdateActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(TaxiupdateActivity.this, "el");
            resources = context.getResources();
        }
        key=getIntent().getStringExtra("key");
        vehicleName=getIntent().getStringExtra("vehiclename");
        vehicleNo=getIntent().getStringExtra("vehiclenum");
        name=getIntent().getStringExtra("name");
        phoneno=getIntent().getStringExtra("phonenum");

        edittextdrivername=(EditText)findViewById(R.id.edittextdrivername);
        edittextdrivername.setHint(resources.getString(R.string.DriverName));
        edittextdrivername.setText(name);
        edittextvehicleno=(EditText)findViewById(R.id. edittextvehicleno);
        edittextvehicleno.setHint(resources.getString(R.string.VehicleNo));
        edittextvehicleno.setText(vehicleNo);
        edittextvehiclename=(EditText)findViewById(R.id. edittextvehiclename);
        edittextvehiclename.setHint(resources.getString(R.string.VehicleName));
        edittextvehiclename.setText(vehicleName);
        edittextphoneno=(EditText)findViewById(R.id. edittextphoneno);
        edittextphoneno.setHint(resources.getString(R.string.phoneno));
        edittextphoneno.setText(phoneno);

        deletebutton= (Button) findViewById(R.id.deletebutton);
        deletebutton.setText(resources.getString(R.string.Delete));

        updatebutton= (Button) findViewById(R.id.updatebutton);
        updatebutton.setText(resources.getString(R.string.Update));
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Update();
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = studentAttendance.getUserid();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Taxi");
                Log.e("My Tag","Keyy for Delete = "+key);
                databaseReference.child(userid).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(TaxiupdateActivity.this,"Taxi Details Deleted Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });



            }
        });

    }

    private void Update()
    {
       if (edittextvehiclename.length() == 0) {
            Toast.makeText(TaxiupdateActivity.this,"Please Enter Taxi Name",Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference= FirebaseDatabase.getInstance().getReference("Taxi");
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
                                        taskMap.put("vichclename",edittextvehiclename.getText().toString());
                                        taskMap.put("phoneno",edittextphoneno.getText().toString());
                                        taskMap.put("vichcleno",edittextvehicleno.getText().toString());

                                        dataSnapshot2.getRef().updateChildren(taskMap);
                                        Toast.makeText(TaxiupdateActivity.this,"Taxi Information Updated Successfully",Toast.LENGTH_SHORT).show();
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

