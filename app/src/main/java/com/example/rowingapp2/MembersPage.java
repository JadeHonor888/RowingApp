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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MembersPage() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MembersPage.
     */
    // TODO: Rename and change types and number of parameters
    public static MembersPage newInstance(String param1, String param2) {
        MembersPage fragment = new MembersPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


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
    ArrayList<Member> members = new ArrayList<>();    //REMINDER: **YOU NEED THIS LINE OR THINGS BREAK!!**
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //member variables for creating new members
    String name;
    int age;
    int imageId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        members.add(new Member("ROWER", "1", 15, true, true, false));     //tests



        Intent i = this.getActivity().getIntent();      //getActivity might not be a good solution here
        if (i != null)
        {
            createNewMember(i.getStringExtra("fName"),i.getStringExtra("lName"),i.getIntExtra("age", -1),
                    i.getBooleanExtra("isFemale", true),i.getBooleanExtra("isPort", false), i.getBooleanExtra("isStarboard", false));
        }

        /*
        Bundle memberInfo = getArguments();
        createNewMember(memberInfo.getString("fName"),memberInfo.getString("lName"),memberInfo.getInt("age", -1),
                memberInfo.getBoolean("isFemale", true),memberInfo.getBoolean("isPort", false), memberInfo.getBoolean("isStarboard", false));

         */
        /**********************************************
         *              RECYCLERVIEW
         *********************************************/
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

    }


    public void createNewMember(String fn, String ln, int a, boolean f, boolean s, boolean p)
    {
        members.add(new Member(fn, ln, a, f, s, p));
    }

}
