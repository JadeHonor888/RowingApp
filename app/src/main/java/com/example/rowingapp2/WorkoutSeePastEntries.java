package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WorkoutSeePastEntries extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Workout currWorkout;
    private ArrayList<Entry> entries;
    private Entry currEntry;
    private ArrayList<Score> scores;

    private int currEntryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_see_past_entries);

        ImageView back = (ImageView) findViewById(R.id.back);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();

        Intent i = this.getIntent();
        if (i != null)
        {
            currWorkout = globalVariable.getWorkoutFromId(i.getIntExtra("workoutId", -1));
            entries = currWorkout.getEntries();

            if (entries.size() > 0)
            {
                currEntry = entries.get(0); //get the first entry
                currEntryId = currEntry.getEntryId();   //get the first entry id
                scores = currEntry.getScores();
            }
        }

        /**********************************************
         *              RECYCLERVIEW
         *********************************************/

        recyclerView = (RecyclerView) findViewById(R.id.rList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new WorkoutEntriesRecyclerAdapter(entries,currEntryId, this);
        recyclerView.setAdapter(mAdapter);

        /**********************************************
         *                 LISTVIEW
         *********************************************/


        ListView listView = (ListView) findViewById(R.id.aList);

        CustomScoreAdapter listAdapter = new CustomScoreAdapter(this, R.id.aList, scores);

        listView.setAdapter(listAdapter);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}