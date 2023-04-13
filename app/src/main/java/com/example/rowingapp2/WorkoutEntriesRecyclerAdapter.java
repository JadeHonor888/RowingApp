package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        WorkoutEntriesRecyclerAdapter.MyViewHolder holder = new WorkoutEntriesRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String s = "Entry: " +entries.get(position).getDate();
        holder.date.setText(s);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.entryDate);
        }
    }
}
