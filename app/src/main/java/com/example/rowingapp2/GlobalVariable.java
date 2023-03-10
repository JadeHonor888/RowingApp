package com.example.rowingapp2;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

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
        Member newMember = new Member(fn, ln, a, f, s, p, memberNextId++);
        members.add(newMember);
        saveMemberData();       //Shared Pref

        Log.d("member", "Member Created: " + newMember.memberToString());
        Log.d("member", "memberNextId: " + memberNextId);
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
        Workout workout = new Workout(n, d, t, workoutNextId++);
        workouts.add(workout);
        saveWorkoutData();      //Shared pref

        Log.d("workout", "Workout Created: " + workout.workoutToString());
        Log.d("workout", "workoutNextIt: " + workoutNextId);
    }
    public void removeWorkout(int id)
    {
        for (int i = 0; i <= workouts.size(); i++)
        {
            if (workouts.get(i).getWorkoutId() == id)
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
            if (workouts.get(i).getWorkoutId() == id)
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
    public void editMember(int id, Member member)
    {
        Log.d("member", "Member passed in: " + member.memberToString());
        for (int i = 0; i < members.size(); i++)
        {
            if (members.get(i).getMemberId() == id)     //if the ids match, they should be the same
            {
                Log.d("member", "Member being replaced: " + members.get(i).memberToString());
                members.set(i, member);
                break;
            }
        }
    }
    public void setWorkouts(ArrayList<Workout> workout) {
        workouts = workout;
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
        myEdit.putString("memberData",json);
        myEdit.putInt("memberNextId", memberNextId);
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
        memberNextId = sharedPreferences.getInt("memberNextId", 0);
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
        myEdit.putInt("workoutNextId", workoutNextId);
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
        workoutNextId = sharedPreferences.getInt("workoutNextId", 0);
    }
}
