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
    private static ArrayList<Score> scores;

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

        for (int i = 0; i <= memberNextId; i++)
        {
            if (members.get(i).getId() == id)
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

        for (int i = 0; i <= workoutNextId; i++)
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

    public ArrayList<Score> getScores() {return scores;}

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

    public void editScore(Score score, int id) {
        scores.set(id, score);
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
