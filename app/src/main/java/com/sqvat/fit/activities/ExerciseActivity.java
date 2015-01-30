package com.sqvat.fit.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamanland.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.sqvat.fit.R;
import com.sqvat.fit.data.Exercise;



public class ExerciseActivity extends ActionBarActivity {
    long exerciseId;
    Exercise exercise;
    final static String LOG_TAG = "exercise activity";
    FloatingActionButton playVideo;
    FloatingActionButton addToWorkout;
    ImageView toolbarImg;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.exercise_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));

        final Intent intent = getIntent();
        Log.d(LOG_TAG, "workout id:  " + intent.getLongExtra("workoutId", -1));
        Log.v(LOG_TAG, "intent gotten");

        exerciseId = intent.getLongExtra("exerciseId", -1);
        Log.v(LOG_TAG, "exercise id is:" + exerciseId);

        exercise = Exercise.load(Exercise.class, exerciseId);
//        getSupportActionBar().setTitle(exercise.name);
//
//        if(exercise.getSteps().size() > 0) {
//            ExerciseStepsAdapter adapter = new ExerciseStepsAdapter(this, exercise);
//            ListView listView = (ListView) findViewById(R.id.instructions_lv);
//            listView.setAdapter(adapter);
//        }

//        List<Muscle> musclesList = exercise.getMuscles();
        TextView name = (TextView) findViewById(R.id.exercise_name);
        name.setText(exercise.name);

        toolbarImg = (ImageView) findViewById(R.id.toolbar_img);
        String imgUrl = "http://img.youtube.com/vi/" + exercise.videoId + "/maxresdefault.jpg";
        Log.d(LOG_TAG, "img url:  " + imgUrl);
        Picasso.with(this)
                .load(imgUrl)
//                .resize(50, 50)
                .fit()
                .centerCrop()
                .into(toolbarImg);

        TextView muscles = (TextView) findViewById(R.id.exercise_muscles);
        muscles.setText(exercise.getMusclesStr());
//        if(musclesList.size() > 0) {
//            //first muscle
//            muscles.setText(musclesList.get(0).name);
//
//            //other muscles
//            for (int i = 1; i < musclesList.size(); i++) {
//                muscles.append(", " + musclesList.get(i).name);
//            }
//        }
        playVideo = (FloatingActionButton) findViewById(R.id.play_video);
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + exercise.videoId)));
            }
        });

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

        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {

        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
