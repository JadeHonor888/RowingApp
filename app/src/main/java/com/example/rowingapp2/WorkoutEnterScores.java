package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Array;
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
        Workout currWorkout = new Workout();



        Intent i = getIntent();
        if (i != null)           //if we came from the workout page
        {
            if (i.getBooleanExtra("enterScores", false))
            {

                Entry entry1 = new Entry(members.size());                       //make a new entry
                for (int x = 0; x < members.size(); x++)
                {
                    String fullName = members.get(x).getFName() + " " + members.get(x).getLName();          //match member names
                    entry1.getScores().get(x).setMemberName(fullName);
                }
                currWorkout = globalVariable.getWorkoutFromId(i.getIntExtra("workoutId", -1));
                currWorkout.getEntries().add(entry1);           //add to workouts so that it can be accessible at all times
            }


            /*
            if(i.getBooleanExtra("scoresEntered", false))
            {
                //here we update the scores
                int id = i.getIntExtra("scoreId", -1);
                currEntry.getScores().get(id).setDuration(i.getDoubleExtra("duration", 0.0));
                currEntry.getScores().get(id).setDistance(i.getIntExtra("distance", 0));
                currEntry.getScores().get(id).setSplit(i.getDoubleExtra("split", 0.0));
                currEntry.getScores().get(id).setStroke(i.getIntExtra("stroke", 0));
            }

             */

        }

        Entry currEntry = currWorkout.getEntries().get(currWorkout.getEntries().size() - 1);            //NOTE: this doesn't work


        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);


        /**********************************************
         *              RECYCLERVIEW
         *********************************************/
        recyclerView = (RecyclerView) findViewById(R.id.rList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EnterScoresRecyclerAdapter(currEntry,this);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HERE GOES THE CODE TO UPDATE WORKOUTS & MEMBER SCORES
            }
        });

    }
}