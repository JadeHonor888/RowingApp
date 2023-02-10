package com.example.rowingapp2;

import java.util.ArrayList;

public class Workout {

    //WORKOUT ATTRIBUTES
    private int id;
    private String name;
    private String desc;
    private String type;

    //SCORE/ENTRY ATTRIBUTES
    private ArrayList<Entry> entries;

    public Workout(String n, String d, String t, int i)
    {
        name = n;
        desc = d;
        type = t;
        id = i;
        entries = new ArrayList<Entry>();
    }
    public Workout () {}

    /***********************
     *      GETTERS
     **********************/
    public int getId() {return id;}
    public String getName() {return name;}
    public String getDesc() {return desc;}
    public String getType() {return type;}
    public ArrayList<Entry> getEntries() {return entries;}


    /***********************
     *      SETTERS
     **********************/
    public void setName(String n) {name = n;}
    public void setDesc(String d) {desc = d;}
    public void setType(String t) {type = t;}


    /***********************
     *    ENTRY METHODS
     **********************/
    public void newEntry(int numOfMembers)
    {
        entries.add(new Entry(numOfMembers));
    }

}
