package com.example.sneh.studentattendance.Activity;

import android.content.Context;
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
import java.util.Date;

public class LeaveActivity extends AppCompatActivity {
    Spinner spinnerarrivedby;
    Button checekbutton;
    TextView textviewdate,textviewname;
    ImageView imageview;
     int count=0;

    DatabaseReference databaseReference,db;
    Context context;
    StudentAttendance studentAttendance;
    Resources resources;
    String Name,Class,currentDateTimeString,key1,gender,phone,address,drivername,vehiclename1,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(LeaveActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(LeaveActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(LeaveActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(LeaveActivity.this, "el");
            resources = context.getResources();
        }

        db= FirebaseDatabase.getInstance().getReference("Leave");
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
        checekbutton = (Button) findViewById(R.id.checekbutton);
        checekbutton.setText(resources.getString(R.string.check));
        textviewdate=(TextView) findViewById(R.id. textviewdate);
        textviewdate.setText(currentDateTimeString);

        spinnerarrivedby= (Spinner) findViewById(R.id.spinnerarrivedby);
        final String [] vehiclename = {" Bus "," Taxi "," Alone"};


        ArrayAdapter adapter1 = new ArrayAdapter<String>(LeaveActivity.this, android.R.layout.simple_spinner_item, vehiclename);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerarrivedby.setAdapter(adapter1);

        spinnerarrivedby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                vehiclename1=spinnerarrivedby.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


        checekbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check();
            }
        });



    }



    private void check() {
        image=getIntent().getStringExtra("image");

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Toast.makeText(this, currentDateTimeString, Toast.LENGTH_SHORT).show();

        Check check = new Check(Name, Class, vehiclename1, currentDateTimeString, key1, image);
        db.child(key1).setValue(check);

        Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        checekbutton.setEnabled(false);
        checekbutton.setBackgroundColor(Color.GRAY);

    }


}

