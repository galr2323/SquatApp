package com.sqvat.fit.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.sqvat.fit.data.CompletedWorkout;
import com.sqvat.fit.data.Session;
import com.sqvat.fit.data.Workout;
import com.sqvat.fit.fragments.TrackSessionFragment;

import java.util.Date;
import java.util.List;

/**
 * Created by Gal on 04/11/2014.
 */
public class TrackWorkoutActPageAdapter extends FragmentStatePagerAdapter {
    private static final String LOG_TAG = "Track workout act page adapter";
    List<Session> sessions;
    CompletedWorkout completedWorkout;

    public TrackWorkoutActPageAdapter(FragmentManager fm, Workout workout, CompletedWorkout completedWorkout) {
        super(fm);
        this.sessions = workout.getSessions();

        this.completedWorkout = completedWorkout;

    }

    @Override
    public Fragment getItem(int position) {
        //Workout workout = Workout.load(Workout.class, position);
        Log.d(LOG_TAG, "item position" + String.valueOf(position));
        Session session = sessions.get(position);
        Fragment fragment = TrackSessionFragment.newInstance(session, completedWorkout, position);

        return fragment;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sessions.get(position).exercise.name;
    }
}
