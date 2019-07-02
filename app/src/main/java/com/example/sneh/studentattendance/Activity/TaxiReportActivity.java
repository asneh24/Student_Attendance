package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneh.studentattendance.R;
import com.squareup.picasso.Picasso;

public class TaxiReportActivity extends AppCompatActivity {
    ImageView imageview;
    TextView textviewid,textviewstudentname;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_report);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(TaxiReportActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(TaxiReportActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(TaxiReportActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(TaxiReportActivity.this, "el");
            resources = context.getResources();
        }
        String key=getIntent().getStringExtra("key");
        String image=getIntent().getStringExtra("image");
        String name=getIntent().getStringExtra("name");
        imageview=(ImageView)findViewById(R.id.imageview);
        textviewid=(TextView)findViewById(R.id.textviewid);

        textviewstudentname=(TextView)findViewById(R.id.textviewstudentname);
        Picasso.get().load(image).into(imageview);
        textviewstudentname.setText(resources.getString(R.string.StudentName));
        textviewid.setText(resources.getString(R.string.Studentid));
        textviewid.setText(key);
        textviewstudentname.setText(name);
    }
}
