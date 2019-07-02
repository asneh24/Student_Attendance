package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sneh.studentattendance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChangelanguageActivity extends AppCompatActivity {

    String lang;
    TextView textheading;
    Spinner spinnerlanguage;
    ArrayList<String> langg;
    StudentAttendance studentAttendance;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chagelaguage);
        spinnerlanguage=(Spinner)findViewById(R.id.spinnerlanguage);
        langg = new ArrayList<>();
        langg.add(0,"Select Language");
        langg.add(1,"English");
        langg.add(2,"Greek");

        textheading = findViewById(R.id.textviewlang);
        mAuth = FirebaseAuth.getInstance();

//        String []language={"Select One...","English","Greek"};
        studentAttendance = (StudentAttendance) getApplication();
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,langg);
        spinnerlanguage.setAdapter(adapter);

//        if(LocaleManager.getLanguage(ChangelanguageActivity.this,lang).equalsIgnoreCase("en"))
//        {
//            spinnerlanguage.setSelection(adapter.getPosition("English"));
//
//        }
//        else if(LocaleManager.getLanguage(ChangelanguageActivity.this,lang).equalsIgnoreCase("el"))
//        {
//            spinnerlanguage.setSelection(adapter.getPosition("Greek"));
//        }
        spinnerlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;
                switch (i) {
                    case 1:
                        Log.e("MyTag","1 In English");
                        context = LocaleManager.setNewLocale(ChangelanguageActivity.this, "en");
                        resources = context.getResources();
                        textheading.setText(resources.getString(R.string.Login));
                        studentAttendance.setLang(ChangelanguageActivity.this,0);
                        Intent intent=new Intent(ChangelanguageActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Log.e("MyTag","2 In Greek");
                        context = LocaleManager.setNewLocale(ChangelanguageActivity.this, "el");
                        resources = context.getResources();
                        textheading.setText(resources.getString(R.string.Login));
                        studentAttendance.setLang(ChangelanguageActivity.this,1);
                        Intent intent1=new Intent(ChangelanguageActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //textviewlang=(TextView)findViewById(R.id.textviewlang);
        //radiogroup= (RadioGroup) findViewById(R.id.radiogroup);



    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser)
    {
        if(currentUser!= null)
        {
            Intent intent=new Intent(ChangelanguageActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();

        }

    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LocaleManager.OnAttach(newBase,""));
//    }

}
