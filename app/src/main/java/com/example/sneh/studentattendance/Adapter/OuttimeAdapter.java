package com.example.sneh.studentattendance.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sneh.studentattendance.Activity.CheckoutActivity;
import com.example.sneh.studentattendance.R;

import com.example.sneh.studentattendance.model.Check;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OuttimeAdapter extends RecyclerView.Adapter<OuttimeAdapter.myviewholder> {
    List<Check> list;
    Activity context;

    public OuttimeAdapter(List<Check> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.time,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(final myviewholder holder, final int position)
    {

        holder.textviewstudentname.setText(list.get(position).getName());
        holder.textviewSection.setText(list.get(position).getClasses());


        Picasso.get().load(list.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.imageviewcheckin);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, CheckoutActivity.class);
                intent.putExtra("key",list.get(position).getKey());
                intent.putExtra("image",list.get(position).getImage());
                intent.putExtra("name",list.get(position).getName());
                context.startActivity(intent);
                context.finish();
            }
        });






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageviewcheckin;
        TextView textviewstudentname,textviewcheckin,textviewdate,textviewSection;
        public myviewholder(View itemView) {
            super(itemView);
            textviewstudentname= (TextView)itemView.findViewById(R.id.textviewstudentname);
            imageviewcheckin=(ImageView)itemView.findViewById(R.id.imageviewcheckin);
            //textviewcheckin=(TextView)itemView.findViewById(R.id.textviewcheckin);
            textviewSection=(TextView)itemView.findViewById(R.id.textviewSection);





        }
    }

}
