package com.sqvat.squat.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.sqvat.squat.R;
import com.sqvat.squat.adapters.TrackWorkoutActPageAdapter;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Workout;
import com.sqvat.squat.fragments.TimerFragment;
import com.sqvat.squat.fragments.TrackSessionFragment;



public class TrackWorkoutAct extends ActionBarActivity{
    private TrackWorkoutActPageAdapter adapter;
    private Workout workout;
    private Intent intent;
    private static int currentSessionOrder = -1;

    private CompletedWorkout completedWorkout;

    TrackSessionFragment currentFrag;

     Toolbar toolbar;
     ViewPager viewPager;
 PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_workout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.track_workout_pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.track_workout_tabs);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();

        intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", 1);

        workout = Workout.load(Workout.class, workoutId);
        adapter = new TrackWorkoutActPageAdapter(fm, workout);

        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.track_workut, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_finish_track){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getSessionOrder(){
        currentSessionOrder++;
        return currentSessionOrder;
    }

    public CompletedWorkout getCompletedWorkout(){
        return completedWorkout;
    }

    public void onFinish() {
        //replace timer fragment to log set fragment
        currentFrag.replaceToLogSetFragment();
    }



}
