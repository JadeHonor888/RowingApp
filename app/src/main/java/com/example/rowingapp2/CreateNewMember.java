package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateNewMember extends AppCompatActivity {

    int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_member);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
        EditText editFName = (EditText) findViewById(R.id.editfName);
        EditText editLName = (EditText) findViewById(R.id.editlName);
        EditText editAge = (EditText) findViewById(R.id.editAge);
        CheckBox port = (CheckBox) findViewById(R.id.port);
        CheckBox starboard = (CheckBox) findViewById(R.id.starboard);

        GlobalVariable globalVariable = (GlobalVariable) getApplication();

        Intent i = this.getIntent();
        if (i != null)
        {
            memberId = i.getIntExtra("id", -1);
        }
        else
        {
            memberId = -1;
        }

        if(memberId != -1)  //IF THERE'S A MEMBER FILL IN ALL AREAS
        {
            editFName.setText(i.getStringExtra("fName"));
            editLName.setText(i.getStringExtra("lName"));
            editAge.setText(i.getStringExtra("age"));
            if (i.getBooleanExtra("gender", false)) {gender.check(R.id.female);}
            else {gender.check(R.id.male);}
            if (i.getBooleanExtra("port", false)) {port.setChecked(true);}
            if (i.getBooleanExtra("starboard", false)) {starboard.setChecked(true);}
        }

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
                if (editAge.getText().toString().isEmpty())
                {
                    age = 0;
                }
                else
                {
                    age = Integer.parseInt(editAge.getText().toString());
                }
                if (gender.getCheckedRadioButtonId() == R.id.female) { isFemale = true; }
                if(port.isChecked())
                {
                    isPort = true;
                }
                else
                {
                    isPort = false;
                }
                if(starboard.isChecked())
                {
                    isStarboard = true;
                }
                else
                {
                    isStarboard = false;
                }

                //create/edit
                if (memberId != -1)
                {
                    Member currMember = globalVariable.getMemberFromId(memberId);
                    currMember.setfName(fName);
                    currMember.setlName(lName);
                    currMember.setAge(age);
                    currMember.setPort(isPort);
                    currMember.setStarboard(isStarboard);
                    currMember.setFemale(isFemale);
                    globalVariable.saveMemberData();
                }
                else
                {
                    globalVariable.createNewMember(
                            fName,
                            lName,
                            age,
                            isFemale,
                            isPort,
                            isStarboard);
                    globalVariable.saveMemberData();
                }

                Intent i = new Intent(CreateNewMember.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    //member info to use on creation
    String fName;
    String lName;
    int age;
    boolean isFemale;
    boolean isPort;
    boolean isStarboard;

}