package com.sqvat.fit.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.sqvat.fit.R;
import com.sqvat.fit.adapters.TrackWorkoutActPageAdapter;
import com.sqvat.fit.data.CompletedWorkout;
import com.sqvat.fit.data.Workout;



public class TrackWorkoutAct extends ActionBarActivity{

    private static int currentSessionOrder = -1;
    public Intent intent;
    Toolbar toolbar;
    private Workout workout;

    public static int getSessionOrder(){
        currentSessionOrder++;
        return currentSessionOrder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", 1);
        workout = Workout.load(Workout.class, workoutId);

        getSupportActionBar().setTitle("Workout" + " " + workout.name);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new TrackWorkoutFragment())
                    .commit();
        }


    }

    public static class TrackWorkoutFragment extends Fragment {
        Intent intent;
        Workout workout;
        ViewPager viewPager;
        PagerSlidingTabStrip tabs;
        private TrackWorkoutActPageAdapter adapter;
        private CompletedWorkout completedWorkout;
        public TrackWorkoutFragment() {

        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.track_workut, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if(id == R.id.action_finish_track){
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            intent = activity.getIntent();
            long workoutId = intent.getLongExtra("workoutId", 1);
            workout = Workout.load(Workout.class, workoutId);

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_track_workout, container, false);

            tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
            viewPager = (ViewPager) view.findViewById(R.id.track_workout_pager);
            //TODO: make a better solution, maybe restore fragments
            viewPager.setOffscreenPageLimit(15);

            FragmentManager fm = getChildFragmentManager();
            completedWorkout = new CompletedWorkout(workout);
            completedWorkout.save();
            adapter = new TrackWorkoutActPageAdapter(fm, workout, completedWorkout);
            viewPager.setAdapter(adapter);
            tabs.setViewPager(viewPager);

            return view;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (completedWorkout.getCompletedSessions().size() == 0){
                completedWorkout.delete();
            }
        }
    }



}
