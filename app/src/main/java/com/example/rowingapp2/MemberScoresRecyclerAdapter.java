package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberScoresRecyclerAdapter extends RecyclerView.Adapter<MemberScoresRecyclerAdapter.MyViewHolder> {
    Context context;
    Member member;
    Score currScore;

    public MemberScoresRecyclerAdapter(Member member, Context context) {
        this.context = context;
        this.member = member;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_scores_item, parent, false);
        MemberScoresRecyclerAdapter.MyViewHolder holder = new MemberScoresRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        currScore = member.getMemberScores().get(position);
        holder.name.setText(currScore.getWorkoutName());
        holder.desc.setText(currScore.getWorkoutDesc());
        holder.duration.setText(String.valueOf(currScore.getDuration()));
        holder.distance.setText(String.valueOf(currScore.getDistance()));
        holder.split.setText(String.valueOf(currScore.getSplit()));
        holder.stroke.setText(String.valueOf(currScore.getStroke()));
    }

    @Override
    public int getItemCount() { return member.getMemberScores().size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView desc;
        TextView duration;
        TextView distance;
        TextView split;
        TextView stroke;
        ImageButton edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            duration = itemView.findViewById(R.id.editDuration);
            distance = itemView.findViewById(R.id.editDistance);
            split = itemView.findViewById(R.id.editSplit);
            stroke = itemView.findViewById(R.id.editStroke);
            edit = itemView.findViewById(R.id.imageButton);
        }

    }
}
