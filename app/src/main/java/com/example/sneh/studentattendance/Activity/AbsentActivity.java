package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneh.studentattendance.R;
import com.squareup.picasso.Picasso;

public class AbsentActivity extends AppCompatActivity {
    ImageView imageview;
    TextView textviewid,textviewstudentname;
     Context context;
     StudentAttendance studentAttendance;
     Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(AbsentActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(AbsentActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(AbsentActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(AbsentActivity.this, "el");
            resources = context.getResources();
        }
        String key=getIntent().getStringExtra("key");
        String image=getIntent().getStringExtra("image");
        String name=getIntent().getStringExtra("name");
        imageview=(ImageView)findViewById(R.id.imageview);
        textviewid=(TextView)findViewById(R.id.textviewid);
        textviewstudentname=(TextView)findViewById(R.id.textviewstudentname);
        textviewid.setText(resources.getString(R.string.Studentid));
        textviewstudentname.setText(resources.getString(R.string.StudentName));
        Picasso.get().load(image).into(imageview);
        textviewid.setText(key);
        textviewstudentname.setText(name);



    }
}
