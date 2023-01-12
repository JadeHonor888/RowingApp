package com.example.rowingapp2;

public class Member {

    private String name;
    private int age;
    private int imageId;

    public Member(String n, int a)
    {
        name = n;
        age = a;
        imageId = -1;
    }

    public Member(String n, int a, int i)
    {
        name = n;
        age = a;
        imageId = i;
    }

    // GETTERS
    public String getName() {return name;}
    public int getAge() {return age;}
    public int getImageId() {return imageId;}

    //SETTERS
    public void setName(String n) {name = n;}
    public void setAge(int a) {age = a;}
    public void setImageId(int i) {imageId = i;}

}
