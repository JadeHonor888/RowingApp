package com.example.rowingapp2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Member {

    //MEMBER ATTRIBUTES
    private int id;
    private String fName;
    private String lName;
    private int age;
    private int imageId;
    private String ageGroup;
    private boolean isFemale;
    private boolean isStarboard;
    private boolean isPort;

    //SCORE ATTRIBUTES
    private ArrayList<Score> memberScores;
    private int nextId;
    private double split;

    //SORTING
    public static Comparator<Member> memberComparatorAtoZ = new Comparator<Member>() {
        @Override
        public int compare(Member m1, Member m2) {
            return m1.getFName().compareTo(m2.getFName());
        }
    };

    public static Comparator<Member> memberComparatorU19toU16 = new Comparator<Member>() {
        @Override
        public int compare(Member m1, Member m2) {
            return m1.getAge() - m2.getAge();
        }
    };

    public static Comparator<Member> memberComparatorU16toU19 = new Comparator<Member>() {
        @Override
        public int compare(Member m1, Member m2) {
            return m2.getAge() - m1.getAge();
        }
    };

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
        memberScores = new ArrayList<Score>();
        nextId = 0;
        split = 0.0;
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
        memberScores = new ArrayList<Score>();
        nextId = 0;
        split = 0.0;
    }

    /***********************
     *      GETTERS
     **********************/
    public int getMemberId() {return id;}
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
    public double getMemberSplit() {return split;}

    /***********************
     *      SETTERS
     **********************/
    public void setfName(String fn) {fName = fn;}
    public void setlName(String ln) {lName = ln;}
    public void setAge(int a)
    {
        age = a;
        if (age < 15)           //Set age group when updating age
        { ageGroup = "U15"; }
        else if (age < 17)
        { ageGroup = "U17"; }
        else
        { ageGroup = "U19"; }
    }
    public void setImageId(int i) {imageId = i;}
    public void setFemale(boolean f) {isFemale = f;}
    public void setPort(boolean p) {isPort = p;}
    public void setStarboard(boolean s) {isStarboard = s;}
    public String memberToString()
    {
        String member;
        member = "Name: " + fName + " " + lName +
                "\nAge: " + age + " Age Group: " + ageGroup +
                "\nSide: " + getStarboardString() + " " + getPortString() +
                "\nGender: " + getGenderString() +
                "\nId: " + id +
                "\nScores: "  + memberScores.size();
        return member;
    }

    /***********************
     *    SCORE METHODS
     **********************/
    public void addScore(Score score)
    {
        score.setScoreId(nextId++);
        memberScores.add(score);
        split = split + score.getSplit();
    }
    public void removeScore(int id)
    {
        for (int i = 0; i < memberScores.size(); i++)
        {
            if (memberScores.get(i).getScoreId() == id)
            {
                split = split - memberScores.get(i).getSplit();     //remove the split from the average
                memberScores.remove(memberScores.get(i));
                break;
            }
        }
    }
    public void editScore(Score score, int i) { memberScores.set(i, score); }
    public ArrayList<Score> getMemberScores() { return memberScores; }
    public Score getScoreFromId(int id)
    {
        for (int i = 0; i < memberScores.size(); i++)
        {
            if (memberScores.get(i).getScoreId() == id)
            {
                return memberScores.get(i);
            }
        }
        return null;
    }

}
