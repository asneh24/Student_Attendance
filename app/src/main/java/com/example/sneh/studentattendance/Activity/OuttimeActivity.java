package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneh.studentattendance.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OuttimeActivity extends AppCompatActivity {
    private Spinner spinnerstudentname,spinnervichclename,spinnervichcleno;
    private EditText edittextvichcleno,edittextvichclename;
    private Button savebutton;
    private String key,vichclename,vichleno,name,currentDateTimeString,date,phoneno;
    DatabaseReference databaseReference,db,db1;
    TextView textviewdate;
    List<String>mData=new ArrayList<>();
    List<String> mData1 = new ArrayList<>();
    List<String> mData2 = new ArrayList<>();
    Context context;
    StudentAttendance studentAttendance;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outtime);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(OuttimeActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(OuttimeActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(OuttimeActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(OuttimeActivity.this, "el");
            resources = context.getResources();
        }
        db= FirebaseDatabase.getInstance().getReference("Student");
        databaseReference= FirebaseDatabase.getInstance().getReference("OutTime");
        db1= FirebaseDatabase.getInstance().getReference("Vehicle");
        spinnervichcleno= (Spinner) findViewById(R.id.spinnervichcleno);
        spinnervichclename= (Spinner) findViewById(R.id.spinnervichclename);
        savebutton= (Button) findViewById(R.id.savebutton);
        spinnerstudentname= (Spinner) findViewById(R.id.spinnerstudentname);
        textviewdate=(TextView) findViewById(R.id. textviewdate);
        textviewdate.setText(currentDateTimeString);
        savebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveinformation();
            }
        });
        Getvalue();
        Getvalue1();
        db.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.child("name").getValue(String.class);

                mData.add(value);

                ArrayAdapter adapter = new ArrayAdapter<String>(OuttimeActivity.this, android.R.layout.simple_spinner_item, mData);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerstudentname.setAdapter(adapter);
                Log.e("Helloe","insert"+value);
                spinnerstudentname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                        name=spinnerstudentname.getSelectedItem().toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {


                    }
                });


            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void Getvalue()
    {
        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Vehicle");
        db1.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.child("vichclename").getValue(String.class);

                mData1.add(value);

                ArrayAdapter adapter1 = new ArrayAdapter<String>(OuttimeActivity.this, android.R.layout.simple_spinner_item, mData1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnervichclename.setAdapter(adapter1);
                Log.e("Helloe","insert"+value);
                spinnervichclename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                        vichclename=spinnervichclename.getSelectedItem().toString();
                        Toast.makeText(OuttimeActivity.this, "spinner is"+vichclename, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {


                    }
                });


            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void Getvalue1()
    {
        db1.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.child("vichcleno").getValue(String.class);

                mData2.add(value);

                ArrayAdapter adapter1 = new ArrayAdapter<String>(OuttimeActivity.this, android.R.layout.simple_spinner_item, mData2);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnervichcleno.setAdapter(adapter1);
                Log.e("Helloe","insert"+value);
                spinnervichcleno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                        vichleno=spinnervichcleno.getSelectedItem().toString();
                        Toast.makeText(OuttimeActivity.this, "spinner is"+vichleno, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {


                    }
                });


            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveinformation()
    {

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Toast.makeText(this, currentDateTimeString, Toast.LENGTH_SHORT).show();
//        Vehicle vehicle =new Vehicle(vichclename,vichleno,phoneno,name,currentDateTimeString,key);
//        databaseReference.push().setValue(vehicle);
        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();

    }
}
