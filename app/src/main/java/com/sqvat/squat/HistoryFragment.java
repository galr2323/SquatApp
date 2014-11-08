package com.sqvat.squat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.ExerciseStep;
import com.sqvat.squat.data.Workout;

import java.util.List;

/**
 * Created by GAL on 9/13/2014.
 */
public class HistoryFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        ExerciseStep step = new ExerciseStep();
        step.exercise = Exercise.load(Exercise.class, 1);
        step.order = 1;
        step.description = "stand with the barbell on your shoulders";
        step.save();

        ExerciseStep step2 = new ExerciseStep();
        step.exercise = Exercise.load(Exercise.class, 1);
        step.order = 2;
        step.description = "squat!";
        step.save();

        List<CompletedWorkout> history = CompletedWorkout.getAll();

        HistoryAdapter adapter = new HistoryAdapter(getActivity(), history);
        ListView listView = (ListView) view.findViewById(R.id.history_listview);
        listView.setAdapter(adapter);

        return view;
    }
}
