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

public class CheckoutActivity extends AppCompatActivity {
    Spinner spinnerarrivedby;
    Button Checkoutbutton;
    ImageView imageview;
    Button checekinbutton;
    TextView textviewdate,textviewname;
    List<String> mData=new ArrayList<>();

    String Selectedvalue,Name,Class,section,rollno,currentDateTimeString,key1,gender,phone,address,vehiclename1,image;

    DatabaseReference databaseReference,db;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chcekout);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(CheckoutActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(CheckoutActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(CheckoutActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(CheckoutActivity.this, "el");
            resources = context.getResources();
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Checkout");
      Bundle extras = getIntent().getExtras();
          key1 = extras.getString("key");
       image=extras.getString("image");

        String name=extras.getString("name");
        imageview=(ImageView)findViewById(R.id.imageview) ;
        Picasso.get().load(image).into(imageview);

        textviewname=(TextView)findViewById(R.id.textviewname) ;
        textviewname.setText(name);



        Checkoutbutton= (Button) findViewById(R.id.Checkoutbutton);

        spinnerarrivedby= (Spinner) findViewById(R.id.spinnerarrivedby);
        final String [] vehiclename = {" Bus "," Taxi "," Alone"};


        ArrayAdapter adapter1 = new ArrayAdapter<String>(CheckoutActivity.this, android.R.layout.simple_spinner_item, vehiclename);
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


        Checkoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkout();
            }
        });


    }

    private void checkout()
    {

        Log.e("key","key"+key1);
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Toast.makeText(this, currentDateTimeString, Toast.LENGTH_SHORT).show();

        Check check=new Check(Name,Class,vehiclename1,currentDateTimeString,key1,image);

        databaseReference.child(key1).setValue(check);

        Toast.makeText(this, "check out", Toast.LENGTH_SHORT).show();
        Checkoutbutton.setEnabled(false);
        Checkoutbutton.setBackgroundColor(Color.GRAY);
        Intent intent=new Intent(CheckoutActivity.this,DashboardActivity.class);
        startActivity(intent);

    }


}
