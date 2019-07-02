package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.sneh.studentattendance.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edittextemail,edittextpassword;
    private Button Loginbutton,Registerbutton,forgotpasswordbutton;
    private FirebaseAuth mAuth;

    String email,password1;

    ProgressBar progressBar;
    StudentAttendance studentAttendance;
     Context context;
     Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        studentAttendance=(StudentAttendance)getApplication();
        Log.e("Mytag","langg = "+studentAttendance.getLang(LoginActivity.this));
        if(studentAttendance.getLang(LoginActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(LoginActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(LoginActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(LoginActivity.this, "el");
            resources = context.getResources();
        }
        edittextpassword=(EditText)findViewById(R.id.edittextpassword);
        edittextpassword.setHint(resources.getString(R.string.password));
        edittextpassword.setTransformationMethod(new PasswordTransformationMethod());
        edittextemail = (EditText)findViewById(R.id.edittextemail);
        edittextemail.setHint(resources.getString(R.string.Email));
        forgotpasswordbutton= (Button) findViewById(R.id.forgotpasswordbutton);
        forgotpasswordbutton.setText(resources.getString(R.string.ForgotPassword));
        Loginbutton=(Button) findViewById(R.id.Loginbutton);
        Loginbutton.setText(resources.getString(R.string.Login));
        Registerbutton=(Button) findViewById(R.id. buttonLinkRegister);
        Registerbutton.setText(resources.getString(R.string.btn_link_to_register));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        init();


    }

    private void init()
    {


        Loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (TextUtils.isEmpty(edittextemail.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(edittextpassword.getText().toString()))
                {
                   Toast.makeText(LoginActivity.this,"Please Enter Valid Password",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(edittextemail.getText().toString(),edittextpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email",edittextemail.getText().toString());
                                editor.commit();

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login Failed. Please Enter Correct Credentials", Toast.LENGTH_SHORT).show();
                        }
                    });

                }




            }
        });
        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        forgotpasswordbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent=new Intent(LoginActivity.this,ForgotpasswordActivity.class);
                startActivity(intent);

            }
        });
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
            Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();

        }

    }

}
