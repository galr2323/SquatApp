package com.sqvat.squat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Workout;

/**
 * Created by Gal on 04/11/2014.
 */
public class TrackWorkoutActPageAdapter extends FragmentPagerAdapter {

    Workout workout;

    public TrackWorkoutActPageAdapter(FragmentManager fm, Workout workout) {
        super(fm);
        this.workout = workout;
    }

    @Override
    public Fragment getItem(int position) {
        //Workout workout = Workout.load(Workout.class, position);
        Session session = workout.getSessions().get(position);
        Fragment fragment = TrackSessionFragment.newInstance(session);

        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return UserRoutineFragment.getNumOfWorkouts();
    }
}
