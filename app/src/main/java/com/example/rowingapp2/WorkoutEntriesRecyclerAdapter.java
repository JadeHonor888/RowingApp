package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutEntriesRecyclerAdapter extends RecyclerView.Adapter<WorkoutEntriesRecyclerAdapter.MyViewHolder>{
    ArrayList<Entry> entries;
    Context context;

    public WorkoutEntriesRecyclerAdapter(ArrayList<Entry> entries, Context context) {
        this.entries = entries;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        WorkoutEntriesRecyclerAdapter.MyViewHolder holder = new WorkoutEntriesRecyclerAdapter.MyViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
