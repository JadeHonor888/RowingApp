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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;

public class WorkoutEnterScores extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Workout currWorkout;
    private Entry currEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_enter_scores);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        ArrayList<Member> members = globalVariable.getMembers();

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

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
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
        );

        /**********************************************
         *              RECYCLERVIEW
         *********************************************/
        recyclerView = (RecyclerView) findViewById(R.id.rList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EnterScoresRecyclerAdapter(editScoreIntent,currEntry,this);
        recyclerView.setAdapter(mAdapter);

        /**********************************************
         *                  BUTTONS
         *********************************************/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WorkoutEnterScores.this, MainActivity.class);
                currWorkout.getEntries().remove(currWorkout.getEntries().size() - 1);           //remove currEntry if they decide to cancel
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UPDATE WORKOUTS & MEMBER SCORES
                for (int x = 0; x < currEntry.getScores().size(); x++)    //go through and remove all that weren't checked and add all that were checked
                {
                    Score currScore = currEntry.getScores().get(x);
                    if (currScore.getIsChecked())    //if it's checked
                    {
                        for (int y = 0; y < members.size(); y++)    //go through the list of members till we find the one that matches
                        {
                            String fullName = members.get(y).getFName() + " " + members.get(y).getLName();
                            if (currScore.getMemberName().equals(fullName))
                            {
                                members.get(y).addScore(currScore);             //add to member
                                break;
                            }
                        }
                        currScore.setWorkoutName(currWorkout.getName());        //add on the name and desc
                        currScore.setWorkoutDesc(currWorkout.getDesc());
                    }
                    else        //if it wasn't checked
                    {
                        currEntry.getScores().remove(currScore);    //remove it
                        x--;
                    }
                }

                currWorkout.getEntries().set(currWorkout.getEntries().size() - 1, currEntry);           //add the updated entry without the members who didn't row
                globalVariable.saveWorkoutData();
                globalVariable.saveMemberData();

                //INTENT
                Intent i = new Intent(WorkoutEnterScores.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currWorkout.getEntries().remove(currWorkout.getEntries().size() - 1);           //remove currEntry if they decide to cancel
        //Log.d("entryNum", "Num of entries: " + currWorkout.getEntries().size());
    }
}