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
        if (gender.getCheckedRadioButtonId() == R.id.male) { isFemale = false; }

        EditText editFName = (EditText) findViewById(R.id.editfName);

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

        editFName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateNewMember.this,"editfName is picking this up", Toast.LENGTH_LONG).show();
                fName = editFName.getText().toString();
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

    /*
    public void getFName(View view)
    {
        EditText editFName = (EditText) findViewById(R.id.editfName);
        fName = editFName.getText().toString();
    }

     */
    public void getLName(View view)
    {
        EditText editLName = (EditText) findViewById(R.id.editlName);
        lName = editLName.getText().toString();
    }
    public void getAge(View view)
    {
        EditText editAge = (EditText) findViewById(R.id.editAge);
        age = Integer.parseInt(editAge.getText().toString());
    }
    public void isPort(View view) {isPort = !isPort;}
    public void isStarboard(View view) {isStarboard = !isStarboard;}





}