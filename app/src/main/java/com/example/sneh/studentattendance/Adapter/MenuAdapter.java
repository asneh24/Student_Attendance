package com.example.sneh.studentattendance.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.sneh.studentattendance.Activity.LeavefectchActivity;
import com.example.sneh.studentattendance.Activity.ReportActivity;
import com.example.sneh.studentattendance.Activity.StudententryActivity;
import com.example.sneh.studentattendance.Activity.TaxiActivity;
import com.example.sneh.studentattendance.Activity.TaxientryActivity;
import com.example.sneh.studentattendance.Activity.VehicleActivity;
import com.example.sneh.studentattendance.Activity.VehicleinformationActivity;
import com.example.sneh.studentattendance.Activity.CheckinfetchActivity;
import com.example.sneh.studentattendance.Activity.CheckoutfectchActivity;
import com.example.sneh.studentattendance.Activity.StudentinformationActivity;
import com.example.sneh.studentattendance.model.MenuModel;
import com.example.sneh.studentattendance.R;
import android.content.Intent;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context mContext;
    private List<MenuModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

             title = (TextView) view.findViewById(R.id.title);
             thumbnail =(ImageView) view.findViewById(R.id.thumbnail);


        }
    }


    public MenuAdapter(Context mContext, List<MenuModel> albumList)
    {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MenuModel album = albumList.get(position);
         holder.title.setText(album.getName());
         Glide.with(mContext).load(album.getImage()).into(holder.thumbnail);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (position == 0) {
                    Intent intent = new Intent(mContext, StudententryActivity.class);
                    mContext.startActivity(intent);

                } else if (position == 1) {
                    Intent intent = new Intent(mContext,VehicleActivity.class);
                    mContext.startActivity(intent);
                }
               else if (position == 2) {
                    Intent intent = new Intent(mContext,TaxientryActivity.class);
                    mContext.startActivity(intent);
                }
               else if (position == 3) {
                   Intent intent = new Intent(mContext,StudentinformationActivity.class);
                   mContext.startActivity(intent);

               }
               else if (position == 4) {
                   Intent intent = new Intent(mContext,VehicleinformationActivity.class);
                   mContext.startActivity(intent);

               }
               else if (position == 5) {
                   Intent intent = new Intent(mContext,TaxiActivity.class);
                   mContext.startActivity(intent);

               }
               else if (position == 6) {
                   Intent intent = new Intent(mContext,CheckinfetchActivity.class);
                   mContext.startActivity(intent);

               }
               else if (position == 7) {
                   Intent intent = new Intent(mContext,CheckoutfectchActivity.class);
                   mContext.startActivity(intent);

               }
               else if (position == 8) {
                   Intent intent = new Intent(mContext,LeavefectchActivity.class);
                   mContext.startActivity(intent);

               }
               else if (position == 9) {
                   Intent intent = new Intent(mContext,ReportActivity.class);
                   mContext.startActivity(intent);

               }




            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (position == 0) {
                    Intent intent = new Intent(mContext, StudententryActivity.class);
                    mContext.startActivity(intent);

                } else if (position == 1) {
                    Intent intent = new Intent(mContext,VehicleActivity.class);
                    mContext.startActivity(intent);
                }
              else if (position == 2) {
                    Intent intent = new Intent(mContext, TaxientryActivity.class);
                    mContext.startActivity(intent);
                }
                 else if (position == 3) {
                     Intent intent = new Intent(mContext,StudentinformationActivity.class);
                     mContext.startActivity(intent);
                 }
                 else if (position == 4) {
                     Intent intent = new Intent(mContext,VehicleinformationActivity.class);
                     mContext.startActivity(intent);

                 }
                 else if (position == 5) {
                     Intent intent = new Intent(mContext,TaxiActivity.class);
                     mContext.startActivity(intent);

                 }
                 else if (position == 6) {
                     Intent intent = new Intent(mContext,CheckinfetchActivity.class);
                     mContext.startActivity(intent);

                 }
                 else if (position == 7) {
                     Intent intent = new Intent(mContext,CheckoutfectchActivity.class);
                     mContext.startActivity(intent);

                 }
                 else if (position == 8) {
                     Intent intent = new Intent(mContext,LeavefectchActivity.class);
                     mContext.startActivity(intent);

                 }
                 else if (position == 9) {
                     Intent intent = new Intent(mContext,ReportActivity.class);
                     mContext.startActivity(intent);

                 }






            }
        });


    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

