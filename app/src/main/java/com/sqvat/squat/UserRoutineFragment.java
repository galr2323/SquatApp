package com.sqvat.squat;

import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqvat.squat.data.Workout;

import java.util.List;

public class UserRoutineFragment extends Fragment {
    //    private Button add;
    private ViewPager viewPager;
    private UserRoutineFragmentPageAdapter adapter;
    private static int numOfWorkouts;
    private static List<Workout> workouts;
    private ActionBar actionBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_routine, container, false);
        workouts = Workout.getAll();
        numOfWorkouts = workouts.size();

        adapter = new UserRoutineFragmentPageAdapter(getFragmentManager());

        viewPager = (ViewPager) view.findViewById(R.id.routine_pager);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        ((ActionBarActivity)getActivity()).getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }
        };
        if(actionBar.getTabCount() == 0){
            for (int i = 0; i < numOfWorkouts; i++) {
                actionBar.addTab(
                    actionBar.newTab()
                    .setText(workouts.get(i).name)
                    .setTabListener(tabListener));
            }
        }


//        Button add = (Button) view.findViewById(R.id.addButton);
//        final String name = String.valueOf(((EditText) view.findViewById(R.id.nameInput)).getText());
//        final String date = String.valueOf(((EditText) view.findViewById(R.id.dateInput)).getText());
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CompletedWorkout workout = new CompletedWorkout();
//                workout.name = name;
//                workout.date = date;
//                workout.save();
//            }
//        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    public static int getNumOfWorkouts(){
        return numOfWorkouts;
    }

    public static Workout getWorkout(int position){
        return workouts.get(position);
    }
}
