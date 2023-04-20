package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutEntriesRecyclerAdapter extends RecyclerView.Adapter<WorkoutEntriesRecyclerAdapter.MyViewHolder>{
    ArrayList<Entry> entries;
    int entryId;
    Context context;

    public WorkoutEntriesRecyclerAdapter(ArrayList<Entry> entries,int entryId, Context context) {
        this.entries = entries;
        this.entryId = entryId;
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
        holder.linearLayout.setBackgroundResource(R.color.white);

        String s = "Entry: " +entries.get(position).getEntryId();
        holder.date.setText(s);

        if (entries.get(position).getEntryId()==entryId)
        {
            holder.linearLayout.setBackgroundResource(R.color.light_gray);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryId = entries.get(holder.getAdapterPosition()).getEntryId();   //highlight the clicked entry and change the currEntryId



                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.entryDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
