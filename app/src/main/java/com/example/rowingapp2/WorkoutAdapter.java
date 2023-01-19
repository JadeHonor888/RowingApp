package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class WorkoutAdapter extends ArrayAdapter<Workout>
{
        int background;

public WorkoutAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects, int i) {
        super(context, resource, objects);
        background = i;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent)
        {
        /***************************************************
         *              STEP 1: GET THE LAYOUT
         ***************************************************/

        View layoutOfView = convertView;
        if (layoutOfView == null)
        {
        layoutOfView = LayoutInflater.from(getContext()).inflate(R.layout.workout_item,parent,false);
        }

        /***************************************************
         *              STEP 2: GET THE DATA
         ***************************************************/

        Workout currentWorkout = getItem(position);

        /***************************************************
         *                STEP 3: MATCH EM
         ***************************************************/
        RelativeLayout layout = (RelativeLayout) layoutOfView.findViewById(R.id.relLayout);
        layout.setBackgroundResource(background);

        TextView name = (TextView) layoutOfView.findViewById(R.id.name);
        name.setText(currentWorkout.getName());

        TextView desc = (TextView) layoutOfView.findViewById(R.id.desc);
        desc.setText(currentWorkout.getDesc());

        /***************************************************
         *                STEP 4: RETURN!
         ***************************************************/
        return layoutOfView;
        }
}
