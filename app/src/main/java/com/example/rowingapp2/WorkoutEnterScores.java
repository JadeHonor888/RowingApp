package com.example.rowingapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;

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
        CheckBox csvCheck = (CheckBox) findViewById(R.id.CSVCheck);

        Intent i = getIntent();
        if (i != null)           //if we came from the workout page
        {
            if (i.getBooleanExtra("enterScores", false))
            {

                currWorkout = globalVariable.getWorkoutFromId(i.getIntExtra("workoutId", -1));
                Entry entry1 = currWorkout.newEntry(members.size());                   //make a new entry
                for (int x = 0; x < members.size(); x++)
                {
                    String fullName = members.get(x).getFName() + " " + members.get(x).getLName();          //match member names
                    entry1.getScores().get(x).setMemberName(fullName);
                }
                currEntry = currWorkout.getEntries().get(currWorkout.getEntries().size() - 1);          //get most recent
                Log.d("Entry", "Entry Created: " + entry1.entryToString());
            }
        }

        /**********************************************
         *          ACTIVITY RESULT LAUNCHER
         *********************************************/
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

        mAdapter = new EnterScoresRecyclerAdapter(editScoreIntent,currWorkout, currEntry,this);
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
                /******************************
                 *       FILTER SCORES
                 *****************************/

                Log.d("Entry", "Entry to be Updated: " + currEntry.entryToString());
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
                Log.d("Entry", "Entry Updated: " + currEntry.entryToString());
                globalVariable.saveWorkoutData();
                globalVariable.saveMemberData();


                /******************************
                 *          CSV FILE
                 *****************************/
                if (csvCheck.isChecked())
                {
                    saveDataOnClick();
                }
                else        //need to have this else otherwise this intent overrides the email intent
                {
                    //INTENT
                    Intent i = new Intent(WorkoutEnterScores.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currWorkout.getEntries().remove(currWorkout.getEntries().size() - 1);           //remove currEntry if they decide to cancel
        //Log.d("entryNum", "Num of entries: " + currWorkout.getEntries().size());
    }

    public void saveDataOnClick() {
        /******************************
         *       WRITE CSV FILE
         *****************************/
        String fileName = "scores.csv";
        ArrayList<Score> scores = currEntry.getScores();
        Score currScore;
        String csvString = "";
        for (int i = 0; i < scores.size(); i++)  //loop through the entry that was just created
        {
            currScore = scores.get(i);  //
            csvString = csvString + currScore.getMemberName() + "," +
                    currScore.getDuration() + "," +
                    currScore.getDistance() + "," +
                    currScore.getSplit() + "," +
                    currScore.getStroke() + "\n";
        }

        try {
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(csvString.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


//        File csvFile = new File(Environment.getExternalStorageDirectory() + fileName);
//        Uri uri = FileProvider.getUriForFile(WorkoutEnterScores.this, "com.example.homefolder.example.provider", csvFile);

        /******************************
         *         EMAIL FILE
         *****************************/
        /*
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(fileName);
        } catch (Exception e) {
            Log.e("score", e.getMessage());
        }
        //Uri uri = ;

         */

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/csv");
        String[] email = {"jade.libson@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RowingApp2 workout scores");


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("email", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(WorkoutEnterScores.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    public void sendEmail(String f)
    {
        String fileName = f;
        File fileLocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        Uri uri = Uri.fromFile(fileLocation);
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            String[] email = {"jade.libson@gmail.com"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RowingApp2 workout scores");
            if (uri != null)
            {
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            }

            startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
        } catch(Throwable t) {
            Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

     */
}