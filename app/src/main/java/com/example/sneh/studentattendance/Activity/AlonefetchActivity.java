package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.AloneAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlonefetchActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    AloneAdapter adapter;
    List<Check> list=new ArrayList<Check>();
    List<String>mData=new ArrayList<>();
    DatabaseReference databaseReference;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alonefetch);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(AlonefetchActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(AlonefetchActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(AlonefetchActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(AlonefetchActivity.this, "el");
            resources = context.getResources();
        }
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        databaseReference= FirebaseDatabase.getInstance().getReference("Checkin");
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setHasFixedSize(true);
        Loadata();
    }

    private void Loadata() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                if(dataSnapshot.hasChild("vehiclename"))

                {
                    Log.e("child","Taxi"+dataSnapshot.getChildren());

                    String value=dataSnapshot.child("vehiclename").getValue().toString();
                    Log.e("tax","All"+value);
                    final String valu2="Alone";
                    if(valu2.equals(value))
                    {
                        Check c=dataSnapshot.getValue(Check.class);

                        Log.e("Alone","ttittt "+value);
                        list.add(c);


                    }

                }


                if(dataSnapshot.getValue().equals("Alone"))
                {
                    Log.e("h2h","hhhh"+dataSnapshot.getValue());
                    Check c=dataSnapshot.getValue(Check.class);
                    list.add(c);
                }




                adapter = new  AloneAdapter (AlonefetchActivity.this, list);
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
