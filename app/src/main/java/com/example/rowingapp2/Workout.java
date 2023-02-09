package com.example.rowingapp2;

import java.util.ArrayList;

public class Workout {

    //WORKOUT ATTRIBUTES
    private int id;
    private String name;
    private String desc;
    private String type;

    //SCORE/ENTRY ATTRIBUTES
    private ArrayList<ArrayList<Score>> entries;

    public Workout(String n, String d, String t, int i)
    {
        name = n;
        desc = d;
        type = t;
        id = i;
        entries = new ArrayList<ArrayList<Score>>();            //list of list of scores
    }

    /***********************
     *      GETTERS
     **********************/
    public int getId() {return id;}
    public String getName() {return name;}
    public String getDesc() {return desc;}
    public String getType() {return type;}


    /***********************
     *      SETTERS
     **********************/
    public void setName(String n) {name = n;}
    public void setDesc(String d) {desc = d;}
    public void setType(String t) {type = t;}


    /***********************
     *    SCORE METHODS
     **********************/
    public void newEntry()
    {
        ArrayList<Score> newEntry = new ArrayList<Score>();
        entries.add(newEntry);
    }

}
