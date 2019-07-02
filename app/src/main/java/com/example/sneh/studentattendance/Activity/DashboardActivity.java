package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import android.content.res.Resources;

import com.example.sneh.studentattendance.Adapter.MenuAdapter;
import com.example.sneh.studentattendance.R;

import com.example.sneh.studentattendance.model.RegisterModel;
import com.example.sneh.studentattendance.model.MenuModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ViewFlipper viewfipper;

    List<MenuModel> list;
    MenuAdapter adapter;

    private ViewFlipper viewFlipper;
    Context context;
    StudentAttendance studentAttendance;
    Resources resources;
    SharedPreferences sharedPreferences;
    String emaill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        studentAttendance=(StudentAttendance)getApplication();
        //if(studentAttendance.getLang()!=null) {
            if (studentAttendance.getLang(DashboardActivity.this) == 0) {
                context = LocaleManager.setNewLocale(DashboardActivity.this, "en");
                resources = context.getResources();
            } else if (studentAttendance.getLang(DashboardActivity.this) == 1) {
                context = LocaleManager.setNewLocale(DashboardActivity.this, "el");
               resources = context.getResources();
            }
            sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
            emaill = sharedPreferences.getString("email","");
            Log.e("Mytag","emailll="+emaill);

            getuserid();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            int images[] = {R.mipmap.imagefilipperone,R.mipmap.imageflippertwo,R.mipmap.imageflipperthree,R.mipmap.imageflipperfour,R.mipmap.imageflippertwo};

            viewFlipper = findViewById(R.id.ViewFliperDashboard);

            ImageView imageViewStudentEntry = findViewById(R.id.imageViewStudentEntry);
            ImageView imageViewBusEntry = findViewById(R.id.imageViewBusEntry);
            ImageView imageViewTaxiEntry = findViewById(R.id.imageViewTaxiEntry);
            ImageView imageViewAllStudent = findViewById(R.id.imageViewAllStudent);
            ImageView imageViewAllBus = findViewById(R.id.imageViewAllBus);
            ImageView imageViewAllTaxi = findViewById(R.id.imageViewAllTaxi);
            ImageView imageViewStudentCheckIn = findViewById(R.id.imageViewStudentCheckIn);
            ImageView imageViewStudentCheckOut = findViewById(R.id.imageViewStudentCheckOut);
            ImageView imageViewLeaveEarly = findViewById(R.id.imageViewLeaveEarly);
            ImageView imageViewReport = findViewById(R.id.imageViewReport);

            TextView textViewStudentEntry = findViewById(R.id.textViewStudentEntry);
            textViewStudentEntry.setText(resources.getString(R.string.student_entry));
            TextView textViewBusEntry = findViewById(R.id.textViewBusEntry);
            textViewBusEntry.setText(resources.getString(R.string.new_bus_entry));
            TextView textViewTaxiEntry = findViewById(R.id.textViewTaxiEntry);
            textViewTaxiEntry.setText(resources.getString(R.string.new_taxi_entry));
            TextView textViewAllStudent = findViewById(R.id.textViewAllStudent);
            textViewAllStudent.setText(resources.getString(R.string.all_students));
            TextView textViewAllBus = findViewById(R.id.textViewAllBus);
            textViewAllBus.setText(resources.getString(R.string.all_bus));
            TextView textViewAllTaxi = findViewById(R.id.textViewAllTaxi);
            textViewAllTaxi.setText(resources.getString(R.string.all_taxi));
            TextView textViewStudentCheckIn = findViewById(R.id.textViewStudentCheckIn);
            textViewStudentCheckIn.setText(resources.getString(R.string.student_checkin));
            TextView textViewStudentCheckOut = findViewById(R.id.textViewStudentCheckOut);
            textViewStudentCheckOut.setText(resources.getString(R.string.student_checkout));
            TextView textViewLeaveEarly = findViewById(R.id.textViewLeaveEarly);
            textViewLeaveEarly.setText(resources.getString(R.string.student_leave_early));
            TextView textViewReport = findViewById(R.id.textViewReport);



        textViewReport.setText(resources.getString(R.string.report));


        for (int image : images){

            flipperImages(image);
        }



        imageViewStudentEntry.setOnClickListener(this);
        imageViewBusEntry.setOnClickListener(this);
        imageViewTaxiEntry.setOnClickListener(this);
        imageViewAllStudent.setOnClickListener(this);
        imageViewAllBus.setOnClickListener(this);
        imageViewAllTaxi.setOnClickListener(this);
        imageViewStudentCheckIn.setOnClickListener(this);
        imageViewStudentCheckOut.setOnClickListener(this);
        imageViewLeaveEarly.setOnClickListener(this);
        imageViewReport.setOnClickListener(this);


        textViewStudentEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,StudententryActivity.class);
                startActivity(intent);



            }
        });

        textViewBusEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,VehicleActivity.class);
                startActivity(intent);


            }
        });

        textViewTaxiEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,TaxientryActivity.class);
                startActivity(intent);


            }
        });

        textViewAllStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,StudentinformationActivity.class);
                startActivity(intent);


            }
        });


        textViewAllBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,VehicleinformationActivity.class);
                startActivity(intent);


            }
        });

        textViewAllTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,TaxiActivity.class);
                startActivity(intent);


            }
        });

        textViewStudentCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,CheckinvehicleActivity.class);
                startActivity(intent);


            }
        });

        textViewStudentCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,CheckOutVehicleActivity.class);
                startActivity(intent);


            }
        });


        textViewLeaveEarly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,CheckEarlyVehicleActivity.class);
                startActivity(intent);


            }
        });

        textViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,ReportActivity.class);
                startActivity(intent);


            }
        });


        list = new ArrayList<>();
        adapter = new MenuAdapter(DashboardActivity.this, list);

        //prepare();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
       // }
    }

    private void flipperImages(int image) {

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(DashboardActivity.this,LoginActivity.class);
            startActivity(intent);
              finish();
             return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(DashboardActivity.this,StudententryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent=new Intent(DashboardActivity.this,VehicleActivity.class);
            startActivity(intent);



        } else if (id == R.id.nav_slideshow) {

            Intent intent=new Intent(DashboardActivity.this,TaxientryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent=new Intent(DashboardActivity.this,StudentinformationActivity.class);
            startActivity(intent);

        }

     else if (id == R.id.nav_manage1) {

        Intent intent=new Intent(DashboardActivity.this,VehicleinformationActivity.class);
        startActivity(intent);

    }

        else if (id == R.id.nav_manage2) {

            Intent intent=new Intent(DashboardActivity.this,TaxiActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_manage3) {

            Intent intent=new Intent(DashboardActivity.this,CheckinvehicleActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_manage4) {

            Intent intent=new Intent(DashboardActivity.this,CheckOutVehicleActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_manage5) {

            Intent intent=new Intent(DashboardActivity.this,CheckEarlyVehicleActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_manage6) {

            Intent intent=new Intent(DashboardActivity.this,ReportActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageViewStudentEntry:

                Intent intent = new Intent(DashboardActivity.this,StudententryActivity.class);
                startActivity(intent);

                break;


            case R.id.imageViewBusEntry:
                Intent product_intent = new Intent(DashboardActivity.this,VehicleActivity.class);
                startActivity(product_intent);

                break;

            case R.id.imageViewTaxiEntry:
                Intent add_product_intent = new Intent(DashboardActivity.this,TaxientryActivity.class);
                startActivity(add_product_intent);

                break;

            case R.id.imageViewAllStudent:
                Intent all_student = new Intent(DashboardActivity.this,StudentinformationActivity.class);
                startActivity(all_student);

                break;



            case R.id.imageViewAllBus:

                Intent allBus_intent = new Intent(DashboardActivity.this,VehicleinformationActivity.class);
                startActivity(allBus_intent);

                break;


            case R.id.imageViewAllTaxi:
                Intent allTaxi_intent = new Intent(DashboardActivity.this,TaxiActivity.class);
                startActivity(allTaxi_intent);

                break;

            case R.id.imageViewStudentCheckIn:
                Intent chechIn_intent = new Intent(DashboardActivity.this,CheckinvehicleActivity.class);
                startActivity(chechIn_intent);

                break;

            case R.id.imageViewStudentCheckOut:
                Intent student_chechout = new Intent(DashboardActivity.this,CheckOutVehicleActivity.class);
                startActivity(student_chechout);

                break;

            case R.id.imageViewLeaveEarly:
                Intent leave_intent = new Intent(DashboardActivity.this,CheckEarlyVehicleActivity.class);
                startActivity(leave_intent);

                break;

            case R.id.imageViewReport:
                Intent report_intent = new Intent(DashboardActivity.this,ReportActivity.class);
                startActivity(report_intent);

                break;

            default:
                break;
        }

    }

    public void getuserid()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registration");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Mytag", "dataSnapshot====" + dataSnapshot);

                Log.e("Mytag", "Data====" + dataSnapshot.getValue());
                if(dataSnapshot != null)
                {
                    RegisterModel regis = null;
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if(dataSnapshot1 != null)
                        {
                            Log.e("Mytag", "Data1====" + dataSnapshot1.getValue());
//                        SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
//                       String     email = sharedPreferences.getString("email",null);
                            Log.e("Mytag", "Data122====" +emaill);
                            regis =dataSnapshot1.getValue(RegisterModel.class);
                            Log.e("Mytag", "Datadd1====" + regis.getEmail());
                            if(regis.getEmail().equals(emaill))
                            {
                                Log.e("Mytag", "Email from firebase:- " + regis.getEmail());
                                studentAttendance.setUserid(regis.getId());
                                Log.e("Mytag", "userid from application :- " + studentAttendance.getUserid());


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


