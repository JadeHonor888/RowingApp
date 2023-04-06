package com.example.rowingapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutPage.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutPage newInstance(String param1, String param2) {
        WorkoutPage fragment = new WorkoutPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_page, container, false);
    }

        //Declare variables out here so that it doesn't recreate it each time
        private RecyclerView recyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;

        private String filter = "all";

        ArrayList<Workout> workouts;

        private boolean filterClicked = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //CALL THIS TO GET WORKOUTS LIST
        GlobalVariable globalVariable = (GlobalVariable) getActivity().getApplication();
        workouts = globalVariable.getWorkouts();

        /**********************************************
         *              RECYCLERVIEW
         *********************************************/
        recyclerView = (RecyclerView) view.findViewById(R.id.rList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new WorkoutRecyclerAdapter(workouts, view.getContext());
        recyclerView.setAdapter(mAdapter);

        /**********************************************
         *                  BUTTONS
         *********************************************/
        Button addWork = (Button) view.findViewById(R.id.addWorkout);
        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addWorkoutPage = new Intent(getContext(), CreateNewWorkout.class);
                addWorkoutPage.putExtra("newWorkout", true);
                startActivity(addWorkoutPage);
            }
        });

        ImageView filterRow = (ImageView) view.findViewById(R.id.filter);
        ImageView filterArrow = (ImageView) view.findViewById(R.id.expandImage);

        LinearLayout sortRow1 = (LinearLayout) view.findViewById(R.id.sortRow1);

        LinearLayout allFilter = (LinearLayout) view.findViewById(R.id.allFilter);
        LinearLayout singleDistanceFilter = (LinearLayout) view.findViewById(R.id.singleDistanceFilter);
        LinearLayout singleTimeFilter = (LinearLayout) view.findViewById(R.id.singleTimeFilter);
        LinearLayout intervalFilter = (LinearLayout) view.findViewById(R.id.intervalFilter);

        //initially make the rows invisible
        sortRow1.setVisibility(View.GONE);

        filterArrow.setImageResource(R.drawable.expand_less_48px);

        SearchView searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Workout> filteredWorkouts = new ArrayList<Workout>();
                for (int i = 0; i < workouts.size(); i++)
                {
                    if (workouts.get(i).getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filteredWorkouts.add(workouts.get(i));
                    }
                }
                RecyclerView.Adapter searchAdapter = new WorkoutRecyclerAdapter(filteredWorkouts, view.getContext());
                recyclerView.setAdapter(searchAdapter);

                return false;
            }
        });

        //SHOW AND UNSHOW THE FILTER OPTIONS WHEN CLICKED
        filterRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterClicked = !filterClicked;
                if (filterClicked)  //if it's been clicked to display
                {
                    sortRow1.setVisibility(View.VISIBLE);

                    filterArrow.setImageResource(R.drawable.expand_more_48px);
                }
                else    //if it's been clicked again/ hasn't been clicked
                {
                    sortRow1.setVisibility(View.GONE);

                    filterArrow.setImageResource(R.drawable.expand_less_48px);
                }
            }
        });


        allFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(mAdapter);
            }
        });

        singleDistanceFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("Single Distance");
            }
        });

        singleTimeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("Single Time");
            }
        });

        intervalFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("Interval");
            }
        });
    }

    private void filterList(String f)
    {
        filter = f;

        ArrayList<Workout> filteredWorkouts = new ArrayList<Workout>();
        for (int i = 0; i < workouts.size(); i++)
        {
            if (workouts.get(i).getType().equals(f))
            {
                filteredWorkouts.add(workouts.get(i));
            }
        }
        RecyclerView.Adapter searchAdapter = new WorkoutRecyclerAdapter(filteredWorkouts, getContext());
        recyclerView.setAdapter(searchAdapter);

    }

}