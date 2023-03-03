package com.example.rowingapp2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
        /*
            if (members.get(position).getImageId()==-1)
                { holder.icon.setVisibility(View.GONE); }
            else
                { holder.icon.setImageResource(R.drawable.baseline_account_circle_24); }

         */
        holder.icon.setImageResource(R.drawable.baseline_account_circle_24);

            //BUTTON
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {       //WANT TO LOOK
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MemberDisplay.class);
                i.putExtra("memberId", members.get(position).getMemberId());
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
                                Intent i = new Intent(context, CreateNewMember.class);
                                i.putExtra("id", members.get(position).getMemberId());
                                i.putExtra("fName", members.get(position).getFName());
                                i.putExtra("lName", members.get(position).getLName());
                                i.putExtra("age", String.valueOf(members.get(position).getAge()));
                                i.putExtra("gender", members.get(position).getGender());
                                i.putExtra("port", members.get(position).getPort());
                                i.putExtra("starboard", members.get(position).getStarboard());
                                context.startActivity(i);
                                return true;
                        }
                        if (menuItem.getItemId() == R.id.deleteItem)        //IF THEY CLICK DELETE
                        {
                            Intent i = new Intent(context, MainActivity.class);
                            i.putExtra("id", members.get(position).getMemberId());
                            i.putExtra("delMember", true);
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
    public int getItemCount() {return members.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView age;
        TextView ageGroup;
        TextView gender;
        TextView side;
        LinearLayout linearLayout;
        ImageView edit;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            icon = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            ageGroup = itemView.findViewById(R.id.ageGroup);
            gender = itemView.findViewById(R.id.gender);
            side = itemView.findViewById(R.id.side);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            edit = itemView.findViewById(R.id.imageButton);

        }
    }
}


