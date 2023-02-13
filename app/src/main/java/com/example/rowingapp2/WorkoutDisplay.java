package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_display);

        ImageView back = (ImageView) findViewById(R.id.back);
        TextView name = (TextView) findViewById(R.id.displayName);
        TextView desc = (TextView) findViewById(R.id.displayDesc);
        TextView type = (TextView) findViewById(R.id.displayType);
        Button enterScores = (Button) findViewById(R.id.enterScores);
        Button pastEntries = (Button) findViewById(R.id.pastEntries);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        Workout currWorkout;

        Intent i = this.getIntent();
        if (i != null)
        {
            currWorkout = globalVariable.getWorkoutFromId(i.getIntExtra("workoutId", -1));
            id = currWorkout.getId();

            name.setText(currWorkout.getName());
            desc.setText(currWorkout.getDesc());
            type.setText(currWorkout.getType());
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkoutDisplay.this, MainActivity.class);
                startActivity(i);
            }
        });

        enterScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkoutDisplay.this, WorkoutEnterScores.class);
                i.putExtra("enterScores", true);
                i.putExtra("workoutId", id);
                startActivity(i);
            }
        });
    }
    int id;
}