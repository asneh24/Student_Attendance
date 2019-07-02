package com.example.sneh.studentattendance.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneh.studentattendance.Activity.StudentAttendance;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.CheckIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by amoly on 11/29/2018.
 */

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyviewHolder> {
    ArrayList<String> Studentlist;
    ArrayList<String> Studentrolllist;
    Context context;
    Boolean flg = false;
    private ArrayList<String> arrayListStudentId  = new ArrayList<>();
    private ArrayList<String> arrayListStudentrollnum  = new ArrayList<>();
    String userid;

    public CheckoutAdapter(ArrayList<String> studentlist,ArrayList<String> studentrolllist,Context context) {
        Studentlist = studentlist;
        Studentrolllist = studentrolllist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.checkinadapter,parent,false);
        MyviewHolder holder = new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        if(Studentlist.get(position)!=null)
        {
            Log.e("Mytag", "cast==" + Studentlist.get(position));
            holder.textViewstudentTitile.setText(Studentlist.get(position));
            if(!flg)
            {
                holder.imageViewCheck.setChecked(false);
            }
            else
            {
                holder.imageViewCheck.setChecked(true);
            }
        }

    }

    @Override
    public int getItemCount() {
        return Studentlist.size();
    }

    public void checkall()
    {
        flg=true;
        notifyDataSetChanged();
    }
    public void uncheckall()
    {
        flg=false;
        arrayListStudentId.clear();
        arrayListStudentrollnum.clear();
        notifyDataSetChanged();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        final TextView textViewstudentTitile;
        final CheckBox imageViewCheck;
        public MyviewHolder(View itemView) {
            super(itemView);
            textViewstudentTitile = itemView.findViewById(R.id.textViewstudentTitile);
            imageViewCheck=itemView.findViewById(R.id.imageViewCheck);
            imageViewCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION)
                    {
                        if(isChecked)
                        {
                            arrayListStudentId.add(Studentlist.get(pos));
                            arrayListStudentrollnum.add(Studentrolllist.get(pos));
                            //Log.e("Mytag", "arraySize==" + arrayListStudentId.size());
                        }
                        if(!isChecked)
                        {
                            arrayListStudentId.remove(Studentlist.get(pos));
                            arrayListStudentrollnum.remove(Studentrolllist.get(pos));

                        }

                    }
                    //Log.e("Mytag", "arrayListStudentId==" + arrayListStudentId);
                }
            });
        }
    }

    public void count(final String datee, final String timee,final String vehtypee)
    {
        if(arrayListStudentId.size()==0)
        {
            Toast.makeText(context,"Please Select the Student",Toast.LENGTH_SHORT).show();
        }
        else {
            Log.e("MyTag", "checkedarraysize" + arrayListStudentId.size());
            Log.e("MyTag", "checkedarrayname" + arrayListStudentId);
            StudentAttendance studentAttendance = (StudentAttendance) ((Activity) context).getApplication();
            userid = studentAttendance.getUserid();

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CheckOut");
            databaseReference.keepSynced(true);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (int i = 0; i < arrayListStudentId.size(); i++) {
                        String key = databaseReference.push().getKey();
                        CheckIn checkin = new CheckIn();
                        checkin.setStudentname(arrayListStudentId.get(i).toString());
                        checkin.setStudentrollnumber(arrayListStudentrollnum.get(i).toString());
                        checkin.setVehicletypee(vehtypee);
                        checkin.setKey(key);
                        checkin.setDatee(datee);
                        checkin.setTimee(timee);
                        databaseReference.child(userid).child(key).setValue(checkin);
                    }
                    Toast.makeText(context, "Attendance Done...", Toast.LENGTH_SHORT).show();
                    flg = false;
                    arrayListStudentId.clear();
                    notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
