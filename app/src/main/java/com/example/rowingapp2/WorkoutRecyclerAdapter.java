package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutRecyclerAdapter extends RecyclerView.Adapter<WorkoutRecyclerAdapter.MyViewHolder> {
    ArrayList<Workout> workouts;
    Context context;


    public WorkoutRecyclerAdapter(ArrayList<Workout> workouts, Context context) {
        this.workouts = workouts;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        WorkoutRecyclerAdapter.MyViewHolder holder = new WorkoutRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutRecyclerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(workouts.get(position).getName());
        holder.desc.setText(workouts.get(position).getDesc());

        //BUTTON
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {           //WANT TO LOOK
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WorkoutDisplay.class);
                i.putExtra("name", workouts.get(position).getName());
                i.putExtra("desc", workouts.get(position).getDesc());
                i.putExtra("type", workouts.get(position).getType());
                context.startActivity(i);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {             //WANT TO EDIT
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CreateNewWorkout.class);
                i.putExtra("name", workouts.get(position).getName());
                i.putExtra("desc", workouts.get(position).getDesc());
                i.putExtra("type", workouts.get(position).getType());
                i.putExtra("id", workouts.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;
        RelativeLayout relativeLayout;
        ImageView edit;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            relativeLayout = itemView.findViewById(R.id.relLayout);
            edit = itemView.findViewById(R.id.imageButton);
        }
    }
}