package com.example.rowingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_display);

        ImageView back = (ImageView) findViewById(R.id.back);
        TextView name = (TextView) findViewById(R.id.displayName);
        TextView age = (TextView) findViewById(R.id.displayAge);
        TextView gender = (TextView) findViewById(R.id.displayGender);
        TextView side = (TextView) findViewById(R.id.displaySide);
        TextView split = (TextView) findViewById(R.id.displaySplit);

        Intent i = this.getIntent();
        if (i != null)
        {
            name.setText(i.getStringExtra("name"));
            age.setText(i.getStringExtra("age"));
            gender.setText(i.getStringExtra("gender"));
            side.setText(i.getStringExtra("side"));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MemberDisplay.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}