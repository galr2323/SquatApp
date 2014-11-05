package com.sqvat.squat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class UserRoutineFragmentPageAdapter extends FragmentStatePagerAdapter {

    public UserRoutineFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //Workout workout = Workout.load(Workout.class, position);
        Fragment fragment = new WorkoutFragment(UserRoutineFragment.getWorkout(position));

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

