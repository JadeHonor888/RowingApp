package com.example.rowingapp2;

public class Score
{
    private double split;
    private int distance;
    private double duration;
    private int stroke;

    //CONSTRUCTORS
    public Score(double s, int d, double t, int stroke)
    {
        split = s;
        distance = d;
        duration = t;
        this.stroke = stroke;
    }

    //GETTERS
    public double getSplit() {return split;}
    public int getDistance() {return distance;}
    public double getDuration() {return duration;}
    public int getStroke() {return stroke;}

    //SETTERS
    public void setSplit(double s) {split = s;}
    public void setDistance(int d) {distance = d;}
    public void setDuration(double d) {duration = d;}
    public void setStroke(int s) {stroke = s;}

}
