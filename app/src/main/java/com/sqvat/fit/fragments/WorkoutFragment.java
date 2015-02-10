package com.sqvat.fit.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.shamanland.fab.FloatingActionButton;
import com.sqvat.fit.R;
import com.sqvat.fit.activities.TrackWorkoutAct;
import com.sqvat.fit.adapters.WorkoutAdapter;
import com.sqvat.fit.data.Workout;
import com.sqvat.fit.events.WorkoutEdited;

import de.greenrobot.event.EventBus;

/**
 * Created by GAL on 9/13/2014.
 */
public class WorkoutFragment extends Fragment {

    protected long workoutId;
    protected Workout workout;
    protected WorkoutAdapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param workoutId Parameter 1.
     * @return A new instance of fragment EditWorkoutFragment.
     */

    public static WorkoutFragment newInstance(long workoutId, boolean editMode) {
        WorkoutFragment fragment = null;
        if (editMode){
            fragment = new EditWorkoutFragment();
        }
        else {
            fragment = new WorkoutFragment();
        }
        Bundle args = new Bundle();
        args.putLong("workoutId", workoutId);
        fragment.setArguments(args);
        return fragment;
    }
    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public String toString() {
        return String.valueOf(workoutId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            workoutId = getArguments().getLong("workoutId");
            workout = Workout.load(Workout.class, workoutId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        //TODO: change the workout now to add exercise in case workout is empty
//        Bundle args = new Bundle();
//        args.put
//        this.se

        adapter = new WorkoutAdapter(getActivity(), workout);
        ListView sessionsList = (ListView) view.findViewById(R.id.sessions_list);
        sessionsList.setAdapter(adapter);

        TextView name = (TextView) view.findViewById(R.id.workout_name);
        name.append(workout.name);

        TextView info = (TextView) view.findViewById(R.id.workout_info);
        //TODO: change to use use of Workout.toString()
        info.setText(workout.toString());


        FloatingActionButton workoutNow = (FloatingActionButton) view.findViewById(R.id.workout_fab);
        workoutNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrackWorkoutAct.class);
                Log.d("Workout fragment", String.valueOf(workout.getId()));
                intent.putExtra("workoutId", workout.getId());
                startActivity(intent);
            }
        });

//        Button addSession = (Button) view.findViewById(R.id.choose_exercise);
//        addSession.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Exercise exercise = new Exercise();
////                exercise.name = "Squat";
////                exercise.description = "y";
////                exercise.save();
////
////                Session s = new Session();
////                s.exercise = exercise;
////                s.rest = 90;
////                s.targetOrder = 1;
////                s.workout = workout;
////                s.save();
//                Intent intent = new Intent(getActivity(), ChooseExerciseActivity.class);
//                intent.putExtra("workoutId",workout.getId().intValue());
//                Log.d("Workout fragment", String.valueOf(workout.getId()) + "--" + workout.name);
//                startActivity(intent);
//         }
//        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.update();
    }

//    public void onEvent(WorkoutEdited event){
//        adapter = new WorkoutAdapter(getActivity(), workout);
//        adapter.update();
//        EventBus.getDefault().removeStickyEvent(event);
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().registerSticky(this);
//    }
//
//    @Override
//    public void onStop() {
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }


}
