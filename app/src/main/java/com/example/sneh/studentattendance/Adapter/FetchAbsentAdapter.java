package com.example.sneh.studentattendance.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Studentinformation;

import java.util.ArrayList;

/**
 * Created by amoly on 11/13/2018.
 */

public class FetchAbsentAdapter extends RecyclerView.Adapter<FetchAbsentAdapter.MyviewHolder> {

    ArrayList<Studentinformation> Studentlist;
    Context context;

    public FetchAbsentAdapter(ArrayList<Studentinformation> studentlist,Context context) {
        Studentlist = studentlist;
        this.context = context;
    }
    @NonNull
    @Override
    public FetchAbsentAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.fetchdata,parent,false);
        FetchAbsentAdapter.MyviewHolder holder = new FetchAbsentAdapter.MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FetchAbsentAdapter.MyviewHolder holder, int position) {
        holder.stdroll.setText(Studentlist.get(position).getId());
        holder.stdname.setText(Studentlist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return Studentlist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView stdroll,stdname;
        public MyviewHolder(View itemView) {
            super(itemView);

            stdroll = itemView.findViewById(R.id.TextViewstdroll);
            stdname = itemView.findViewById(R.id.TextViewstdname);
        }
    }
}
