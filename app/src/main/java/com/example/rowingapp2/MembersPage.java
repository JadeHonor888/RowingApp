package com.example.rowingapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MembersPage extends Fragment {

    //TEST: Removing the instance stuff

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

    //Declare variables out here so that it doesn't recreate it each time
    ArrayList<Member> members;
    MemberAdapter memberAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**********************************************
         *                  LISTVIEW
         *********************************************/
        members = new ArrayList<>();

        members.add(new Member("ROWER 1", 15));     //tests
        members.add(new Member("ROWER 2", 18));

        memberAdapter = new MemberAdapter(view.getContext(),R.id.list, members, R.color.white);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(memberAdapter);


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

                //Not working, don't know why ;; (listview doesn't know it's being clicked?)
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent openMemberDisplay = new Intent(getContext(), MemberDisplay.class);
                startActivity(openMemberDisplay);
            }
        });







    }
}
