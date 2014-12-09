package com.sqvat.squat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Workout;

import java.util.List;

/**
 * Created by Gal on 04/11/2014.
 */
public class TrackWorkoutActPageAdapter extends FragmentStatePagerAdapter {
    private static final String LOG_TAG = "Track workout act page adapter";
    List<Session> sessions;

    public TrackWorkoutActPageAdapter(FragmentManager fm, Workout workout) {
        super(fm);
        this.sessions = workout.getSessions();
    }

    @Override
    public Fragment getItem(int position) {
        //Workout workout = Workout.load(Workout.class, position);
        Log.d(LOG_TAG, "item position" + String.valueOf(position));
        Session session = sessions.get(position);
        Fragment fragment = TrackSessionFragment.newInstance(session);

        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }
}
