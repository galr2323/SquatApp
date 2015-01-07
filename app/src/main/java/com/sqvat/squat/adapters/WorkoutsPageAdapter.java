package com.sqvat.squat.adapters;

import android.app.FragmentManager;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.sqvat.squat.data.Workout;
import com.sqvat.squat.fragments.UserRoutineFragment;
import com.sqvat.squat.fragments.WorkoutFragment;

import java.util.List;

public class WorkoutsPageAdapter extends FragmentPagerAdapter {

    private boolean editMode;
    private List<Workout> workouts;

    public WorkoutsPageAdapter(FragmentManager fm, boolean editMode) {
        super(fm);
        this.editMode = editMode;
        this.workouts = Workout.getAll();
    }

    @Override
    public Fragment getItem(int position) {
        return WorkoutFragment.newInstance(workouts.get(position).getId(), editMode);
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return workouts.get(position).name;
    }

    public void update(){
        workouts = Workout.getAll();
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return workouts.size() == 0 ? true : false;
    }
}

