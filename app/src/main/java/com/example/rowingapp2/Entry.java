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
    public Score getScoreFromId (int id)
    {
        for (int i = 0; i < scores.size(); i++)
        {
            if (scores.get(i).getScoreId() == id)
            {
                return scores.get(i);
            }
        }
        return null;
    }

    /***********************
     *      SETTERS
     **********************/
    public void setDate(int d) {date = d;}
    public void setEntryId(int i) {id = i;}

    public void editScore(int i, Score score)
    {
        scores.set(i, score);
    }

    public String entryToString()
    {
        String entry = "";
        for (int i = 0; i < scores.size(); i++)
        {
            entry = entry + "Score " + (i + 1) + ": isChecked: " + scores.get(i).getIsChecked() +
                    "\n Split: " + scores.get(i).getSplit() +
                    "\n Duration: " + scores.get(i).getDuration() +
                    "\n Distance: " + scores.get(i).getDistance() +
                    "\n Stroke: " + scores.get(i).getStroke() + "\n";
        }
        return entry;
    }

}
