package com.sqvat.squat.adapters;

import android.app.FragmentManager;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.sqvat.squat.fragments.UserRoutineFragment;
import com.sqvat.squat.fragments.WorkoutFragment;

public class WorkoutsPageAdapter extends FragmentStatePagerAdapter {

    private boolean editMode;

    public WorkoutsPageAdapter(FragmentManager fm, boolean editMode) {
        super(fm);
        this.editMode = editMode;
    }

    @Override
    public Fragment getItem(int position) {
        return WorkoutFragment.newInstance(UserRoutineFragment.getWorkout(position).getId(), editMode);
    }

    @Override
    public int getCount() {
        return UserRoutineFragment.getNumOfWorkouts();
    }
}

