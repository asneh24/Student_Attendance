package com.example.sneh.studentattendance.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sneh.studentattendance.R;

public class TimenoteActivity extends AppCompatActivity {
    private Button intimebutton,outtimebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timenote);
        intimebutton= (Button) findViewById(R.id.intimebutton);
        outtimebutton= (Button) findViewById(R.id.outtimebutton);
        intimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TimenoteActivity.this,VehicleupdateActivity.class);
                startActivity(intent);
                finish();
            }
        });
        outtimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TimenoteActivity.this,OuttimeActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }
}
