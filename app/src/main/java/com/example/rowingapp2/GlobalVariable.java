package com.example.rowingapp2;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GlobalVariable extends Application {

    private static ArrayList<Member> members;
    private static int memberNextId;
    private static ArrayList<Workout> workouts;
    private static int workoutNextId;

    public GlobalVariable() {            //CONSTRUCTOR
    }

    public void createNewMember(String fn, String ln, int a, boolean f, boolean s, boolean p) {
        Member newMember = new Member(fn, ln, a, f, s, p, memberNextId);
        members.add(newMember);
        memberNextId++;
        saveMemberData();       //Shared Pref
    }
    public void removeMember(int id)
    {

        for (int i = 0; i <= members.size(); i++)
        {
            if (members.get(i).getMemberId() == id)
            {
                members.remove(members.get(i));     //don't use remove(i) it doens't work (idk why)
                saveMemberData();
                break;
            }
        }
    }

    public void createNewWorkout(String n, String d, String t) {
        Workout workout = new Workout(n, d, t, workoutNextId);
        workouts.add(workout);
        workoutNextId++;
        saveWorkoutData();      //Shared pref
    }
    public void removeWorkout(int id)
    {

        for (int i = 0; i <= workouts.size(); i++)
        {
            if (workouts.get(i).getId() == id)
            {
                workouts.remove(workouts.get(i));
                saveWorkoutData();
                break;
            }
        }
    }

    /**
     *      GETTERS
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    public int getMemberNextId() {
        return memberNextId;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public int getNextId() {
        return workoutNextId;
    }

    public Workout getWorkoutFromId(int id)             //find the workout with this id
    {
        for (int i = 0; i < workouts.size(); i++)
        {
            if (workouts.get(i).getId() == id)
            {
                return workouts.get(i);
            }
        }
        return null;    //this shouldn't happen
    }

    public Member getMemberFromId(int id)
    {
        for (int i = 0; i < members.size(); i++)
        {
            if (members.get(i).getMemberId() == id)         //if the member has the right id
            {
                return members.get(i);
            }
        }
        return null;
    }

    /**
     *      SETTERS
     */
    public void setMembers(ArrayList<Member> m) {
        members = m;
    }

    public void editMember(Member member, int id) {
        members.set(id, member);
        saveMemberData();       //Shared pref
    }

    public void setWorkouts(ArrayList<Workout> workout) {
        workouts = workout;
    }

    public void editWorkout(Workout workout, int id) {
        workouts.set(id, workout);
        saveWorkoutData();          //Shared pref
    }


    /**
     *      SHARED PREFERENCES
     */
    public void saveMemberData()
    {
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MemberData", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        //Translate arrayList into string
        Gson gson = new Gson();
        String json = gson.toJson(members);
        myEdit.putString("memberData",json);                //TODO: Look into whether we need to save memberNextId or not, it might set it to zero each time
        myEdit.apply();
    }

    public void loadMemberData()
    {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MemberData", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("memberData", null);
        Type type = new TypeToken<ArrayList<Member>>(){
        }.getType();
        members = gson.fromJson(json, type);
        if(members == null)                     //CREATE NEW IF FIRST
        {
            members = new ArrayList<Member>();
            memberNextId = 0;
        }
    }

    public void saveWorkoutData()
    {
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("WorkoutData", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        //Translate arrayList into string
        Gson gson = new Gson();
        String json = gson.toJson(workouts);
        myEdit.putString("workoutData",json);
        myEdit.apply();
    }

    public void loadWorkoutData()
    {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("WorkoutData", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("workoutData", null);
        Type type = new TypeToken<ArrayList<Workout>>(){
        }.getType();
        workouts = gson.fromJson(json, type);
        if(workouts == null)                        //CREATE NEW IF FIRST
        {
            workouts = new ArrayList<Workout>();
            workoutNextId = 0;
        }
    }
}
