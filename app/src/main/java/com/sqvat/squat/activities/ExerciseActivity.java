package com.sqvat.squat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.ListView;
import android.widget.TextView;

import com.sqvat.squat.adapters.ExerciseStepsAdapter;
import com.sqvat.squat.R;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Muscle;
import com.shamanland.fab.FloatingActionButton;

import java.util.List;



public class ExerciseActivity extends ActionBarActivity {
    int exerciseId;
    Exercise exercise;
    final static String LOG_TAG = "exercise activity";
    FloatingActionButton addToWorkout;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        Log.d(LOG_TAG, "workout id:  " + intent.getLongExtra("workoutId", -1));
        Log.v(LOG_TAG, "intent gotten");

        exerciseId = intent.getIntExtra("exerciseId", -1);
        Log.v(LOG_TAG, "exercise id is:" + exerciseId);

        exercise = Exercise.load(Exercise.class, exerciseId);
        getSupportActionBar().setTitle(exercise.name);

        if(exercise.getSteps().size() > 0) {
            ExerciseStepsAdapter adapter = new ExerciseStepsAdapter(this, exercise);
            ListView listView = (ListView) findViewById(R.id.instructions_lv);
            listView.setAdapter(adapter);
        }

        List<Muscle> musclesList = exercise.getMuscles();
        TextView muscles = (TextView) findViewById(R.id.muscles);
        if(musclesList.size() > 0) {
            //first muscle
            muscles.setText(musclesList.get(0).name);

            //other muscles
            for (int i = 1; i < musclesList.size(); i++) {
                muscles.append(", " + musclesList.get(i).name);
            }
        }

        addToWorkout = (FloatingActionButton) findViewById(R.id.add_to_workout);

        long workoutId = intent.getLongExtra("workoutId", -1);
        if(workoutId == -1){
            ((ViewManager)addToWorkout.getParent()).removeView(addToWorkout);
        }
        else {
            addToWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setClass(getApplicationContext(), ConfigSessionActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {

        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
