package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterEditScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_edit_scores);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button camera = (Button) findViewById(R.id.camera);

        EditText editDuration = (EditText) findViewById(R.id.editDuration);
        EditText editDistance = (EditText) findViewById(R.id.editDistance);
        EditText editSplit = (EditText) findViewById(R.id.editSplit);
        EditText editStroke = (EditText) findViewById(R.id.editStroke);

        Intent i = getIntent();
        if (i != null)
        {
            scoreId = i.getIntExtra("scoreId", -1);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterEditScores.this, WorkoutEnterScores.class);
                duration = Double.parseDouble(editDuration.getText().toString());
                distance = Integer.parseInt(editDistance.getText().toString());
                split = Double.parseDouble(editSplit.getText().toString());
                stroke = Integer.parseInt(editStroke.getText().toString());
                //i.putExtra("scoresEntered", true);
                i.putExtra("scoreId", scoreId);
                i.putExtra("duration", duration);
                i.putExtra("distance", distance);
                i.putExtra("split", split);
                i.putExtra("stroke", stroke);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(EnterEditScores.this, WorkoutEnterScores.class);
//                startActivity(i);
                finish();
            }
        });
    }

    int scoreId;
    double duration;
    int distance;
    double split;
    int stroke;
}