package com.example.rowingapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

    private Workout currWorkout;
    private Entry currEntry;

    ActivityResultLauncher<Intent> editScoreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_enter_scores);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        ArrayList<Member> members = globalVariable.getMembers();

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
                currEntry = currWorkout.getEntries().get(currWorkout.getEntries().size() - 1);
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        
        /**********************************************
         *            RECEIVING SCORE INFO
         *********************************************/
        /*
        ActivityResultLauncher<Intent> editScoreIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent i = result.getData();
                            if(i != null)
                            {
                                //here we update the scores
                                int id = i.getIntExtra("scoreId", -1);
                                currEntry.getScores().get(id).setDuration(i.getDoubleExtra("duration", 0.0));
                                currEntry.getScores().get(id).setDistance(i.getIntExtra("distance", 0));
                                currEntry.getScores().get(id).setSplit(i.getDoubleExtra("split", 0.0));
                                currEntry.getScores().get(id).setStroke(i.getIntExtra("stroke", 0));
                            }

                        }
                    }
                }
        );

         */

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
                editScoreIntent.launch(i);
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









/*                                                              //just storing this here for now
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