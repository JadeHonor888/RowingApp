package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class CreateNewMember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_member);
    }

    //member info to use on creation
    String fName;
    String lName;
    int age;
    boolean isFemale = true;
    boolean isPort;
    boolean isStarboard;

    public void getFName(View view)
    {
        EditText editFName = (EditText) findViewById(R.id.editfName);
        fName = editFName.getText().toString();
    }
    public void getLName(View view)
    {
        EditText editLName = (EditText) findViewById(R.id.editlName);
        lName = editLName.getText().toString();
    }
    public void isPort(View view) {isPort = !isPort;}
    public void isStarboard(View view) {isStarboard = !isStarboard;}
    public void isFemale(View view)                                 //NOTE: Look up proper syntax for radio buttons & groups
    {
        RadioButton fem = (RadioButton) findViewById(R.id.female);
        RadioButton mal = (RadioButton) findViewById(R.id.male);
        if(fem.isChecked())
        {
            isFemale = true;
        }
        else if(mal.isChecked())
        {
            isFemale = false;
        }
    }



}