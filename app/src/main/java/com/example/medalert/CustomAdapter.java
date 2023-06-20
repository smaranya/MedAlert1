package com.example.medalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public  class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
   private ArrayList name,type,dose,unit,remaning,time;

    CustomAdapter(Context context,ArrayList name,ArrayList type,ArrayList dose,ArrayList unit,ArrayList time,ArrayList remaning){
        this.context = context;
        this.name = name;
        this.time =  time;
        this.dose = dose;
        this.unit  = unit;
        this.type = type;
        this.remaning = remaning;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.my_role,parent,false);
        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.name.setText(String.valueOf(name.get(position)));
            holder.type.setText(String.valueOf(type.get(position)));
            holder.dose.setText(String.valueOf(dose.get(position)));
            holder.unit.setText(String.valueOf(unit.get(position)));
            holder.remaning.setText(String.valueOf(remaning.get(position)));
            holder.time.setText(String.valueOf(time.get(position)));


    }

    @Override
    public int getItemCount() {

        return name.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name,type,dose,unit,remaning,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            dose = itemView.findViewById(R.id.dosage);
            unit = itemView.findViewById(R.id.unit);
            remaning = itemView.findViewById(R.id.remaining);
            time = itemView.findViewById(R.id.time);

        }
    }
}
