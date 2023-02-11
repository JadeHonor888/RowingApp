package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EnterScoresRecyclerAdapter extends RecyclerView.Adapter<EnterScoresRecyclerAdapter.MyViewHolder>{
    Entry entry;
    Context context;

    public EnterScoresRecyclerAdapter(Entry entry, Context context) {
        this.entry = entry;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enter_scores_item, parent, false);
        EnterScoresRecyclerAdapter.MyViewHolder holder = new EnterScoresRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(entry.getScores().get(position).getMemberName());
        holder.duration.setText(String.valueOf(entry.getScores().get(position).getDuration()));
        holder.distance.setText(String.valueOf(entry.getScores().get(position).getDistance()));
        holder.split.setText(String.valueOf(entry.getScores().get(position).getSplit()));
        holder.stroke.setText(String.valueOf(entry.getScores().get(position).getStroke()));

        if (entry.getScores().get(position).getIsChecked())     //checking when going back and forth between pages
        {
            holder.enterScores.setVisibility(View.VISIBLE);
            holder.scoreLine1.setVisibility(View.VISIBLE);
            holder.scoreLine2.setVisibility(View.VISIBLE);
            entry.getScores().get(position).setIsChecked(true);
        }
        else
        {
            holder.enterScores.setVisibility(View.GONE);
            holder.scoreLine1.setVisibility(View.GONE);
            holder.scoreLine2.setVisibility(View.GONE);
            entry.getScores().get(position).setIsChecked(false);

        }

        holder.enterScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EnterEditScores.class);
                context.startActivity(i);
            }
        });

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    holder.enterScores.setVisibility(View.VISIBLE);
                    holder.scoreLine1.setVisibility(View.VISIBLE);
                    holder.scoreLine2.setVisibility(View.VISIBLE);
                    entry.getScores().get(position).setIsChecked(true);
                }
                else
                {
                    holder.enterScores.setVisibility(View.GONE);
                    holder.scoreLine1.setVisibility(View.GONE);
                    holder.scoreLine2.setVisibility(View.GONE);
                    entry.getScores().get(position).setIsChecked(false);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return entry.getScores().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        ImageView image;
        TextView name;
        ImageView enterScores;
        LinearLayout scoreLine1;
        LinearLayout scoreLine2;

        TextView duration;
        TextView distance;
        TextView split;
        TextView stroke;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.check);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            enterScores = itemView.findViewById(R.id.enterScores);
            scoreLine1 = itemView.findViewById(R.id.scoreLine1);
            scoreLine2 = itemView.findViewById(R.id.scoreLine2);

            duration = itemView.findViewById(R.id.editDuration);
            distance = itemView.findViewById(R.id.editDistance);
            split = itemView.findViewById(R.id.editSplit);
            stroke = itemView.findViewById(R.id.editStroke);
        }
    }
}
