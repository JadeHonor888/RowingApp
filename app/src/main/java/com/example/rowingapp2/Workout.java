package com.example.rowingapp2;

public class Workout {

    private int id;
    private String name;
    private String desc;
    private String type;

    public Workout(String n, String d, String t, int i)
    {
        name = n;
        desc = d;
        type = t;
        id = i;
    }

    //GETTERS
    public int getId() {return id;}
    public String getName() {return name;}
    public String getDesc() {return desc;}
    public String getType() {return type;}


    //SETTERS
    public void setName(String n) {name = n;}
    public void setDesc(String d) {desc = d;}
    public void setType(String t) {type = t;}

}
