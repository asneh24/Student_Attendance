package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.sneh.studentattendance.model.RegisterModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.sneh.studentattendance.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText edittextemail,edittextpassword,edittextTeachername,edittextconformedpassword,edittextphone;
    private Button Registerbutton, buttonLinkLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    StudentAttendance studentAttendance;
    Context context;
    Resources resources;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);

        mAuth=FirebaseAuth.getInstance();
        studentAttendance=(StudentAttendance)getApplication();
        if(studentAttendance.getLang(RegisterActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(RegisterActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(RegisterActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(RegisterActivity.this, "el");
            resources = context.getResources();
        }

        edittextemail=(EditText)findViewById(R.id.edittextemail);
        edittextemail.setHint(resources.getString(R.string.Email));
        edittextpassword=(EditText)findViewById(R.id.edittextpassword);
        edittextpassword.setHint(resources.getString(R.string.password));
        edittextTeachername=(EditText)findViewById(R.id.edittextUsername);
        edittextTeachername.setHint(resources.getString(R.string.teacher_name));
        edittextconformedpassword=(EditText)findViewById(R.id.edittextconformedpassword);

        edittextconformedpassword.setHint(resources.getString(R.string.confirm_password));
        edittextphone=(EditText)findViewById(R.id. edittextphone);

        edittextphone.setHint(resources.getString(R.string.phoneno));
        Registerbutton=(Button) findViewById(R.id. Registerbutton);
        Registerbutton.setText(resources.getString(R.string.Register));
        buttonLinkLogin=(Button) findViewById(R.id. buttonLinkLogin);
        buttonLinkLogin.setText(resources.getString(R.string.Login));

        buttonLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);



        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                registeruser();
            }
        });
    }

    private void registeruser()
    {
        if (TextUtils.isEmpty(edittextTeachername.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this,"Please Enter Teacher Name",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(edittextphone.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(edittextemail.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this,"Please Enter email",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(edittextpassword.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();

        }
        else if(edittextpassword.getText().toString().length() < 6)
        {
            Toast.makeText(RegisterActivity.this,"Please Enter atleast 6 character Password",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(edittextconformedpassword.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();

        } else if(edittextconformedpassword.getText().toString().length() < 6)
        {
            Toast.makeText(RegisterActivity.this,"Please Enter atleast 6 character Password",Toast.LENGTH_SHORT).show();
        }
        else if (!edittextpassword.getText().toString().equals(edittextconformedpassword.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this,"Password and Confirm Password does not match.",Toast.LENGTH_SHORT).show();
        }
       else  {

            progressBar.setVisibility(View.VISIBLE);
            databaseReference = FirebaseDatabase.getInstance().getReference("Registration");
            String key=databaseReference.push().getKey();
            RegisterModel reg = new RegisterModel();
            reg.setTeachername(edittextTeachername.getText().toString());
            reg.setPhonenumber(edittextphone.getText().toString());
            reg.setEmail(edittextemail.getText().toString());
            reg.setPassword(edittextconformedpassword.getText().toString());
            reg.setId(key);
            databaseReference.child(key).setValue(reg).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.e("Mytag","true");
//                                Log.e("MyTag","userid:- "+userId);
                    }
                }
            });
        mAuth.createUserWithEmailAndPassword(edittextemail.getText().toString(), edittextpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {

                    progressBar.setVisibility(View.GONE);
                    if ((task.isSuccessful()))
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email",edittextemail.getText().toString());
                        editor.commit();
                        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });


        }



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
            Intent intent=new Intent(RegisterActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();

        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
