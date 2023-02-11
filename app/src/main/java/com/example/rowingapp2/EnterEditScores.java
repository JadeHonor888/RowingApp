package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterEditScores.this, WorkoutEnterScores.class);
                duration = Double.parseDouble(editDuration.getText().toString());
                distance = Integer.parseInt(editDistance.getText().toString());
                split = Double.parseDouble(editSplit.getText().toString());
                stroke = Integer.parseInt(editStroke.getText().toString());

                startActivity(i);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EnterEditScores.this, WorkoutEnterScores.class);
                startActivity(i);
            }
        });
    }

    Score currScore;
    double duration;
    int distance;
    double split;
    int stroke;
}