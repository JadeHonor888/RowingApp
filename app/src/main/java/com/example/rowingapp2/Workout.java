package com.example.rowingapp2;

public class Workout {

    private String name;
    private String desc;

    public Workout(String n, String d)
    {
        name = n;
        desc = d;

    }

    //GETTERS
    public String getName() {return name;}
    public String getDesc() {return desc;}

    //SETTERS
    public void setName(String n) {name = n;}
    public void setDesc(String d) {desc = d;}

}
