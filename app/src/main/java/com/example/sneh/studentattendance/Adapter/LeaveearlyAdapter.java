package com.example.sneh.studentattendance.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sneh.studentattendance.Activity.LeaveActivity;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaveearlyAdapter  extends RecyclerView.Adapter< LeaveearlyAdapter.Myviewholder>
{
    List<Check> list;
    Activity context;


    public  LeaveearlyAdapter(List<Check> list, Activity context) {
        this.list = list;
        this.context = context;
    }



    @Override
    public  LeaveearlyAdapter.Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.time,parent,false);
        return new  LeaveearlyAdapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder( LeaveearlyAdapter.Myviewholder holder, final int position)
    {
        holder.textviewstudentname.setText(list.get(position).getName());

        holder.textviewSection.setText(list.get(position).getClasses());
        String key =list.get(position).getKey();

        Picasso.get().load(""+list.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.imageview);


        Log.e("image","imagepath"+list.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, LeaveActivity.class);
                intent.putExtra("key",list.get(position).getKey());
                intent.putExtra("image",list.get(position).getImage());
                intent.putExtra("name",list.get(position).getName());
                context.startActivity(intent);
                context.finish();




            }

        });

    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }



    public class Myviewholder extends RecyclerView.ViewHolder{
        TextView textviewstudentname,textviewcheckin,textviewdate,textviewSection;
        ImageView imageview;
        public Myviewholder(View itemView) {
            super(itemView);
            textviewstudentname= (TextView)itemView.findViewById(R.id.textviewstudentname);
            imageview=(ImageView)itemView.findViewById(R.id.imageviewcheckin) ;
           // textviewcheckin=(TextView)itemView.findViewById(R.id.textviewcheckin);
            textviewSection=(TextView)itemView.findViewById(R.id.textviewSection);


        }

    }

}

