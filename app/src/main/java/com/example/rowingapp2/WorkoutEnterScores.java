package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class WorkoutEnterScores extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_enter_scores);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        ArrayList<Member> members = globalVariable.getMembers();

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        /**********************************************
         *              RECYCLERVIEW
         *********************************************/
        recyclerView = (RecyclerView) findViewById(R.id.rList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EnterScoresRecyclerAdapter(members,this);
        recyclerView.setAdapter(mAdapter);

        /**********************************************
         *                  BUTTONS
         *********************************************/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WorkoutEnterScores.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}