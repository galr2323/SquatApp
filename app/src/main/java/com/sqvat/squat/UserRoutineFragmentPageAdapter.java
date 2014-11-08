package com.sqvat.squat;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;

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

