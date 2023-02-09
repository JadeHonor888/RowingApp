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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EnterScoresRecyclerAdapter extends RecyclerView.Adapter<EnterScoresRecyclerAdapter.MyViewHolder>{
    ArrayList<Member> members;
    Context context;

    public EnterScoresRecyclerAdapter(ArrayList<Member> members, Context context) {
        this.members = members;
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
        String fullName = members.get(position).getFName() + " " + members.get(position).getLName();
        holder.name.setText(fullName);
        holder.enterScores.setVisibility(View.GONE);

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
                }
                else
                {
                    holder.enterScores.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        ImageView image;
        TextView name;
        ImageView edit;
        Button enterScores;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.check);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.imageButton);
            enterScores = itemView.findViewById(R.id.enterScores);
        }
    }
}
