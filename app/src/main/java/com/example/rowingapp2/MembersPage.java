package com.example.rowingapp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MembersPage extends Fragment {

    public MembersPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Member> members = new ArrayList<>();

        members.add(new Member("ROWER 1", 15));
        members.add(new Member("ROWER 2", 18));

        MemberAdapter memberAdapter = new MemberAdapter(getActivity(),R.id.list,members,R.color.white);

        ListView listView = (ListView) getView().findViewById(R.id.list);

        listView.setAdapter(memberAdapter);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_members_page, container, false);

    }

}