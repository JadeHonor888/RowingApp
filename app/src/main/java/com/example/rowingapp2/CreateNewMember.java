package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateNewMember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_member);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
        if (gender.getCheckedRadioButtonId() == R.id.male) { isFemale = false; }        //Gender is NOT WORKING (but this is for later)

        EditText editFName = (EditText) findViewById(R.id.editfName);
        EditText editLName = (EditText) findViewById(R.id.editlName);
        EditText editAge = (EditText) findViewById(R.id.editAge);

        cancel.setOnClickListener(new View.OnClickListener() {      //go back to main activity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateNewMember.this, MainActivity.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //pick up on the edit text stuff
                fName = editFName.getText().toString();
                lName = editLName.getText().toString();
                age = Integer.parseInt(editAge.getText().toString());

                Intent i = new Intent(CreateNewMember.this, MainActivity.class);
                    //Pass along the information:
                i.putExtra("fName", fName);
                i.putExtra("lName", lName);
                i.putExtra("age", age);
                i.putExtra("isFemale", isFemale);
                i.putExtra("isPort", isPort);
                i.putExtra("isStarboard", isStarboard);
                startActivity(i);
            }
        });
    }

    //member info to use on creation
    String fName;
    String lName;
    int age;
    boolean isFemale = true;
    boolean isPort;
    boolean isStarboard;

    public void isPort(View view) {isPort = !isPort;}
    public void isStarboard(View view) {isStarboard = !isStarboard;}





}