package com.sqvat.squat.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sqvat.squat.R;
import com.sqvat.squat.activities.EditRoutineAct;
import com.sqvat.squat.adapters.WorkoutsPageAdapter;
import com.sqvat.squat.data.Workout;
import com.sqvat.squat.events.RestFinished;

import java.util.List;

public class UserRoutineFragment extends Fragment {
    //    private Button add;
    private ViewPager viewPager;
    private WorkoutsPageAdapter adapter;
    private static int numOfWorkouts;
    private static List<Workout> workouts;
    private ActionBar actionBar;
    private long currentWorkoutId;
    private boolean inEditMode;

    private static String LOG_TAG = "User routine fragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_routine, container, false);
        //TODO: check if init of vars needs to move to onCreate
        workouts = Workout.getAll();
        numOfWorkouts = workouts.size();
        currentWorkoutId = 1;
        inEditMode = false;

        adapter = new WorkoutsPageAdapter(getFragmentManager(), false);

        viewPager = (ViewPager) view.findViewById(R.id.routine_pager);
        viewPager.setAdapter(adapter);

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActivity().getActionBar().setSelectedNavigationItem(position);
                    }
                });



        TabListener tabListener = new TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
                currentWorkoutId = tab.getPosition() + 1;
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_routine, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_routine){
            Intent intent = new Intent(getActivity(), EditRoutineAct.class);
            startActivityForResult(intent, 0);
        }

        return super.onOptionsItemSelected(item);
    }

//    public void toggleEditMode(){
//        if(inEditMode){
//            return;
//        }
//        actionBar.addTab(
//                actionBar.newTab()
//                        .setText("+")
//                        .setTabListener(new TabListener() {
//                            @Override
//                            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//
//                                alert.setTitle("Workout name");
//
//
//                                // Set an EditText view to get user input
//                                final EditText input = new EditText(getActivity());
//                                alert.setView(input);
//
//                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        Editable value = input.getText();
//                                        Workout workout = new Workout();
//                                        workout.name = String.valueOf(value);
//                                        workout.order = Workout.getAll().size();
//                                        workout.save();
//                                    }
//                                });
//
//                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        // Canceled.
//                                    }
//                                });
//
//                                alert.show();
//                            }
//
//                            @Override
//                            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//
//                            }
//
//                            @Override
//                            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//
//                            }
//                        }));
//
//        Button workoutNow = (Button) getView().findViewById(R.id.workout_now);
//        ViewGroup layout = (ViewGroup) workoutNow.getParent();
//        if (layout != null) {
//            layout.removeView(workoutNow);
//            layout.addView(new AddExerciseButton(getActivity(), currentWorkoutId));
//        }
//    }



    public static int getNumOfWorkouts(){
        return numOfWorkouts;
    }

    public static Workout getWorkout(int position){
        return workouts.get(position);
    }

}