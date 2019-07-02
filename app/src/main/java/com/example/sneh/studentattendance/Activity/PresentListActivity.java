package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.PresentstudentAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PresentListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    PresentstudentAdapter adapter;
    List<Check> list=new ArrayList<Check>();
    DatabaseReference databaseReference;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_list);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(PresentListActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(PresentListActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(PresentListActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(PresentListActivity.this, "el");
            resources = context.getResources();
        }
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
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
                    Log.e("child","presnt"+dataSnapshot.getChildren());


                    Check c=dataSnapshot.getValue(Check.class);

                    list.add(c);
                }

                Log.e("TAG","key"+list);
                adapter = new PresentstudentAdapter(PresentListActivity.this, list);
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
