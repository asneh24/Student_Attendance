package com.example.sneh.studentattendance.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.sneh.studentattendance.FetchData;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.CheckIn;
import com.example.sneh.studentattendance.model.PresentStd;
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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    Button todate,fromdate,search;
    TextView bus,taxi,alone,present,absent,early,datetoshow,datefromshow;
    TextView bushead,taxihead,alonehead,presenthead,absenthead,earlyhead;

    private DatabaseReference databaseReference;
    Calendar myCalendar;
    Calendar mm;
    ArrayList<String>stdroll;
    ArrayList<String>checkinstdroll;
    ArrayList<String>checkinbus;
    ArrayList<String>checkintaxi;
    ArrayList<String>checkinindividual;
    ArrayList<String>earlyout;

    String classnamee;
    Context context;
    Resources resources;

    StudentAttendance studentAttendance;
    String userid;
    int flg=0;

    Date dateto,datefrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        stdroll = new ArrayList<>();
        checkinstdroll = new ArrayList<>();
        checkinbus = new ArrayList<>();
        checkintaxi = new ArrayList<>();
        checkinindividual = new ArrayList<>();
        earlyout = new ArrayList<>();

        if(studentAttendance.getLang(ReportActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(ReportActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(ReportActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(ReportActivity.this, "el");
            resources = context.getResources();
        }
        bus = findViewById(R.id.textviewbus);
        taxi = findViewById(R.id.textviewtaxi);
        alone = findViewById(R.id.textviewalone);
        present = findViewById(R.id.textviewpresent);
        absent = findViewById(R.id.textviewabsent);
        early = findViewById(R.id.textviewearlyleave);

        fromdate = findViewById(R.id.buttonfromdate);
        todate = findViewById(R.id.buttontodate);
        search = findViewById(R.id.search);
        datetoshow = findViewById(R.id.textviewtodate);
        datefromshow = findViewById(R.id.textviewfromdate);

        bushead = findViewById(R.id.textviewstudentcomefrombus);
        taxihead = findViewById(R.id.textviewstudentcomeviataxi);
        alonehead = findViewById(R.id.textviewcomeviaAlone);
        presenthead = findViewById(R.id.textviewnoofstudentpresent);
        absenthead = findViewById(R.id.textviewnoofstudentabsent);
        earlyhead = findViewById(R.id.textViewLeaveEarly);

        bushead.setText(resources.getString(R.string.No_ofStudent_come_via_Bus));
        taxihead.setText(resources.getString(R.string.No_of_Student_come_via_Taxi));
        alonehead.setText(resources.getString(R.string.No_of_student_come_via_Alone));
        presenthead.setText(resources.getString(R.string.No_of_student_present));
        absenthead.setText(resources.getString(R.string.Total_No_of_student_Absent));
        earlyhead.setText(resources.getString(R.string.No_of_student_Leave_early));

        myCalendar = Calendar.getInstance();
        mm = Calendar.getInstance();
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        datetoshow.setText(sdf.format(myCalendar.getTime()));
        datefromshow.setText(sdf.format(myCalendar.getTime()));

        bus.setText("0");
        taxi.setText("0");
        alone.setText("0");
        present.setText("0");
        absent.setText("0");
        early.setText("0");

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flg = 1;
                // TODO Auto-generated method stub
                new DatePickerDialog(ReportActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                bus.setText("0");
                taxi.setText("0");
                alone.setText("0");
                present.setText("0");
                absent.setText("0");
                early.setText("0");
//                stdroll.clear();
//                checkinbus.clear();
//                checkintaxi.clear();
//                checkinindividual.clear();
//                checkinstdroll.clear();
//                earlyout.clear();

            }
        });

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flg = 2;
                new DatePickerDialog(ReportActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                bus.setText("0");
                taxi.setText("0");
                alone.setText("0");
                present.setText("0");
                absent.setText("0");
                early.setText("0");

            }
        });



        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ReportActivity.this, FetchData.class);
                ii.putExtra("std","Bus");
                ii.putExtra("dateeto",datetoshow.getText().toString());
                ii.putExtra("dateefrom",datefromshow.getText().toString());
                startActivity(ii);


            }
        });
        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ReportActivity.this, FetchData.class);
                ii.putExtra("std","Taxi");
                ii.putExtra("dateeto",datetoshow.getText().toString());
                ii.putExtra("dateefrom",datefromshow.getText().toString());
                startActivity(ii);


            }
        });
        alone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ReportActivity.this, FetchData.class);
                ii.putExtra("std","Individual");
                ii.putExtra("dateeto",datetoshow.getText().toString());
                ii.putExtra("dateefrom",datefromshow.getText().toString());
                startActivity(ii);


            }
        });
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ReportActivity.this, FetchData.class);
                ii.putExtra("std","Present");
                ii.putExtra("dateeto",datetoshow.getText().toString());
                ii.putExtra("dateefrom",datefromshow.getText().toString());
                startActivity(ii);


            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ReportActivity.this, FetchData.class);
                ii.putExtra("std","Absent");
                ii.putExtra("dateeto",datetoshow.getText().toString());
                ii.putExtra("dateefrom",datefromshow.getText().toString());
                startActivity(ii);


            }
        });
        early.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ReportActivity.this, FetchData.class);
                ii.putExtra("std","Early");
                ii.putExtra("dateeto",datetoshow.getText().toString());
                ii.putExtra("dateefrom",datefromshow.getText().toString());
                startActivity(ii);


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stdroll.size() != 0)
                {
                    stdroll.clear();
                    checkinstdroll.clear();
                    checkinbus.clear();
                    checkintaxi.clear();
                    checkinindividual.clear();
                    earlyout.clear();

                }
                Log.e("MyTag","Total Student=="+stdroll.size());
                Log.e("MyTag","Present"+checkinstdroll.size());
                Log.e("MyTag","Come_with_bus"+checkinbus.size());
                Log.e("MyTag","Come_with_taxi"+checkintaxi.size());
                Log.e("MyTag","Come_with_indiviual"+checkinindividual.size());

                fetchstudent();
                presentstudent();
                comeviabus();
                comeviataxi();
                comeviaindividual();
                earlygo();



            }
        });





    }

    public void fetchstudent()
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
                                        stdroll.add(studentinformation.getId());
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

    public void comeviabus()
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
                                        Log.e("Mytag","datefromappinbus= "+datetoshow.getText().toString());
                                        Log.e("Mytag","datefromfirebaseinbus= "+checkin.getDatee());
                                        if(checkin.getVehicletypee().equalsIgnoreCase("Bus"))
                                        {

                                            ArrayList<String> datelist = new ArrayList<>();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",Locale.ENGLISH);
                                            try {
                                                mm.setTime(sdf.parse(datefromshow.getText().toString()));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            mm.add(Calendar.DATE,1);
                                            String d2 = sdf.format(mm.getTime());
                                            Log.e("MyTag","d2==="+d2);
                                            Date Date1 = null ,Date2 = null ;
                                            try{
                                                Date1 = sdf.parse(datetoshow.getText().toString());
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
                                                    checkinbus.add(checkin.getStudentrollnumber());


                                                }
                                            }
                                            Log.e("Mytag","comeinbussize= "+checkinbus.size());
                                            bus.setText(Integer.toString(checkinbus.size()));

                                        }
                                        else
                                        {
                                            //bus.setText("0");
                                        }



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

    public void comeviataxi()
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
                                        Log.e("Mytag","datefromappintaxi= "+datetoshow.getText().toString());
                                        Log.e("Mytag","datefromfirebaseintaxi= "+checkin.getDatee());
                                        if(checkin.getVehicletypee().equalsIgnoreCase("Taxi"))
                                        {
                                            ArrayList<String> datelist = new ArrayList<>();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",Locale.ENGLISH);
                                            try {
                                                mm.setTime(sdf.parse(datefromshow.getText().toString()));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            mm.add(Calendar.DATE,1);
                                            String d2 = sdf.format(mm.getTime());
                                            Log.e("MyTag","d2==="+d2);
                                            Date Date1 = null ,Date2 = null ;
                                            try{
                                                Date1 = sdf.parse(datetoshow.getText().toString());
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
                                                    checkintaxi.add(checkin.getStudentrollnumber());


                                                }
                                            }
                                            Log.e("Mytag","comeinbussize= "+checkinbus.size());
                                            taxi.setText(Integer.toString(checkintaxi.size()));





                                        }
                                        else
                                        {
                                            //taxi.setText("0");
                                        }


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

    public void comeviaindividual()
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
                                        Log.e("Mytag","datefromappinindi= "+datetoshow.getText().toString());
                                        Log.e("Mytag","datefromfirebaseinindi= "+checkin.getDatee());
                                        if(checkin.getVehicletypee().equalsIgnoreCase("Individual"))
                                        {
                                            ArrayList<String> datelist = new ArrayList<>();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",Locale.ENGLISH);
                                            try {
                                                mm.setTime(sdf.parse(datefromshow.getText().toString()));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            mm.add(Calendar.DATE,1);
                                            String d2 = sdf.format(mm.getTime());
                                            Log.e("MyTag","d2==="+d2);
                                            Date Date1 = null ,Date2 = null ;
                                            try{
                                                Date1 = sdf.parse(datetoshow.getText().toString());
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
                                                    checkinindividual.add(checkin.getStudentrollnumber());


                                                }
                                            }
                                            Log.e("Mytag","comeinbussize= "+checkinbus.size());
                                            alone.setText(Integer.toString(checkinindividual.size()));







                                        }
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

    public void presentstudent()
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
                                    PresentStd pststd = new PresentStd();
                                    pststd = dataSnapshot2.getValue(PresentStd.class);
                                    if (pststd.getKey() != null) {
                                        Log.e("Mytag","datefromappinpresent= "+datetoshow.getText().toString());
                                        Log.e("Mytag","datefromfirebaseinpresent= "+pststd.getDatee());

                                            ArrayList<String> datelist = new ArrayList<>();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",Locale.ENGLISH);
                                            try {
                                                mm.setTime(sdf.parse(datefromshow.getText().toString()));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            mm.add(Calendar.DATE,1);
                                            String d2 = sdf.format(mm.getTime());
                                            Log.e("MyTag","d2==="+d2);
                                            Date Date1 = null ,Date2 = null ;
                                            try{
                                                Date1 = sdf.parse(datetoshow.getText().toString());
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
                                                if(datelist.get(i).equals(pststd.getDatee()))
                                                {
                                                    Log.e("MyTag","datefromlist=="+datelist.get(i));
                                                    Log.e("MyTag","datefromdatabase=="+pststd.getDatee());
                                                    checkinstdroll.add(pststd.getStudentrollnumber());


                                                }
                                            }
                                            Log.e("Mytag","presentstd= "+checkinstdroll.size());
                                            present.setText(Integer.toString(checkinstdroll.size()));

                                    }
                                }

                            }
                        }
                    }
//                    Log.e("MyTag","allstd"+stdroll.size());
//                    Log.e("MyTag","presentstd"+checkinstdroll.size());
//                    Integer absentt = stdroll.size() - checkinstdroll.size();
//                    Log.e("MyTag","Absent_Std"+absentt);
//                    absent.setText(Integer.toString(absentt));
                    absent();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    public void absent()
    {
        Integer absentt = 0;
        Log.e("MyTag","allstd"+stdroll.size());
        Log.e("MyTag","presentstd"+checkinstdroll.size());
        absentt = stdroll.size() - checkinstdroll.size();
        Log.e("MyTag","Absent_Std"+absentt);
        absent.setText(Integer.toString(absentt));
    }


    public void earlygo()
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
                                        Log.e("Mytag","datefromappinearly= "+datetoshow.getText().toString());
                                        Log.e("Mytag","datefromfirebaseinearly= "+checkin.getDatee());


                                            ArrayList<String> datelist = new ArrayList<>();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",Locale.ENGLISH);
                                        try {
                                            mm.setTime(sdf.parse(datefromshow.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        mm.add(Calendar.DATE,1);
                                        String d2 = sdf.format(mm.getTime());
                                        Log.e("MyTag","d2==="+d2);
                                            Date Date1 = null ,Date2 = null ;
                                            try{
                                                Date1 = sdf.parse(datetoshow.getText().toString());
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
                                                    earlyout.add(checkin.getStudentrollnumber());


                                                }
                                            }
                                            Log.e("Mytag","comeinbussize= "+checkinbus.size());
                                        early.setText(Integer.toString(earlyout.size()));


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
    private void updateLabel() {
        if(flg==1)
        {
            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            datetoshow.setText(sdf.format(myCalendar.getTime()));
            try {
                dateto = sdf.parse(datetoshow.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("MyTag","dateto=="+dateto);

        }
        else
        if(flg==2)
        {
            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            datefromshow.setText(sdf.format(myCalendar.getTime()));
            try {
                datefrom = sdf.parse(datefromshow.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
           // Log.e("MyTag","datefrom=="+datefrom);
        }

    }

//    public void daybetween(String datee1,String datee2)
//    {
//        ArrayList<String> datelist = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",Locale.ENGLISH);
//        Date Date1 = null ,Date2 = null ;
//        try{
//            Date1 = sdf.parse(datee1);
//            Date2 = sdf.parse(datee2);
//            Log.e("MyTag","date2=="+Date2);
//            Log.e("MyTag","date1=="+Date1);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        Calendar mmcalendar = new GregorianCalendar();
//        mmcalendar.setTime(Date1);
//        String dateformat;
//        while(mmcalendar.getTime().before(Date2))
//        {
//            Date result = mmcalendar.getTime();
//            dateformat = sdf.format(result);
//            datelist.add(dateformat);
//            mmcalendar.add(Calendar.DATE, 1);
//        }
//        Log.e("MyTag","dayslist=="+datelist);
//    }


}


