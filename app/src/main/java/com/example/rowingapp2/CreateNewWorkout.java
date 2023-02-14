package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class CreateNewWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_workout);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editDesc = (EditText) findViewById(R.id.editDesc);
        RadioGroup radioType = (RadioGroup) findViewById(R.id.type);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        Workout currWorkout;

        Intent i = this.getIntent();
        if (i != null && !i.getBooleanExtra("newWorkout", false))
        {
            workoutId = i.getIntExtra("workoutId", -1);
            currWorkout = globalVariable.getWorkoutFromId(workoutId);       //get currWorkout from id


            editName.setText(currWorkout.getName());
            editDesc.setText(currWorkout.getDesc());
            if (currWorkout.getType().equals("Single Distance"))
            {
                radioType.check(R.id.singleDistance);
            }
            else if (currWorkout.getType().equals("Single Time"))
            {
                radioType.check(R.id.singleTime);
            }
            else if (currWorkout.getType().equals("Interval"))
            {
                radioType.check(R.id.interval);
            }
        }
        else
        {
            workoutId = -1;
        }

        cancel.setOnClickListener(new View.OnClickListener() {      //go back to main activity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateNewWorkout.this, MainActivity.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pick up on the edit text stuff
                name = editName.getText().toString();
                desc = editDesc.getText().toString();
                if (radioType.getCheckedRadioButtonId() == R.id.singleDistance)
                {
                    type = "Single Distance";
                }
                else if (radioType.getCheckedRadioButtonId() == R.id.singleTime)
                {
                    type = "Single Time";
                }
                else if (radioType.getCheckedRadioButtonId() == R.id.interval)
                {
                    type = "Interval";
                }
                //create intent
                Intent i = new Intent(CreateNewWorkout.this, MainActivity.class);
                //Pass along the information:
                i.putExtra("name", name);
                i.putExtra("desc", desc);
                i.putExtra("type", type);
                i.putExtra("workoutId", workoutId);
                i.putExtra("checkWorkout", true);
                startActivity(i);


            }
        });
    }
    int workoutId;
    String name;
    String desc;
    String type;
}