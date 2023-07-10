package com.example.medalert;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public  class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, name,time,dose,remaining, type;
    private MyViewHolder holder;
    Activity activity;

    CustomAdapter(Activity activity,Context context, ArrayList id,ArrayList name, ArrayList time,ArrayList dose,ArrayList remaining, ArrayList type){
        this.context = context;

        this.activity = activity;
        this.id = id;
        this.dose = dose;
        this.remaining = remaining;
        this.type = type;
        this.name = name;
        this.time =  time;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.my_role,parent,false);
        return new MyViewHolder(view) ;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        holder.name.setText(String.valueOf(name.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
        if(String.valueOf(type.get(position)).equals("Tablet")){
            holder.dose.setText("Take "+String.valueOf(dose.get(position))+" pill");
        }
        else{
            holder.dose.setText("Take "+String.valueOf(dose.get(position))+" ml");
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("_id",String.valueOf(id.get(position)));
                intent.putExtra("name",String.valueOf(name.get(position)));
                intent.putExtra("dose",String.valueOf(dose.get(position)));
                intent.putExtra("remaining",String.valueOf(remaining.get(position)));
                activity.startActivityForResult(intent,1);
            }

        });

    }
    @Override
    public int getItemCount() {
        return name.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,time, dose;
        LinearLayout mainLayout;
        Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time2);
            dose = itemView.findViewById(R.id.dose1);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            update = itemView.findViewById(R.id.button);
        }
    }
}