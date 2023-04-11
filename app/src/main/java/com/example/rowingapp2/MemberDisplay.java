package com.example.rowingapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MemberDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    SimpleDateFormat simpleDateFormatSplit = new SimpleDateFormat("m:ss.S");

    private Member currMember;

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

        GlobalVariable globalVariable = (GlobalVariable) getApplication();

        Intent i = this.getIntent();
        if (i != null)
        {
            //get member
            currMember = globalVariable.getMemberFromId(i.getIntExtra("memberId", -1));

            //set views to match attributes
            String fullName = currMember.getFName() + " " + currMember.getLName();
            name.setText(fullName);
            age.setText(String.valueOf(currMember.getAge()));
            gender.setText(currMember.getGenderString());
            String fullSide = currMember.getStarboardString() + "  " + currMember.getPortString();
            side.setText(fullSide);
            split.setText(simpleDateFormatSplit.format((currMember.getMemberSplit()/currMember.getMemberScores().size()) * 1000));

            Log.d("member", "Member Displayed: " + currMember.memberToString());
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

        mAdapter = new MemberScoresRecyclerAdapter(editScoreIntent, currMember,this, globalVariable);
        recyclerView.setAdapter(mAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MemberDisplay.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}