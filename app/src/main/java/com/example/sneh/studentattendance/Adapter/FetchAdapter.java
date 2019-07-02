package com.example.sneh.studentattendance.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.CheckIn;

import java.util.ArrayList;

/**
 * Created by amoly on 11/10/2018.
 */

public class FetchAdapter extends RecyclerView.Adapter<FetchAdapter.MyviewHolder> {
    ArrayList<CheckIn> Studentlist;
    Context context;

    public FetchAdapter(ArrayList<CheckIn> studentlist,Context context) {
        Studentlist = studentlist;
        this.context = context;
    }
    @NonNull
    @Override
    public FetchAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.fetchdata,parent,false);
        FetchAdapter.MyviewHolder holder = new FetchAdapter.MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FetchAdapter.MyviewHolder holder, int position) {

        holder.stdroll.setText(Studentlist.get(position).getStudentrollnumber());
        holder.stdname.setText(Studentlist.get(position).getStudentname());

    }

    @Override
    public int getItemCount() {
        return Studentlist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView stdroll,stdname;
        public MyviewHolder(View itemview) {
            super(itemview);

            stdroll = itemview.findViewById(R.id.TextViewstdroll);
            stdname = itemview.findViewById(R.id.TextViewstdname);
        }
    }
}
