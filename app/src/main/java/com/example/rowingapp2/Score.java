package com.example.rowingapp2;

public class Score
{
    private double split;
    private int distance;
    private double duration;
    private int stroke;
    private int id;


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
    public int getId() {return id;}

    //SETTERS
    public void setSplit(double s) {split = s;}
    public void setDistance(int d) {distance = d;}
    public void setDuration(double d) {duration = d;}
    public void setStroke(int s) {stroke = s;}
    public void setId(int i) {id = i;}

}
