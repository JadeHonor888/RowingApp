package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMemberScores extends AppCompatActivity {

    Score currScore;

    private double duration;
    private int distance;
    private double split;
    private int stroke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member_scores);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        TextView name = (TextView) findViewById(R.id.editName);
        TextView desc = (TextView) findViewById(R.id.editDesc);

        EditText editDuration = (EditText) findViewById(R.id.editDuration);
        EditText editDistance = (EditText) findViewById(R.id.editDistance);
        EditText editSplit = (EditText) findViewById(R.id.editSplit);
        EditText editStroke = (EditText) findViewById(R.id.editStroke);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();

        SimpleDateFormat simpleDateFormatDistance = new SimpleDateFormat("mm:ss.S");
        SimpleDateFormat simpleDateFormatSplit = new SimpleDateFormat("m:ss.S");

        Intent i = getIntent();
        if (i != null)
        {
            int memberId = i.getIntExtra("memberId", -1);
            int scoreId = i.getIntExtra("scoreId", -1);

            Member currMember = globalVariable.getMemberFromId(memberId);
            currScore = currMember.getScoreFromId(scoreId);

            editDuration.setText(simpleDateFormatDistance.format(currScore.getDuration() * 1000));
            editDistance.setText(String.valueOf(currScore.getDistance()));
            editSplit.setText(simpleDateFormatSplit.format(currScore.getSplit() * 1000));
            editStroke.setText(String.valueOf(currScore.getStroke()));

            name.setText(currScore.getWorkoutName());
            desc.setText(currScore.getWorkoutDesc());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditMemberScores.this, MemberDisplay.class);
                try {
                    Date d = simpleDateFormatDistance.parse(editDuration.getText().toString());
                    String dur1 = new SimpleDateFormat("mm").format(d);     //first bit
                    String dur2 = new SimpleDateFormat("ss.SS").format(d);   //last bit
                    duration = Double.parseDouble(dur1) * 60;
                    duration += Double.parseDouble(dur2);
                    Log.d("Duration", "String(1): " + dur1 + "\nString(2): " + dur2 + "\nDuration: " + duration);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                distance = Integer.parseInt(editDistance.getText().toString());
                //SPLIT
                try {
                    Date s = simpleDateFormatSplit.parse(editSplit.getText().toString());
                    String split1 = new SimpleDateFormat("m").format(s);   //first bit
                    String split2 = new SimpleDateFormat("ss.SS").format(s);   //last bit
                    split = Double.parseDouble(split1) * 60;
                    split += Double.parseDouble(split2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                stroke = Integer.parseInt(editStroke.getText().toString());

                currScore.setDuration(duration);
                currScore.setDistance(distance);
                currScore.setSplit(split);
                currScore.setStroke(stroke);
                globalVariable.saveMemberData();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}