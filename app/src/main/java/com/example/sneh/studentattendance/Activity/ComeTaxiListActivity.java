package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.TaxiReportAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ComeTaxiListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    TaxiReportAdapter adapter;
    List<Check> list=new ArrayList<Check>();
    DatabaseReference databaseReference;
    Context context;
    StudentAttendance studentAttendance;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_come_taxi_list);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(ComeTaxiListActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(ComeTaxiListActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(ComeTaxiListActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(ComeTaxiListActivity.this, "el");
            resources = context.getResources();
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Checkin");
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setHasFixedSize(true);
        Loadata();


    }

    private void Loadata()
    {


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.hasChild("vehiclename"))
                {
                    Log.e("child","Taxi"+dataSnapshot.getChildren());

                    String value=dataSnapshot.child("vehiclename").getValue().toString();
                    Log.e("tax","taxi"+value);
                    String Taxi="Taxi";
                    if(Taxi.equals(value))
                    {
                        Check c=dataSnapshot.getValue(Check.class);

                        Log.e("tt","ttittt "+value);
                        list.add(c);
                        Log.e("ddd","ff"+list.size());


                    }


                }

                Log.e("TAG","key"+list);
                adapter = new TaxiReportAdapter(ComeTaxiListActivity.this, list);
                recycler_view.setAdapter(adapter);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
