package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.LeaveearlyReportAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LeaveEarlyListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    LeaveearlyReportAdapter adapter;
    List<Check> list=new ArrayList<Check>();
    List<String>mData=new ArrayList<>();
    DatabaseReference databaseReference;
    Context context;
    StudentAttendance studentAttendance;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaveearly_list);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(LeaveEarlyListActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(LeaveEarlyListActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(LeaveEarlyListActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(LeaveEarlyListActivity.this, "el");
            resources = context.getResources();
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Leave");
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

                    Check c=dataSnapshot.getValue(Check.class);
                    list.add(c);
                    Log.e("Leave1","leave"+dataSnapshot.hasChildren());

                }
                adapter = new LeaveearlyReportAdapter(LeaveEarlyListActivity.this, list);
                recycler_view.setAdapter(adapter);
                Log.e("Leave","leave"+dataSnapshot.getChildrenCount());













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

    }}
