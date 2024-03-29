package com.example.rowingapp2;

import java.util.ArrayList;
import java.util.Comparator;

public class Workout {

    //WORKOUT ATTRIBUTES
    private int id;
    private String name;
    private String desc;
    private String type;

    //SCORE/ENTRY ATTRIBUTES
    private ArrayList<Entry> entries;
    private int nextEntryId;    //don't make this static! (idk why)

    public Workout(String n, String d, String t, int i)
    {
        name = n;
        desc = d;
        type = t;
        id = i;
        nextEntryId = 0;
        entries = new ArrayList<Entry>();
    }

    /***********************
     *      GETTERS
     **********************/
    public int getWorkoutId() {return id;}
    public String getName() {return name;}
    public String getDesc() {return desc;}
    public String getType() {return type;}
    public ArrayList<Entry> getEntries() {return entries;}
    public Entry getEntryfromId(int id)
    {
        for (int i = 0; i < entries.size(); i++)
        {
            if (entries.get(i).getEntryId() == id)
            {
                return entries.get(i);
            }
        }
        return null;
    }


    /***********************
     *      SETTERS
     **********************/
    public void setName(String n) {name = n;}
    public void setDesc(String d) {desc = d;}
    public void setType(String t) {type = t;}
    public String workoutToString()
    {
        String workout;
        workout = "Name: " + name +
                "\nDescription: " + desc +
                "\nType: " + type +
                "\nEntries: " + entries.size() +
                "\nId: " + id +
                "\nNextEntryId: " + nextEntryId;
        return workout;
    }


    /***********************
     *    ENTRY METHODS
     **********************/
    public Entry newEntry(int numOfMembers)
    {
        Entry newEntry = new Entry(nextEntryId++,numOfMembers);
        entries.add(newEntry);
        return newEntry;
    }

}
