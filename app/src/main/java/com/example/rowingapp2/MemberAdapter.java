package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MemberAdapter extends ArrayAdapter<Member>
{
        int background;

public MemberAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects, int i) {
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
                LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layoutOfView = layoutInflater.inflate(R.layout.member_item,parent,false);
        }

        /***************************************************
         *              STEP 2: GET THE DATA
         ***************************************************/

        Member currentMember = getItem(position);

        /***************************************************
         *                STEP 3: MATCH EM
         ***************************************************/
        RelativeLayout layout = (RelativeLayout) layoutOfView.findViewById(R.id.relLayout);
        layout.setBackgroundResource(background);


        // CHECK IF THERE'S AN IMAGE, OTHERWISE SET IT TO NOTHING
        ImageView imageView = (ImageView) layoutOfView.findViewById(R.id.image);
        if (currentMember.getImageId()==-1)
        { imageView.setVisibility(View.GONE); }
        else
        { imageView.setImageResource(currentMember.getImageId()); }



        TextView name = (TextView) layoutOfView.findViewById(R.id.name);
        name.setText(currentMember.getName());

        TextView age = (TextView) layoutOfView.findViewById(R.id.age);
        age.setText(String.valueOf(currentMember.getAge()));

        TextView ageGroup = (TextView) layoutOfView.findViewById(R.id.ageGroup);
        ageGroup.setText(currentMember.getAgeGroup());


        /***************************************************
         *                STEP 4: RETURN!
         ***************************************************/
        return layoutOfView;
        }
}
