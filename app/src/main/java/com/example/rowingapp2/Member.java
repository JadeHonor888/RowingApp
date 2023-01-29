package com.example.rowingapp2;

import java.io.Serializable;
import java.util.ArrayList;

public class Member {

    private int id;
    private String fName;
    private String lName;
    private int age;
    private int imageId;
    private String ageGroup;
    private boolean isFemale;
    private boolean isStarboard;
    private boolean isPort;

    // FOR WORKOUT SECTION
    private double split;
    private ArrayList<Score> scores;

    public Member(String fn,String ln, int a, boolean f, boolean s, boolean p, int i)
    {
        id = i;
        fName = fn;
        lName = ln;
        age = a;
        imageId = -1;
            if (age < 15)
            { ageGroup = "U15"; }
            else if (age < 16)
            { ageGroup = "U16"; }
            else if (age < 17)
            { ageGroup = "U17"; }
            else
            { ageGroup = "U19"; }
        isFemale = f;
        isPort = p;
        isStarboard = s;
    }

    public Member(String fn, String ln, int a, boolean f, boolean s, boolean p, int im, int i)
    {
        id = i;
        fName = fn;
        lName = ln;
        age = a;
        imageId = im;
            if (age < 15)
            { ageGroup = "U15"; }
            else if (age < 16)
            { ageGroup = "U16"; }
            else if (age < 17)
            { ageGroup = "U17"; }
            else
            { ageGroup = "U19"; }
        isFemale = f;
        isPort = p;
        isStarboard = s;
    }

    // GETTERS
    public int getId() {return id;}
    public String getFName() {return fName;}
    public String getLName() {return lName;}
    public int getAge() {return age;}
    public int getImageId() {return imageId;}
    public String getAgeGroup() {return ageGroup;}
    public String getGenderString()
    {
        if(isFemale) {return "Female";}
        else {return "Male";}
    }
    public boolean getGender() {return isFemale;}
    public String getPortString()
    {
        if(isPort) {return "Port";}
        else {return "";}
    }
    public boolean getPort() {return isPort;}
    public String getStarboardString()
    {
        if(isStarboard) {return "Starboard";}
        else {return "";}
    }
    public boolean getStarboard() {return isStarboard;}

    //SETTERS
    public void setfName(String fn) {fName = fn;}
    public void setlName(String ln) {lName = ln;}
    public void setAge(int a)
    {
        age = a;
        if (age < 15)           //Set age group when updating age
        { ageGroup = "U15"; }
        else if (age < 16)
        { ageGroup = "U16"; }
        else if (age < 17)
        { ageGroup = "U17"; }
        else
        { ageGroup = "U19"; }
    }
    public void setImageId(int i) {imageId = i;}
    public void setFemale(boolean f) {isFemale = f;}
    public void setPort(boolean p) {isPort = p;}
    public void setStarboard(boolean s) {isStarboard = s;}

}
