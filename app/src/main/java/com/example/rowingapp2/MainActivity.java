package com.example.rowingapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();       //IDK IF IT'S USEFUL BUT NOW WE CAN SEND BUNDLES TO MEMBERS PAGE (AND THE OTHERS IF WE WANT)

        // TAB NAMES
        ArrayList<String> tabNames = new ArrayList<>();
        tabNames.add("MEMBERS");
        tabNames.add("WORKOUT");
        tabNames.add("CALENDAR");

        /**
         *      GET ELEMENTS
         */

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);


        /**
         *      CREATE ADAPTER
         */
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, bundle);
        viewPager2.setAdapter(adapter);


        /**
         *      SETS UP TAB ON RUNTIME
          */
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames.get(position));
            }
        }).attach();

        //DO NOT PUT BUTTONS IN HERE

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        globalVariable.loadMemberData();
        globalVariable.loadWorkoutData();

        Intent i = this.getIntent();
        if (i != null)
        {
            if(i.getBooleanExtra("checkMember", false))     //IS IT A MEMBER?
            {
                if (i.getIntExtra("id", -1) == -1)
                {   //CREATE MEMBER
                    globalVariable.createNewMember(                     //TODO: JUST PASS THE ID BETWEEN THE CLASSES INSTEAD OF ALL THE INFORMATION
                            i.getStringExtra("fName"),
                            i.getStringExtra("lName"),
                            i.getIntExtra("age", -1),
                            i.getBooleanExtra("isFemale", true),
                            i.getBooleanExtra("isPort", false),
                            i.getBooleanExtra("isStarboard", false));
                }
                else
                {   //EDIT MEMBER
                    Member editMember = new Member(
                            i.getStringExtra("fName"),
                            i.getStringExtra("lName"),
                            i.getIntExtra("age", -1),
                            i.getBooleanExtra("isFemale", true),
                            i.getBooleanExtra("isPort", false),
                            i.getBooleanExtra("isStarboard", false),
                            i.getIntExtra("id", -1));
                    globalVariable.editMember(editMember, i.getIntExtra("id", -1));
                }
            }
            else if (i.getBooleanExtra("checkWorkout", false))      //IS IT A WORKOUT?
            {
                if (i.getIntExtra("workoutId", -1) == -1)
                {   //CREATE WORKOUT
                    globalVariable.createNewWorkout(
                            i.getStringExtra("name"),
                            i.getStringExtra("desc"),
                            i.getStringExtra("type"));
                }
                else
                {   //EDIT WORKOUT
                    Workout editWorkout = new Workout(
                            i.getStringExtra("name"),
                            i.getStringExtra("desc"),
                            i.getStringExtra("type"),
                            i.getIntExtra("workoutId", -1));
                    globalVariable.editWorkout(editWorkout, i.getIntExtra("id", -1));
                }
            }
            else if(i.getBooleanExtra("delMember", false))      //DELETE MEMBER
            {
                globalVariable.removeMember(i.getIntExtra("id", -1));
            }
            else if(i.getBooleanExtra("delWorkout", false))      //DELETE WORKOUT
            {
                globalVariable.removeWorkout(i.getIntExtra("id", -1));
            }

        }


    }
}