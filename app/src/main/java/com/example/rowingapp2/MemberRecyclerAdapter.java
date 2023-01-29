package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MemberRecyclerAdapter extends RecyclerView.Adapter<MemberRecyclerAdapter.MyViewHolder> {
    ArrayList<Member> members;
    Context context;


    public MemberRecyclerAdapter(ArrayList<Member> members, Context context) {
        this.members = members;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String fullName = members.get(position).getFName() + " " + members.get(position).getLName();
        holder.name.setText(fullName);
        holder.ageGroup.setText(members.get(position).getAgeGroup());
        holder.age.setText(String.valueOf(members.get(position).getAge()));
        holder.gender.setText(members.get(position).getGenderString());
        String side = members.get(position).getStarboardString() + "  " + members.get(position).getPortString();
        holder.side.setText(side);
            if (members.get(position).getImageId()==-1)
                { holder.icon.setVisibility(View.GONE); }
            else
                { holder.icon.setImageResource(members.get(position).getImageId()); }

            //BUTTON
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {       //WANT TO LOOK
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MemberDisplay.class);
                i.putExtra("name", fullName);
                i.putExtra("age", String.valueOf(members.get(position).getAge()));
                i.putExtra("gender", members.get(position).getGenderString());
                i.putExtra("side", side);
                context.startActivity(i);
            }
        });

       holder.edit.setOnClickListener(new View.OnClickListener() {              //WANT TO EDIT
           @Override
           public void onClick(View view) {
               Intent i = new Intent(context, CreateNewMember.class);
               i.putExtra("id", members.get(position).getId());
               i.putExtra("fName", members.get(position).getFName());
               i.putExtra("lName", members.get(position).getLName());
               i.putExtra("age", String.valueOf(members.get(position).getAge()));
               i.putExtra("gender", members.get(position).getGender());
               i.putExtra("port", members.get(position).getPort());
               i.putExtra("starboard", members.get(position).getStarboard());
               context.startActivity(i);
           }
       });
    }

    @Override
    public int getItemCount() {return members.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView age;
        TextView ageGroup;
        TextView gender;
        TextView side;
        RelativeLayout relativeLayout;
        ImageView edit;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            icon = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            ageGroup = itemView.findViewById(R.id.ageGroup);
            gender = itemView.findViewById(R.id.gender);
            side = itemView.findViewById(R.id.side);
            relativeLayout = itemView.findViewById(R.id.relLayout);
            edit = itemView.findViewById(R.id.imageButton);
        }
    }
}


