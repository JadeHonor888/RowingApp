package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberScoresRecyclerAdapter extends RecyclerView.Adapter<MemberScoresRecyclerAdapter.MyViewHolder> {
    Context context;
    Member member;

    public MemberScoresRecyclerAdapter(Member member, Context context) {
        this.context = context;
        this.member = member;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enter_scores_item, parent, false);
        MemberScoresRecyclerAdapter.MyViewHolder holder = new MemberScoresRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() { return member.getMemberScores().size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }
}
