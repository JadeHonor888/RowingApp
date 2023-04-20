package com.example.rowingapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomScoreAdapter extends ArrayAdapter<Score> {

    public CustomScoreAdapter(@NonNull Context context, int resource, ArrayList<Score> scores) {
        super(context, resource, scores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_score_item, parent, false);
        }

        Score currScore = getItem(position);

        TextView memberName = (TextView) convertView.findViewById(R.id.memberName);
        memberName.setText(currScore.getMemberName());

        TextView score = (TextView) convertView.findViewById(R.id.score);
        String s = "Duration: " + currScore.getDuration();
        score.setText(s);

        return convertView;
    }

    public void updateAdapter()
    {

    }
}
