package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sneh.studentattendance.Adapter.CheckoutearlyAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Studentinformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LeavefectchActivity extends AppCompatActivity {

    TextView datee,timee,txtstd,txtallstd;
    Button btnsubmit,btnreset;
    private CheckoutearlyAdapter adapter;
    private RecyclerView recycler_view;
    private DatabaseReference databaseReference;
    ArrayList<String> arrayStudentList;
    ArrayList<String> arrayStudentrollnum;
    CheckBox checkboxallstd;
    StudentAttendance studentAttendance;
    String userid;
    Context context;
    Resources resources;
    String vehtypee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leavefectch);

        vehtypee = getIntent().getStringExtra("Vehtype");
        Log.e("MyTag", "vehtypee==" + vehtypee);
        studentAttendance = (StudentAttendance) getApplication();
        userid = studentAttendance.getUserid();
        if (studentAttendance.getLang(LeavefectchActivity.this) == 0) {
            context = LocaleManager.setNewLocale(LeavefectchActivity.this, "en");
            resources = context.getResources();
        } else if (studentAttendance.getLang(LeavefectchActivity.this) == 1) {
            context = LocaleManager.setNewLocale(LeavefectchActivity.this, "el");
            resources = context.getResources();
        }

        datee = findViewById(R.id.datee);
        timee = findViewById(R.id.timee);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnreset = findViewById(R.id.btnreset);
        datee.setHint(resources.getString(R.string.Datee));
        timee.setHint(resources.getString(R.string.Timee));
        btnsubmit.setText(resources.getString(R.string.Submit));
        btnreset.setText(resources.getString(R.string.Reset));
        txtstd = findViewById(R.id.txtstd);
        txtallstd = findViewById(R.id.txtallstd);

        if (vehtypee.equalsIgnoreCase("Bus")) {
            txtstd.setText(resources.getString(R.string.Select_Student_Who_Going_From_Bus));
        } else if (vehtypee.equalsIgnoreCase("Taxi"))
        {
            txtstd.setText(resources.getString(R.string.Select_Student_Who_Going_From_Taxi));
        }
        else
        if(vehtypee.equalsIgnoreCase("Individual"))
        {
            txtstd.setText(resources.getString(R.string.Select_Student_Who_Going_Individual));
        }

        txtallstd.setText(resources.getString(R.string.Select_All_Student));
        arrayStudentList = new ArrayList<>();
        arrayStudentrollnum = new ArrayList<>();
        final Calendar myCalendar = Calendar.getInstance();
        String myDateFormat = "dd/MM/yy"; //In which you need put here
        String myTimeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat);
        SimpleDateFormat sdff = new SimpleDateFormat(myTimeFormat);
        datee.setText(sdf.format(myCalendar.getTime()));
        timee.setText(sdff.format(myCalendar.getTime()));
        checkboxallstd = findViewById(R.id.checkboxallstd);

        recycler_view=(RecyclerView) findViewById(R.id.recyclerstudentlist);
        recycler_view.hasFixedSize();
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager( LeavefectchActivity.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recycler_view.setLayoutManager(layoutManager);

        fetchstudent();

        checkboxallstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxallstd.isChecked())
                {
                    adapter.checkall();
                }
                else
                {
                    adapter.uncheckall();
                }

            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.count(datee.getText().toString(),timee.getText().toString(),vehtypee);
                checkboxallstd.setChecked(false);
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkboxallstd.setChecked(false);
                adapter.uncheckall();
            }
        });

    }

    private void fetchstudent()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Log.e("Mytag","data=="+dataSnapshot.getValue());
                if(dataSnapshot !=null)
                {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        Log.e("Mytag","data1=="+dataSnapshot1.getValue());
                        if(dataSnapshot1 !=null)
                        {
                            if(dataSnapshot1.getKey().equals(userid)) {
                                Log.e("Mytag", "prodata1key==" + dataSnapshot1.getKey());
                                Log.e("Mytag", "prodata1userid==" + userid);
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    Log.e("Mytag", "data2==" + dataSnapshot2.getValue());
                                    Studentinformation studentinformation = new Studentinformation();
                                    studentinformation = dataSnapshot2.getValue(Studentinformation.class);
                                    if (studentinformation.getId() != null) {
                                        if(studentinformation.getVehicletype().equalsIgnoreCase(vehtypee))
                                        {
                                            arrayStudentList.add(studentinformation.getName());
                                            arrayStudentrollnum.add(studentinformation.getId());
                                        }
                                    }
                                    adapter = new CheckoutearlyAdapter(arrayStudentList,arrayStudentrollnum,LeavefectchActivity.this);
                                    recycler_view.setAdapter(adapter);
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
}
