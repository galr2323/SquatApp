package com.sqvat.squat;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sqvat.squat.data.Workout;

/**
 * Created by GAL on 9/13/2014.
 */
public class WorkoutFragment extends Fragment {

    Workout workout;
    //TODO: replace the constructor with Bundle staff
    public WorkoutFragment(Workout workout){
        super();
        this.workout = workout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
//        Bundle args = new Bundle();
//        args.put
//        this.se

        WorkoutAdapter adapter = new WorkoutAdapter(getActivity(), workout);
        ListView sessionsList = (ListView) view.findViewById(R.id.sessions_list);
        sessionsList.setAdapter(adapter);

        Button addSession = (Button) view.findViewById(R.id.add_session);
        addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Exercise exercise = new Exercise();
//                exercise.name = "Squat";
//                exercise.description = "y";
//                exercise.save();
//
//                Session s = new Session();
//                s.exercise = exercise;
//                s.rest = 90;
//                s.targetOrder = 1;
//                s.workout = workout;
//                s.save();
                Intent intent = new Intent(getActivity(), ChooseExerciseActivity.class);
                intent.putExtra("workoutId",workout.getId().intValue());
                Log.d("Workout fragment", String.valueOf(workout.getId()) + "--" + workout.name);
                startActivity(intent);
            }
        });
        return view;

    }
}
