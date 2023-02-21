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
                if(port.isChecked()) {isPort = true;}
                if(starboard.isChecked()) {isStarboard = true;}
                    //create intent
                Intent i = new Intent(CreateNewMember.this, MainActivity.class);
                    //Pass along the information:
                i.putExtra("fName", fName);
                i.putExtra("lName", lName);
                i.putExtra("age", age);
                i.putExtra("isFemale", isFemale);
                i.putExtra("isPort", isPort);
                i.putExtra("isStarboard", isStarboard);
                i.putExtra("id", memberId);
                i.putExtra("checkMember", true);
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