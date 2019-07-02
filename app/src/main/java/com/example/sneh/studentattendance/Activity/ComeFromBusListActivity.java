package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.BusReportAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ComeFromBusListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    BusReportAdapter adapter;
    List<Check> list=new ArrayList<Check>();
    DatabaseReference databaseReference;
    Context context;
    StudentAttendance studentAttendance;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comefrom_bus_list);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(ComeFromBusListActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(ComeFromBusListActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(ComeFromBusListActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(ComeFromBusListActivity.this, "el");
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
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {

                Log.e("bus1","bus2"+dataSnapshot.getChildren());
                 String vehiclename1="Bus";
                String vehiclename= (String) dataSnapshot.child("vehiclename").getValue();
                if (vehiclename1.equals(vehiclename))
                {


                    Log.e("My6tag","busmmmmm"+dataSnapshot.getValue());
                    Check c=dataSnapshot.getValue(Check.class);

                    list.add(c);


                }else if(!vehiclename.equals(vehiclename))
                {
                  //  Toast.makeText(ComeFromBusListActivity.this, "", Toast.LENGTH_SHORT).show();

                }



                    Log.e("TAG","key"+list);
                    adapter = new BusReportAdapter (ComeFromBusListActivity.this, list);
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
