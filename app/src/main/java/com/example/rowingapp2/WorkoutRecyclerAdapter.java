package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {           //WANT TO LOOK
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WorkoutDisplay.class);
                i.putExtra("workoutId", workouts.get(position).getWorkoutId());
                context.startActivity(i);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.inflate(R.menu.edit_delete_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.editItem)          //IF THEY CLICK EDIT
                        {
                            Intent i = new Intent(context, CreateNewWorkout.class);
                            i.putExtra("workoutId", workouts.get(position).getWorkoutId());
                            context.startActivity(i);
                            return true;
                        }
                        if (menuItem.getItemId() == R.id.deleteItem)        //IF THEY CLICK DELETE
                        {
                            Intent i = new Intent(context, MainActivity.class);
                            i.putExtra("workoutId", workouts.get(position).getWorkoutId());
                            i.putExtra("delWorkout", true);
                            context.startActivity(i);
                            return true;
                        }
                        return false;
                    }
                });
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
        LinearLayout linearLayout;
        ImageView edit;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            edit = itemView.findViewById(R.id.imageButton);
        }
    }
}