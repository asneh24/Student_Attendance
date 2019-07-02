package com.example.sneh.studentattendance.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneh.studentattendance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotpasswordActivity extends AppCompatActivity {
    EditText edittextemail;
    Button resetbutton ,buttonBack;
    private FirebaseAuth mAuth;
    TextView textviewforgotpassword,textviewsendpassword;
    ProgressBar progressBar;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mAuth = FirebaseAuth.getInstance();
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(ForgotpasswordActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(ForgotpasswordActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(ForgotpasswordActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(ForgotpasswordActivity.this, "el");
            resources = context.getResources();
        }
        textviewforgotpassword=(TextView) findViewById(R.id.textviewforgotpassword);
        textviewforgotpassword.setText(resources.getString(R.string.lbl_forgot_password));
        textviewsendpassword=(TextView) findViewById(R.id.textviewsendpassword);
        textviewsendpassword.setText(resources.getString(R.string.forgot_password_msg));
        edittextemail=(EditText)findViewById(R.id.edittextemail);
        edittextemail.setHint(resources.getString(R.string.Email));
        resetbutton= (Button) findViewById(R.id.resetbutton);
        resetbutton.setText(resources.getString(R.string.Reset));
        buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setText(resources.getString(R.string.btn_back));

        progressBar = (ProgressBar) findViewById(R.id.progressBarForgotPassword);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(edittextemail.getText().toString()))
            {
                edittextemail.setError("Enter Gmail");

            }
            else
            {

                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(edittextemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotpasswordActivity.this, " Paasword sent at gmail", Toast.LENGTH_SHORT).show();
                                    Log.d("Password", "Email sent.");
                                    Intent intent = new Intent(ForgotpasswordActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });

            }




            }
        });
    }
}
