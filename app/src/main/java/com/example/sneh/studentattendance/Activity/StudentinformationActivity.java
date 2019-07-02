package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Adapter.StudentAdapter;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Studentinformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class StudentinformationActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private StudentAdapter adapter;
    private DatabaseReference databaseReference;
    ArrayList<Studentinformation> arrayStudentList;
    StudentAttendance studentAttendance;
    String userid;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentinformation);
        arrayStudentList = new ArrayList<>();
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        if(studentAttendance.getLang(StudentinformationActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(StudentinformationActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(StudentinformationActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(StudentinformationActivity.this, "el");
            resources = context.getResources();
        }

        recycler_view=(RecyclerView) findViewById(R.id. recycler_view);
        recycler_view.hasFixedSize();
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager( StudentinformationActivity.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recycler_view.setLayoutManager(layoutManager);

        loaddata();

        }

    private void loaddata()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("Student");
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
                                        arrayStudentList.add(studentinformation);
                                    }
                                    adapter = new StudentAdapter(arrayStudentList, StudentinformationActivity.this);
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
