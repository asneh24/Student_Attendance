package com.example.sneh.studentattendance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneh.studentattendance.Activity.StudentupdateActivity;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Studentinformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyviewHolder>{
    ArrayList<Studentinformation> Studentlist;
    Context context;


    public StudentAdapter(ArrayList<Studentinformation> studentlist,Context context) {
        Studentlist = studentlist;
        this.context = context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.student,parent,false);
        MyviewHolder holder = new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, final int position)
    {
        holder.textviewid.setText(Studentlist.get(position).getId());
        holder.textviewname.setText(Studentlist.get(position).getName());
        holder.textviewclass.setText(Studentlist.get(position).getClasse());
        holder.textviewsection.setText(Studentlist.get(position).getSection());
        Picasso.get().load(Studentlist.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.list_image);
        final String key=Studentlist.get(position).getId();

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               Intent intent =new Intent(context,StudentupdateActivity.class);
               intent.putExtra("key",Studentlist.get(position).getKey());
               intent.putExtra("id",Studentlist.get(position).getId());
               intent.putExtra("name",Studentlist.get(position).getName());
               intent.putExtra("class",Studentlist.get(position).getClasse());
               intent.putExtra("section",Studentlist.get(position).getSection());
               intent.putExtra("image",Studentlist.get(position).getImage());
               intent.putExtra("address", Studentlist.get(position).getAddress());
               intent.putExtra("phone",Studentlist.get(position).getPhoneno());
               intent.putExtra("gender",Studentlist.get(position).getGender());
               context.startActivity(intent);
               Log.e(" key"," key "+key);

           }
       });


    }

    @Override
    public int getItemCount() {
        return Studentlist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
      TextView textviewid,textviewname,textviewclass,textviewsection;
      ImageView list_image;

        public MyviewHolder(View itemView) {
            super(itemView);

            list_image = (ImageView) itemView.findViewById(R.id.list_image);
            textviewid= (TextView)itemView.findViewById(R.id.textviewid);
            textviewname= (TextView)itemView.findViewById(R.id.textviewname);
            textviewclass= (TextView)itemView.findViewById(R.id.textviewclass);
            textviewsection= (TextView)itemView.findViewById(R.id.textviewsection);


        }
    }

}
