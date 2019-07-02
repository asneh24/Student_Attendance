package com.example.sneh.studentattendance.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneh.studentattendance.R;

public class CheckOutVehicleActivity extends AppCompatActivity {

    ImageView imgbus,imgtaxi,imgindividual;
    TextView textViewbus,textViewtaxi,textViewindividual;
    StudentAttendance studentAttendance;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_vehicle);

        studentAttendance = (StudentAttendance) getApplication();

        if (studentAttendance.getLang(CheckOutVehicleActivity.this) == 0) {
            context = LocaleManager.setNewLocale(CheckOutVehicleActivity.this, "en");
            resources = context.getResources();
        } else if (studentAttendance.getLang(CheckOutVehicleActivity.this) == 1) {
            context = LocaleManager.setNewLocale(CheckOutVehicleActivity.this, "el");
            resources = context.getResources();
        }

        imgbus = findViewById(R.id.imageViewBus);
        imgtaxi = findViewById(R.id.imageViewTaxi);
        imgindividual = findViewById(R.id.imageViewIndividual);
        textViewbus = findViewById(R.id.textViewBus);
        textViewtaxi = findViewById(R.id.textViewTaxi);
        textViewindividual = findViewById(R.id.textViewIndividual);
        textViewbus.setText(resources.getString(R.string.Bus));
        textViewtaxi.setText(resources.getString(R.string.Taxi));
        textViewindividual.setText(resources.getString(R.string.Individual));

        imgbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckOutVehicleActivity.this, CheckoutfectchActivity.class);
                intent.putExtra("Vehtype","Bus");
                startActivity(intent);

            }
        });
        imgtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckOutVehicleActivity.this,CheckoutfectchActivity.class);
                intent.putExtra("Vehtype","Taxi");
                startActivity(intent);

            }

        });
        imgindividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(CheckOutVehicleActivity.this,CheckoutfectchActivity.class);
                intent.putExtra("Vehtype","Individual");
                startActivity(intent);

            }
        });

        textViewbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckOutVehicleActivity.this, CheckoutfectchActivity.class);
                intent.putExtra("Vehtype","Bus");
                startActivity(intent);

            }
        });
        textViewtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckOutVehicleActivity.this,CheckoutfectchActivity.class);
                intent.putExtra("Vehtype","Taxi");
                startActivity(intent);

            }

        });
        textViewindividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(CheckOutVehicleActivity.this,CheckoutfectchActivity.class);
                intent.putExtra("Vehtype","Individual");
                startActivity(intent);

            }
        });
    }
}
