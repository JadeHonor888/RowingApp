package com.example.rowingapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
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

        //IDEA: get the info here and sent it to memberspage
        // question: how do I send it to memberspage?

        //NOT WORKING
        /*
        Intent i = this.getIntent();
        if (i != null)
        {
            Bundle memberInfo = new Bundle();
            memberInfo.putString("fName",i.getStringExtra("fName"));
            memberInfo.putString("lName",i.getStringExtra("lName"));
            memberInfo.putInt("age",i.getIntExtra("age", -1));
            memberInfo.putBoolean("isFemale", i.getBooleanExtra("isFemale", true));
            memberInfo.putBoolean("isPort", i.getBooleanExtra("isPort", false));
            memberInfo.putBoolean("isStarboard", i.getBooleanExtra("isStarboard", false));

            MembersPage mem = new MembersPage();
            mem.setArguments(memberInfo);
        }

         */


    }
}