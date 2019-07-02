package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.VehicleAdapter;
import com.example.sneh.studentattendance.R;

import com.example.sneh.studentattendance.model.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VehicleinformationActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private VehicleAdapter adapter;
    private DatabaseReference databaseReference;
    ArrayList<Vehicle> arrayBusList;
    StudentAttendance studentAttendance;
    String userid;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicalinformation);

        arrayBusList = new ArrayList<>();
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        if(studentAttendance.getLang(VehicleinformationActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(VehicleinformationActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(VehicleinformationActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(VehicleinformationActivity.this, "el");
            resources = context.getResources();
        }
        recycler_view=(RecyclerView) findViewById(R.id. recycler_view);
        recycler_view.hasFixedSize();
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager( VehicleinformationActivity.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recycler_view.setLayoutManager(layoutManager);
        getdata();
    }

    private void getdata()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("Bus");
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
                                    Vehicle vehicle = new Vehicle();
                                    vehicle = dataSnapshot2.getValue(Vehicle.class);
                                    if (vehicle.getKey() != null) {
                                        arrayBusList.add(vehicle);
                                    }
                                    adapter = new VehicleAdapter(arrayBusList, VehicleinformationActivity.this);
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
