package com.sqvat.squat.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.sqvat.squat.Util;
import com.sqvat.squat.data.Workout;
import com.sqvat.squat.fragments.WorkoutFragment;

import java.util.List;

public class WorkoutsPageAdapter extends FragmentStatePagerAdapter {

    private boolean editMode;
    private Context context;
    private List<Workout> workouts;

    public WorkoutsPageAdapter(FragmentManager fm, Context context, boolean editMode) {
        super(fm);
        this.editMode = editMode;
        this.context = context;
        this.workouts = Util.getUsersRoutine(context).getWorkouts();
    }

    @Override
    public Fragment getItem(int position) {
        return WorkoutFragment.newInstance(workouts.get(position).getId(), editMode);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
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
        this.workouts = Util.getUsersRoutine(context).getWorkouts();
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return workouts.size() == 0 ? true : false;
    }
}

