package com.example.rowingapp2;

import java.util.ArrayList;

public class Entry {

    private ArrayList<Score> scores;
    private int date;
    private int id;

    /***********************
     *    CONSTRUCTORS
     **********************/
    public Entry()
    {
        scores = new ArrayList<>();
    }

    public Entry(int numOfMembers)
    {
        scores = new ArrayList<>();
        for (int i = 0; i < numOfMembers; i++)
        {
            scores.add(new Score(i));
        }
    }

    /***********************
     *      GETTERS
     **********************/
    public ArrayList<Score> getScores() {return scores;}
    public int getDate() {return date;}
    public int getEntryId() {return id;}

    /***********************
     *      SETTERS
     **********************/
    public void setDate(int d) {date = d;}
    public void setEntryId(int i) {id = i;}

    public void editScore(int i, Score score)
    {
        scores.set(i, score);
    }

}
