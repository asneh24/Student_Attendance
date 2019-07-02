package com.example.sneh.studentattendance.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Handler;

import com.example.sneh.studentattendance.R;
import com.google.firebase.auth.FirebaseUser;

public class Splashscreen extends AppCompatActivity {
    private static final long WELCOMETIMEOUT = 5000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);



//        View view = findViewById(android.R.id.content);
//        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
//        mLoadAnimation.setDuration(2000);
//        view.startAnimation(mLoadAnimation);


    }
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser)
    {
        if(currentUser!= null)
        {
            Intent intent=new Intent(Splashscreen.this,DashboardActivity.class);
            startActivity(intent);
            finish();

        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(Splashscreen.this,ChangelanguageActivity.class);
                    startActivity(intent);

                    //overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                    finish();
                }
            },WELCOMETIMEOUT);

        }

    }
}
