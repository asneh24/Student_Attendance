package com.example.sneh.studentattendance;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sneh.studentattendance.Activity.LocaleManager;
import com.example.sneh.studentattendance.Activity.StudentAttendance;
import com.example.sneh.studentattendance.Adapter.FetchAbsentAdapter;
import com.example.sneh.studentattendance.Adapter.FetchAdapter;
import com.example.sneh.studentattendance.model.CheckIn;
import com.example.sneh.studentattendance.model.Studentinformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FetchData extends AppCompatActivity {
    private RecyclerView recycler_view;
    private FetchAdapter adapter;
    private FetchAbsentAdapter absentAdapter;
    private DatabaseReference databaseReference;
    ArrayList<Studentinformation> arrayStudentList;
    StudentAttendance studentAttendance;
    String userid;
    Context context;
    Resources resources;
    String std,dateto,datefrom;
    Calendar myCalendar;
    Calendar mm;
    ArrayList<CheckIn> arraybus;
    ArrayList<CheckIn> arraytaxi;
    ArrayList<CheckIn> arrayindividual;
    ArrayList<CheckIn> arraypresent;
    ArrayList<CheckIn> arrayearly;
    ArrayList<Studentinformation> arrayallstd;
    ArrayList<String> arrayallstdname;
    ArrayList<String> arraypresentstdname;
    ArrayList<String> arrayAbsentStd;
    ArrayList<Studentinformation> arrayfinalabsent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        std = getIntent().getStringExtra("std");
        Log.e("MyTag","StudentDetails = "+std);
        dateto = getIntent().getStringExtra("dateeto");
        datefrom = getIntent().getStringExtra("dateefrom");

        arrayStudentList = new ArrayList<>();
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        if(studentAttendance.getLang(FetchData.this)==0)
        {
            context = LocaleManager.setNewLocale(FetchData.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(FetchData.this)==1)
        {
            context = LocaleManager.setNewLocale(FetchData.this, "el");
            resources = context.getResources();
        }
        myCalendar = Calendar.getInstance();
        mm = Calendar.getInstance();
        recycler_view=(RecyclerView) findViewById(R.id.recyclerViewFetchData);
        recycler_view.hasFixedSize();
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager( FetchData.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recycler_view.setLayoutManager(layoutManager);

        arraybus = new ArrayList<>();
        arraytaxi = new ArrayList<>();
        arrayindividual = new ArrayList<>();
        arraypresent = new ArrayList<>();
        arrayearly = new ArrayList<>();
        arrayallstd = new ArrayList<>();
        arrayallstdname = new ArrayList<>();
        arraypresentstdname = new ArrayList<>();

        if(std.equalsIgnoreCase("Bus"))
        {
            databaseReference= FirebaseDatabase.getInstance().getReference("CheckIn");
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
                                        CheckIn checkin = new CheckIn();
                                        checkin = dataSnapshot2.getValue(CheckIn.class);
                                        if (checkin.getKey() != null) {
                                            Log.e("Mytag","datefromappinbus= "+dateto);
                                            Log.e("Mytag","datefromfirebaseinbus= "+checkin.getDatee());
                                            if(checkin.getVehicletypee().equalsIgnoreCase("Bus"))
                                            {
                                                ArrayList<String> datelist = new ArrayList<>();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
                                                try {
                                                    mm.setTime(sdf.parse(datefrom));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                mm.add(Calendar.DATE,1);
                                                String d2 = sdf.format(mm.getTime());
                                                Log.e("MyTag","d2==="+d2);
                                                Date Date1 = null ,Date2 = null ;
                                                try{
                                                    Date1 = sdf.parse(dateto);
                                                    Date2 = sdf.parse(d2);
                                                    Log.e("MyTag","date2=="+Date2);
                                                    Log.e("MyTag","date1=="+Date1);
                                                }catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                Calendar mmcalendar = new GregorianCalendar();
                                                mmcalendar.setTime(Date1);
                                                String dateformat;
                                                while(mmcalendar.getTime().before(Date2))
                                                {
                                                    Date result = mmcalendar.getTime();
                                                    dateformat = sdf.format(result);
                                                    datelist.add(dateformat);
                                                    mmcalendar.add(Calendar.DATE, 1);
                                                }
                                                Log.e("MyTag","dayslist=="+datelist);

                                                //--------------------------------------------------
                                                for(int i = 0;i<datelist.size();i++)
                                                {
                                                    if(datelist.get(i).equals(checkin.getDatee()))
                                                    {
                                                        Log.e("MyTag","datefromlist=="+datelist.get(i));
                                                        Log.e("MyTag","datefromdatabase=="+checkin.getDatee());
                                                        arraybus.add(checkin);
                                                    }
                                                }

                                            }
                                            adapter = new FetchAdapter(arraybus, FetchData.this);
                                            recycler_view.setAdapter(adapter);

                                        }
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

        }else
        if(std.equalsIgnoreCase("Taxi"))
        {
            databaseReference= FirebaseDatabase.getInstance().getReference("CheckIn");
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
                                        CheckIn checkin = new CheckIn();
                                        checkin = dataSnapshot2.getValue(CheckIn.class);
                                        if (checkin.getKey() != null) {
                                            Log.e("Mytag","datefromappintaxi= "+dateto);
                                            Log.e("Mytag","datefromfirebaseintaxi= "+checkin.getDatee());
                                            if(checkin.getVehicletypee().equalsIgnoreCase("Taxi"))
                                            {
                                                ArrayList<String> datelist = new ArrayList<>();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
                                                try {
                                                    mm.setTime(sdf.parse(datefrom));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                mm.add(Calendar.DATE,1);
                                                String d2 = sdf.format(mm.getTime());
                                                Log.e("MyTag","d2==="+d2);
                                                Date Date1 = null ,Date2 = null ;
                                                try{
                                                    Date1 = sdf.parse(dateto);
                                                    Date2 = sdf.parse(d2);
                                                    Log.e("MyTag","date2=="+Date2);
                                                    Log.e("MyTag","date1=="+Date1);
                                                }catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                Calendar mmcalendar = new GregorianCalendar();
                                                mmcalendar.setTime(Date1);
                                                String dateformat;
                                                while(mmcalendar.getTime().before(Date2))
                                                {
                                                    Date result = mmcalendar.getTime();
                                                    dateformat = sdf.format(result);
                                                    datelist.add(dateformat);
                                                    mmcalendar.add(Calendar.DATE, 1);
                                                }
                                                Log.e("MyTag","dayslist=="+datelist);

                                                //--------------------------------------------------
                                                for(int i = 0;i<datelist.size();i++)
                                                {
                                                    if(datelist.get(i).equals(checkin.getDatee()))
                                                    {
                                                        Log.e("MyTag","datefromlist=="+datelist.get(i));
                                                        Log.e("MyTag","datefromdatabase=="+checkin.getDatee());
                                                        arraytaxi.add(checkin);
                                                    }
                                                }





                                            }
                                            adapter = new FetchAdapter(arraytaxi, FetchData.this);
                                            recycler_view.setAdapter(adapter);


                                        }
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

        }else
        if(std.equalsIgnoreCase("Individual"))
        {
            databaseReference= FirebaseDatabase.getInstance().getReference("CheckIn");
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
                                        CheckIn checkin = new CheckIn();
                                        checkin = dataSnapshot2.getValue(CheckIn.class);
                                        if (checkin.getKey() != null) {
                                            Log.e("Mytag","datefromappinindi= "+dateto);
                                            Log.e("Mytag","datefromfirebaseinindi= "+checkin.getDatee());
                                            if(checkin.getVehicletypee().equalsIgnoreCase("Individual"))
                                            {
                                                ArrayList<String> datelist = new ArrayList<>();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
                                                try {
                                                    mm.setTime(sdf.parse(datefrom));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                mm.add(Calendar.DATE,1);
                                                String d2 = sdf.format(mm.getTime());
                                                Log.e("MyTag","d2==="+d2);
                                                Date Date1 = null ,Date2 = null ;
                                                try{
                                                    Date1 = sdf.parse(dateto);
                                                    Date2 = sdf.parse(d2);
                                                    Log.e("MyTag","date2=="+Date2);
                                                    Log.e("MyTag","date1=="+Date1);
                                                }catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                Calendar mmcalendar = new GregorianCalendar();
                                                mmcalendar.setTime(Date1);
                                                String dateformat;
                                                while(mmcalendar.getTime().before(Date2))
                                                {
                                                    Date result = mmcalendar.getTime();
                                                    dateformat = sdf.format(result);
                                                    datelist.add(dateformat);
                                                    mmcalendar.add(Calendar.DATE, 1);
                                                }
                                                Log.e("MyTag","dayslist=="+datelist);

                                                //--------------------------------------------------
                                                for(int i = 0;i<datelist.size();i++)
                                                {
                                                    if(datelist.get(i).equals(checkin.getDatee()))
                                                    {
                                                        Log.e("MyTag","datefromlist=="+datelist.get(i));
                                                        Log.e("MyTag","datefromdatabase=="+checkin.getDatee());
                                                        arrayindividual.add(checkin);
                                                    }
                                                }



                                            }
                                            adapter = new FetchAdapter(arrayindividual, FetchData.this);
                                            recycler_view.setAdapter(adapter);
                                        }
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

        }else
        if(std.equalsIgnoreCase("Present"))
        {
            databaseReference= FirebaseDatabase.getInstance().getReference("CheckIn");
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
                                        CheckIn checkin = new CheckIn();
                                        checkin = dataSnapshot2.getValue(CheckIn.class);
                                        if (checkin.getKey() != null) {
                                            Log.e("Mytag","datefromappinpresent= "+dateto);
                                            Log.e("Mytag","datefromfirebaseinpresent= "+checkin.getDatee());

                                                ArrayList<String> datelist = new ArrayList<>();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
                                            try {
                                                mm.setTime(sdf.parse(datefrom));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            mm.add(Calendar.DATE,1);
                                            String d2 = sdf.format(mm.getTime());
                                            Log.e("MyTag","d2==="+d2);
                                                Date Date1 = null ,Date2 = null ;
                                                try{
                                                    Date1 = sdf.parse(dateto);
                                                    Date2 = sdf.parse(d2);
                                                    Log.e("MyTag","date2=="+Date2);
                                                    Log.e("MyTag","date1=="+Date1);
                                                }catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                Calendar mmcalendar = new GregorianCalendar();
                                                mmcalendar.setTime(Date1);
                                                String dateformat;
                                                while(mmcalendar.getTime().before(Date2))
                                                {
                                                    Date result = mmcalendar.getTime();
                                                    dateformat = sdf.format(result);
                                                    datelist.add(dateformat);
                                                    mmcalendar.add(Calendar.DATE, 1);
                                                }
                                                Log.e("MyTag","dayslist=="+datelist);

                                                //--------------------------------------------------
                                                for(int i = 0;i<datelist.size();i++)
                                                {
                                                    if(datelist.get(i).equals(checkin.getDatee()))
                                                    {
                                                        Log.e("MyTag","datefromlist=="+datelist.get(i));
                                                        Log.e("MyTag","datefromdatabase=="+checkin.getDatee());
                                                        arraypresent.add(checkin);
                                                    }
                                                }



                                            adapter = new FetchAdapter(arraypresent, FetchData.this);
                                            recycler_view.setAdapter(adapter);

                                        }
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

        }else
        if(std.equalsIgnoreCase("Absent"))
        {
            //Fetch All Student.....

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
                                            arrayallstd.add(studentinformation);
                                            arrayallstdname.add(studentinformation.getName());


                                        }
                                    }
                                }
                            }
                        }

                        Collections.sort(arrayallstdname);


                        //Fetch Present student........

                        databaseReference= FirebaseDatabase.getInstance().getReference("CheckIn");
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
                                                    CheckIn checkin = new CheckIn();
                                                    checkin = dataSnapshot2.getValue(CheckIn.class);
                                                    if (checkin.getKey() != null) {
                                                        Log.e("Mytag","datefromappinpresent= "+dateto);
                                                        Log.e("Mytag","datefromfirebaseinpresent= "+checkin.getDatee());
                                                        if(checkin.getDatee().equals(dateto))
                                                        {
                                                            arraypresent.add(checkin);
                                                            arraypresentstdname.add(checkin.getStudentname());
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Collections.sort(arraypresentstdname);
                                    Log.e("MyTag","sortedarrayallstdname = "+arrayallstdname);
                                    Log.e("MyTag","sortedarrayallpresentstdname = "+arraypresentstdname);
                                    Log.e("MyTag","allstd"+arrayallstdname.size());
                                    Log.e("MyTag","presentstd"+arraypresentstdname.size());
                                   // Integer absentt = arrayallstdname.size() - arraypresentstdname.size();
                                    //Log.e("MyTag","Absent_Std "+absentt);
                                    arrayAbsentStd = new ArrayList<>();
                                    boolean isfound;
                                    for(int i=0;i<arrayallstdname.size();i++)
                                    {
                                        isfound = false;
                                        for(int j=0;j<arraypresentstdname.size();j++) {
                                            Log.e("Mytag","arrayallstdname"+arrayallstdname.get(i));
                                            Log.e("Mytag","arraypresentstdname"+arraypresentstdname.get(j));

                                            if (arrayallstdname.get(i).equalsIgnoreCase(arraypresentstdname.get(j)))
                                            {
                                                  isfound = true;
                                            }
                                        }
                                        if(!isfound)
                                        {
                                            arrayAbsentStd.add(arrayallstdname.get(i));
                                        }
                                    }
                                    Log.e("Mytag","arrayAbsentStd"+arrayAbsentStd);
                                    Log.e("MyTag","sortedarrayallstdnamejhgjgh = "+arrayallstdname);
                                    arrayfinalabsent = new ArrayList<>();
                                    for(int k=0;k<arrayAbsentStd.size();k++)
                                    {
                                        for(int l=0;l<arrayallstd.size();l++)
                                        {
                                            if(arrayAbsentStd.get(k).equalsIgnoreCase(arrayallstd.get(l).getName()))
                                            {
                                                arrayfinalabsent.add(arrayallstd.get(l));
                                                Log.e("Mytag","arrayfinalabsent = "+arrayfinalabsent.get(k).getName());
                                                Log.e("Mytag","arrayfinalabsent = "+arrayfinalabsent.get(k).getId());
                                            }
                                        }
                                    }

                                    absentAdapter = new FetchAbsentAdapter(arrayfinalabsent, FetchData.this);
                                    recycler_view.setAdapter(absentAdapter);

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {

                            }
                        });

                        //end of present student......

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });

            //End of Fetch All Student.......



        }else
        if(std.equalsIgnoreCase("Early"))
        {
            databaseReference= FirebaseDatabase.getInstance().getReference("EarlyLeave");
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
                                        CheckIn checkin = new CheckIn();
                                        checkin = dataSnapshot2.getValue(CheckIn.class);
                                        if (checkin.getKey() != null) {
                                            Log.e("Mytag","datefromappinearly= "+dateto);
                                            Log.e("Mytag","datefromfirebaseinearly= "+checkin.getDatee());


                                                ArrayList<String> datelist = new ArrayList<>();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
                                                try {
                                                    mm.setTime(sdf.parse(datefrom));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                mm.add(Calendar.DATE,1);
                                                String d2 = sdf.format(mm.getTime());
                                                Log.e("MyTag","d2==="+d2);
                                                Date Date1 = null ,Date2 = null ;
                                                try{
                                                    Date1 = sdf.parse(dateto);
                                                    Date2 = sdf.parse(d2);
                                                    Log.e("MyTag","date2=="+Date2);
                                                    Log.e("MyTag","date1=="+Date1);
                                                }catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                Calendar mmcalendar = new GregorianCalendar();
                                                mmcalendar.setTime(Date1);
                                                String dateformat;
                                                while(mmcalendar.getTime().before(Date2))
                                                {
                                                    Date result = mmcalendar.getTime();
                                                    dateformat = sdf.format(result);
                                                    datelist.add(dateformat);
                                                    mmcalendar.add(Calendar.DATE, 1);
                                                }
                                                Log.e("MyTag","dayslist=="+datelist);

                                                //--------------------------------------------------
                                                for(int i = 0;i<datelist.size();i++)
                                                {
                                                    if(datelist.get(i).equals(checkin.getDatee()))
                                                    {
                                                        Log.e("MyTag","datefromlist=="+datelist.get(i));
                                                        Log.e("MyTag","datefromdatabase=="+checkin.getDatee());
                                                        arrayearly.add(checkin);
                                                    }
                                                }

                                            adapter = new FetchAdapter(arrayearly, FetchData.this);
                                            recycler_view.setAdapter(adapter);

                                        }
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
}
