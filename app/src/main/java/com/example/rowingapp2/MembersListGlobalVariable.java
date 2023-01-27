package com.example.rowingapp2;

import android.app.Application;

import java.util.ArrayList;

public class MembersListGlobalVariable extends Application {

    private static ArrayList<Member> members = new ArrayList<Member>();
    private static int nextId;

    public MembersListGlobalVariable() {            //CONSTRUCTOR
    }

    public void createNewMember(String fn, String ln, int a, boolean f, boolean s, boolean p)
    {
        members.add(new Member(fn, ln, a, f, s, p));
    }

    //GETTERS
    public ArrayList<Member> getMembers() {return members;}
    public int getNextId() {return nextId;}
}
