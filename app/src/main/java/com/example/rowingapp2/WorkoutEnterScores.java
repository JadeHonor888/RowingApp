package com.example.rowingapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.util.Calendar;
import java.util.Locale;

public class WorkoutEnterScores extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Workout currWorkout;
    private Entry currEntry;

    //CSV
    private static final int PERMISSION_REQUEST_CODE = 100;


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
                    if (checkPermission())
                    {
                        saveDataOnClick();
                    } else
                    {
                        requestPermission();
                    }
                }

                if (!csvCheck.isChecked())        //need to have this else otherwise this intent overrides the email intent
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
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();

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
            FileOutputStream out = openFileOutput("CSV_Score_Data_"+time+".csv", Context.MODE_PRIVATE);

            out.write(csvString.getBytes());
            out.close();

            Context context = getApplicationContext();
            final File newFile = new File(Environment.getExternalStorageDirectory(), "SimpleCVS");
            if (!newFile.exists())
            {
                newFile.mkdir();
            }

            File file = new File(context.getFilesDir(), "CSV_Score_Data_"+time+".csv");

            Uri path = FileProvider.getUriForFile(context, "com.example.rowingapp2", file);

            /******************************
             *         EMAIL FILE
             *****************************/

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/csv");
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RowingApp2 workout scores");
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(WorkoutEnterScores.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            Toast.makeText(WorkoutEnterScores.this, "Please allow permission...",Toast.LENGTH_LONG).show();
        }
        else
        {
            ActivityCompat.requestPermissions(WorkoutEnterScores.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

}