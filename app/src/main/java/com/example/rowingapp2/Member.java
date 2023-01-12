package com.example.rowingapp2;

public class Member {

    private String name;
    private int age;
    private int imageId;
    private String ageGroup;

    public Member(String n, int a)
    {
        name = n;
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
    }

    public Member(String n, int a, int i)
    {
        name = n;
        age = a;
        imageId = i;
            if (age < 15)
            { ageGroup = "U15"; }
            else if (age < 16)
            { ageGroup = "U16"; }
            else if (age < 17)
            { ageGroup = "U17"; }
            else
            { ageGroup = "U19"; }
    }

    // GETTERS
    public String getName() {return name;}
    public int getAge() {return age;}
    public int getImageId() {return imageId;}
    public String getAgeGroup() {return ageGroup;}

    //SETTERS
    public void setName(String n) {name = n;}
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

}
