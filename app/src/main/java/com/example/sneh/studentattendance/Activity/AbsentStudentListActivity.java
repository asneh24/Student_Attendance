package com.example.sneh.studentattendance.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.AbsentstudentAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AbsentStudentListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    AbsentstudentAdapter adapter;
    List<Check> list=new ArrayList<Check>();
    String key1,key2;
    DatabaseReference databaseReference,db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_student_list);
        databaseReference= FirebaseDatabase.getInstance().getReference("Checkin");
        db=FirebaseDatabase.getInstance().getReference("Student");
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setHasFixedSize(true);
        Loadata();


    }

    private void Loadata()
    {

       key2 =FirebaseDatabase.getInstance().getReference("Student").getKey();
       Log.e("kee","kk"+key2);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                 key1=dataSnapshot.getKey();
                Log.e("ee","kk"+key1);

                if(key1.equals(key2))
                {
                    Log.e("child","absent"+dataSnapshot.getChildren());


                    Check c=dataSnapshot.getValue(Check.class);

                    list.add(c);
                }


                Log.e("chi","abst"+dataSnapshot.getChildren());

                adapter = new AbsentstudentAdapter(AbsentStudentListActivity.this, list);
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
