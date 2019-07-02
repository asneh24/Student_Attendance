package com.example.sneh.studentattendance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sneh.studentattendance.Activity.TaxiupdateActivity;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Vehicle;

import java.util.ArrayList;

public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.myviewHolder>{
    ArrayList<Vehicle> VehicleList;
    Context context;

    public TaxiAdapter(ArrayList<Vehicle> vehicleList,Context context)
    {
        VehicleList = vehicleList;
        this.context = context;
    }

    @Override
    public TaxiAdapter.myviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.vehical,parent,false);
        myviewHolder holder = new myviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TaxiAdapter.myviewHolder holder, final int position)
    {
        holder.textviewvehicleno.setText(VehicleList.get(position).getVichcleno());
        holder.textviewvehiclename.setText(VehicleList.get(position).getVichclename());
        holder.textviewdrivername.setText(VehicleList.get(position).getName());
        holder.textviewphone.setText(VehicleList.get(position).getPhoneno());
        final String key=VehicleList.get(position).getKey();
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(context,TaxiupdateActivity.class);
                intent.putExtra("key",VehicleList.get(position).getKey());
                intent.putExtra("name",VehicleList.get(position).getName());
                intent.putExtra("vehiclename",VehicleList.get(position).getVichclename());
                intent.putExtra("vehiclenum",VehicleList.get(position).getVichcleno());
                intent.putExtra("phonenum",VehicleList.get(position).getPhoneno());
                context.startActivity(intent);
                Log.e(" key"," key "+key);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return VehicleList.size();

    }

    public class myviewHolder extends RecyclerView.ViewHolder
    {
        TextView textviewvehicleno,textviewvehiclename,textviewdrivername,textviewphone;

        public myviewHolder(View itemView)
        {
            super(itemView);
            textviewvehicleno= (TextView)itemView.findViewById(R.id.textviewvehicleno);
            textviewvehiclename= (TextView)itemView.findViewById(R.id.textviewvehiclename);
            textviewdrivername= (TextView)itemView.findViewById(R.id. textviewdrivername);
            textviewphone= (TextView)itemView.findViewById(R.id.textviewphone);
        }
    }
}

