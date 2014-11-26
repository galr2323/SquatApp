package com.sqvat.squat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sqvat.squat.data.Workout;

/**
 * Created by GAL on 11/11/2014.
 */
public class AddExerciseButton extends Button {
    private long workoutId;
    public AddExerciseButton(Context context, long WorkoutId) {
        super(context);
        this.workoutId = WorkoutId;

        this.setText("+ ADD EXERCISE");

        this.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(getContext(), ChooseExerciseActivity.class);
                intent.putExtra("workoutId", workoutId);
                Log.d("Workout fragment", String.valueOf(workoutId) + "--");
                getContext().startActivity(intent);
            }
        });


    }
}
