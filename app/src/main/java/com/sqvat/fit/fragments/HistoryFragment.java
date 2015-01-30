package com.sqvat.fit.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sqvat.fit.R;
import com.sqvat.fit.activities.DetailedHistoryAct;
import com.sqvat.fit.adapters.HistoryAdapter;
import com.sqvat.fit.data.CompletedWorkout;

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

//        ExerciseStep step = new ExerciseStep();
//        step.exercise = Exercise.load(Exercise.class, 1);
//        step.order = 1;
//        step.description = "stand with the barbell on your shoulders";
//        step.save();
//
//        ExerciseStep step2 = new ExerciseStep();
//        step.exercise = Exercise.load(Exercise.class, 1);
//        step.order = 2;
//        step.description = "squat!";
//        step.save();

        List<CompletedWorkout> history = CompletedWorkout.getAll();

        final HistoryAdapter adapter = new HistoryAdapter(getActivity(), history);
        ListView listView = (ListView) view.findViewById(R.id.history_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailedHistoryAct.class);
                intent.putExtra("completedWorkoutId",adapter.getItem(i).getId());
                startActivity(intent);

            }
        });

        return view;
    }
}
