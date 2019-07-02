package com.example.sneh.studentattendance.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneh.studentattendance.Activity.LeaveReportActivity;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Check;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaveearlyReportAdapter extends RecyclerView.Adapter<LeaveearlyReportAdapter.MyviewHolder>
{
    Activity context;
    List<Check> list;

    public LeaveearlyReportAdapter(Activity context, List<Check> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LeaveearlyReportAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.absentstudent,parent,false);
        return new LeaveearlyReportAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaveearlyReportAdapter.MyviewHolder holder, final int position)
    {
        holder.textviewstudentid.setText(list.get(position).getKey());
        holder.textviewstudentname.setText(list.get(position).getName());
        Picasso.get().load(list.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.imageview);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, LeaveReportActivity.class);
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

    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageview;
        TextView textviewstudentname,textviewstudentid;

        public MyviewHolder(View itemView)
        { super(itemView);
            imageview=(ImageView)itemView.findViewById(R.id.imageview);
            textviewstudentname= (TextView) itemView.findViewById(R.id.textviewstudentname);
            textviewstudentid= (TextView) itemView.findViewById(R.id.textviewstudentid);

        }
    }
}
