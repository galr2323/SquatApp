package com.sqvat.squat.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.sqvat.squat.R;
import com.sqvat.squat.adapters.TrackWorkoutActPageAdapter;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Workout;
import com.sqvat.squat.fragments.TimerFragment;
import com.sqvat.squat.fragments.TrackSessionFragment;


public class TrackWorkoutAct extends Activity implements TimerFragment.OnTimerFinishListener, TrackSessionFragment.HasCurrentFragment{
    private TrackWorkoutActPageAdapter adapter;
    private Workout workout;
    private Intent intent;
    private ViewPager viewPager;
    private ActionBar actionBar;
    private static int currentSessionOrder = -1;

    private CompletedWorkout completedWorkout;

    TrackSessionFragment currentFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_workout);



        FragmentManager fm = getFragmentManager();

        intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", 1);

        workout = Workout.load(Workout.class, workoutId);
        adapter = new TrackWorkoutActPageAdapter(fm, workout);

        viewPager = (ViewPager) findViewById(R.id.track_workout_pager);
        viewPager.setAdapter(adapter);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
               actionBar.setSelectedNavigationItem(position);
            }
        });


        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (Session session : workout.getSessions()) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(session.exercise.name)
                            .setTabListener(tabListener));
        }



        
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


    @Override
    public void setCurrent(TrackSessionFragment fragment) {
        currentFrag = fragment;
    }


}
