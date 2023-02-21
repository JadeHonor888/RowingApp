package com.example.rowingapp2;

public class Score
{
    private double split;
    private int distance;
    private double duration;
    private int stroke;
    private int id;

    private String memberName;
    private int memberImageId;
    private String workoutName;
    private String workoutDesc;
    private int date;
    private boolean isChecked;

    //CONSTRUCTORS
    public Score (int i)
    {
        split = 0;
        distance = 0;
        duration = 0;
        stroke = 0;
        id = i;
    }

    //GETTERS
    public double getSplit() {return split;}
    public int getDistance() {return distance;}
    public double getDuration() {return duration;}
    public int getStroke() {return stroke;}
    public int getScoreId() {return id;}
    public String getMemberName() {return memberName;}
    public int getMemberImageId() {return memberImageId;}
    public String getWorkoutName() {return workoutName;}
    public String getWorkoutDesc() {return workoutDesc;}
    public boolean getIsChecked() {return isChecked;}

    //SETTERS
    public void setSplit(double s) {split = s;}
    public void setDistance(int d) {distance = d;}
    public void setDuration(double d) {duration = d;}
    public void setStroke(int s) {stroke = s;}
    public void setScoreId(int i) {id = i;}
    public void setMemberName(String n) {memberName = n;}
    public void setMemberImageId(int i) {memberImageId = i;}
    public void setWorkoutName(String n) {workoutName = n;}
    public void setWorkoutDesc(String d) {workoutDesc = d;}
    public void setIsChecked(boolean c) {isChecked = c;}

}
