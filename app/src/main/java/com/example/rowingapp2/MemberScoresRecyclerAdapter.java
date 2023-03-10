package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class MemberScoresRecyclerAdapter extends RecyclerView.Adapter<MemberScoresRecyclerAdapter.MyViewHolder> {
    Context context;
    Member member;
    Score currScore;

    GlobalVariable globalVariable;

    public MemberScoresRecyclerAdapter(Member member, Context context, GlobalVariable globalVariable) {
        this.context = context;
        this.member = member;
        this.globalVariable = globalVariable;
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
        SimpleDateFormat simpleDateFormatDistance = new SimpleDateFormat("mm:ss.S");
        SimpleDateFormat simpleDateFormatSplit = new SimpleDateFormat("m:ss.S");

        currScore = member.getMemberScores().get(position);
        holder.name.setText(currScore.getWorkoutName());
        if (currScore.getWorkoutDesc().isEmpty())
        {
            holder.desc.setVisibility(View.GONE);
        }
        else
        {
            holder.desc.setText(currScore.getWorkoutDesc());
        }
        holder.duration.setText(simpleDateFormatDistance.format(currScore.getDuration() * 1000));
        holder.distance.setText(String.valueOf(currScore.getDistance()));
        holder.split.setText(simpleDateFormatSplit.format(currScore.getSplit() * 1000));
        holder.stroke.setText(String.valueOf(currScore.getStroke()));

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
                            Intent i = new Intent(context, EditMemberScores.class);
                            i.putExtra("memberId",member.getMemberId());
                            i.putExtra("scoreId", currScore.getScoreId());
                            context.startActivity(i);
                            return true;
                        }
                        if (menuItem.getItemId() == R.id.deleteItem)        //IF THEY CLICK DELETE
                        {
                            member.removeScore(currScore.getScoreId());
                            MemberScoresRecyclerAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                            globalVariable.saveMemberData();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });


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
