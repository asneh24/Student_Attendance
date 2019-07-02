package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckinActivity extends AppCompatActivity {
    Spinner  spinnerarrivedby;
    ImageView imageview;
    Button checekinbutton;
    TextView textviewdate,textviewname;

    List<String>mData=new ArrayList<>();


    String Name,Class,currentDateTimeString,key1,gender,phone,address,drivername,vehiclename1,image;

    DatabaseReference databaseReference,db;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(CheckinActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(CheckinActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(CheckinActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(CheckinActivity.this, "el");
            resources = context.getResources();
        }
        Bundle extras = getIntent().getExtras();
          key1 = extras.getString("key");
         image=getIntent().getStringExtra("image");
        Log.e("image","image"+image);
        String name=extras.getString("name");

        imageview=(ImageView)findViewById(R.id.imageview) ;
         Picasso.get().load(image).into(imageview);
        textviewname=(TextView)findViewById(R.id.textviewname) ;
        textviewname.setText(resources.getString(R.string.StudentName));

        textviewname.setText(name);



        spinnerarrivedby=(Spinner)findViewById(R.id.spinnerarrivedby);

       Toast.makeText(this, "key is"+key1, Toast.LENGTH_SHORT).show();
        databaseReference= FirebaseDatabase.getInstance().getReference("Checkin");


        checekinbutton= (Button) findViewById(R.id.checekinbutton);
        checekinbutton.setText(resources.getString(R.string.Check));
        textviewdate=(TextView) findViewById(R.id. textviewdate);

        textviewdate.setText(currentDateTimeString);

     final String [] vehiclename = {"Bus","Taxi","Alone"};


        ArrayAdapter adapter1 = new ArrayAdapter<String>(CheckinActivity.this, android.R.layout.simple_spinner_item, vehiclename);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerarrivedby.setAdapter(adapter1);

        spinnerarrivedby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                vehiclename1=spinnerarrivedby.getSelectedItem().toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


        checekinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                Log.e("keys", "key" + key1);
                currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Toast.makeText(CheckinActivity.this, currentDateTimeString, Toast.LENGTH_SHORT).show();
                Check check = new Check(Name, Class, vehiclename1, currentDateTimeString, key1, image);
                databaseReference.child(key1).setValue(check);
                Toast.makeText(CheckinActivity.this, "check in", Toast.LENGTH_SHORT).show();
                checekinbutton.setEnabled(false);
                Intent intent=new Intent(CheckinActivity.this,DashboardActivity.class);
                startActivity(intent);
                checekinbutton.setBackgroundColor(Color.GRAY);


            }
        });


    }




}
