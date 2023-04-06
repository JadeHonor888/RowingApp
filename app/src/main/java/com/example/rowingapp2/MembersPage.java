package com.example.rowingapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.google.mlkit.vision.text.Text;

import java.util.ArrayList;
import java.util.Collections;

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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<Member> members;

    private boolean filterClicked = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //CALL THIS TO GET MEMBERS LIST
        GlobalVariable globalVariable = (GlobalVariable) getActivity().getApplication();
        members = globalVariable.getMembers();

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
                Intent i = new Intent(getContext(), CreateNewMember.class);
                //i.putExtra("check", true);
                startActivity(i);
            }
        });

        ImageView filterRow = (ImageView) view.findViewById(R.id.filter);
        ImageView filterArrow = (ImageView) view.findViewById(R.id.expandImage);

        LinearLayout sortRow1 = (LinearLayout) view.findViewById(R.id.sortRow1);
        LinearLayout sortRow2 = (LinearLayout) view.findViewById(R.id.sortRow2);

        LinearLayout allFilter = (LinearLayout) view.findViewById(R.id.allFilter);
        LinearLayout u15Filter = (LinearLayout) view.findViewById(R.id.u15Filter);
        LinearLayout u17Filter = (LinearLayout) view.findViewById(R.id.u17Filter);
        LinearLayout u19Filter = (LinearLayout) view.findViewById(R.id.u19Filter);
        LinearLayout femaleFilter = (LinearLayout) view.findViewById(R.id.femaleFilter);
        LinearLayout maleFilter = (LinearLayout) view.findViewById(R.id.maleFilter);
        LinearLayout portFilter = (LinearLayout) view.findViewById(R.id.portFilter);
        LinearLayout starboardFilter = (LinearLayout) view.findViewById(R.id.starboardFilter);

        //initially make the rows invisible
        sortRow1.setVisibility(View.GONE);
        sortRow2.setVisibility(View.GONE);

        filterArrow.setImageResource(R.drawable.expand_less_48px);

        SearchView searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //when the text is changed
                //create a new arrayList with only the members that have those characters
                ArrayList<Member> filteredMembers = new ArrayList<Member>();
                for (int i = 0; i < members.size(); i++)
                {
                    if (members.get(i).getFName().toLowerCase().contains(newText.toLowerCase()))        //if the member contains whatever they're searching for, add it to the list of shown members
                    {
                        filteredMembers.add(members.get(i));
                    }
                }
                RecyclerView.Adapter searchAdapter = new MemberRecyclerAdapter(filteredMembers, view.getContext());
                recyclerView.setAdapter(searchAdapter);

                return false;
            }
        });

        //SHOW AND UNSHOW THE FILTER OPTIONS WHEN CLICKED
        filterRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterClicked = !filterClicked;
                if (filterClicked)  //if it's been clicked to display
                {
                    sortRow1.setVisibility(View.VISIBLE);
                    sortRow2.setVisibility(View.VISIBLE);

                    filterArrow.setImageResource(R.drawable.expand_more_48px);
                }
                else    //if it's been clicked again/ hasn't been clicked
                {
                    sortRow1.setVisibility(View.GONE);
                    sortRow2.setVisibility(View.GONE);

                    filterArrow.setImageResource(R.drawable.expand_less_48px);
                }
            }
        });

        allFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(mAdapter);
            }
        });

        u15Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListAge("U15");
            }
        });

        u17Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListAge("U17");
            }
        });

        u19Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListAge("U19");
            }
        });

        femaleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListGender("Female");
            }
        });

        maleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListGender("Male");
            }
        });

        portFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListSide("Port");
            }
        });

        starboardFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListSide("Starboard");
            }
        });
    }

    private void filterListAge(String f)
    {

        ArrayList<Member> filteredMembers = new ArrayList<Member>();
        for (int i = 0; i < members.size(); i++)
        {
            //if the member is u15/u17/u19
            if (members.get(i).getAgeGroup().equals(f))
            {
                filteredMembers.add(members.get(i));
            }
        }
        RecyclerView.Adapter searchAdapter = new MemberRecyclerAdapter(filteredMembers, getContext());
        recyclerView.setAdapter(searchAdapter);
    }

    private void filterListGender(String f)
    {

        ArrayList<Member> filteredMembers = new ArrayList<Member>();
        for (int i = 0; i < members.size(); i++)
        {
            //if the member is female
            if(members.get(i).getGenderString().equals(f))
            {
                filteredMembers.add(members.get(i));
            }

        }
        RecyclerView.Adapter searchAdapter = new MemberRecyclerAdapter(filteredMembers, getContext());
        recyclerView.setAdapter(searchAdapter);
    }

    private void filterListSide(String f)
    {

        ArrayList<Member> filteredMembers = new ArrayList<Member>();
        for (int i = 0; i < members.size(); i++)
        {
            //if the member is starboard
            if (members.get(i).getStarboardString().equals(f))
            {
                filteredMembers.add(members.get(i));
            }
            //if the member is port
            else if (members.get(i).getPortString().equals(f))
            {
                filteredMembers.add(members.get(i));
            }

        }
        RecyclerView.Adapter searchAdapter = new MemberRecyclerAdapter(filteredMembers, getContext());
        recyclerView.setAdapter(searchAdapter);
    }

}
