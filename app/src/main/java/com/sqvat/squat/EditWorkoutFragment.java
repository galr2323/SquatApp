package com.sqvat.squat;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqvat.squat.data.Workout;



public class EditWorkoutFragment extends Fragment {
    private long workoutId;
    private Workout workout;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param workoutId Parameter 1.
     * @return A new instance of fragment EditWorkoutFragment.
     */

    public static EditWorkoutFragment newInstance(long workoutId) {
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        Bundle args = new Bundle();
        args.putLong("workoutId", workoutId);
        fragment.setArguments(args);
        return fragment;
    }
    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workoutId = getArguments().getLong("workoutId");
            workout = Workout.load(Workout.class, workoutId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);
    }

}
