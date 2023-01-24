package com.example.rowingapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MembersPage extends Fragment {

    //TEST: Removing the instance stuff

    //Declare variables out here so that it doesn't recreate it each time
    ArrayList<Member> members;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_members_page, container, false);
    }





    //NOTE: code should be executed in onViewCreated() b/c lifetime of a Fragment differs from an Activity: it's layout is created after onCreate()
    MemberAdapter memberAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**********************************************
         *                  LISTVIEW
         *********************************************/
        /*
        members = new ArrayList<>(); //REMINDER: **YOU NEED THIS LINE OR THINGS BREAK!!**

        members.add(new Member("ROWER 1", 15));     //tests
        members.add(new Member("ROWER 2", 18));

        memberAdapter = new MemberAdapter(view.getContext(),R.id.list, members, R.color.white);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(memberAdapter);

         */


        /**********************************************
         *              RECYCLERVIEW
         *********************************************/

        members = new ArrayList<>();    //REMINDER: **YOU NEED THIS LINE OR THINGS BREAK!!**

        members.add(new Member("ROWER 1", 15));     //tests
        members.add(new Member("ROWER 2", 18));

        recyclerView = (RecyclerView) view.findViewById(R.id.rList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MemberRecyclerAdapter(members, view.getContext());
        recyclerView.setAdapter(mAdapter);

        /**********************************************
         *                  BUTTONS
         *********************************************/

        Button addMem = (Button) view.findViewById(R.id.addMember);
        addMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMemberPage = new Intent(getContext(), CreateNewMember.class);

                startActivity(addMemberPage);
            }
        });




        /*
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getContext(),"this works", Toast.LENGTH_LONG).show();            //THIS ISN'T SHOWING SO IT MUST NOT GET TO THIS STAGE
                Intent openMemberDisplay = new Intent(getContext(), MemberDisplay.class);
                startActivity(openMemberDisplay);
            }
        });

         */

    }
}
