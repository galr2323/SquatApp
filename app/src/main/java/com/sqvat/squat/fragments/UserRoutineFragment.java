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
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.sqvat.squat.R;
import com.sqvat.squat.activities.EditRoutineAct;
import com.sqvat.squat.adapters.WorkoutsPageAdapter;
import com.sqvat.squat.data.Workout;
import com.sqvat.squat.events.RestFinished;

import java.util.List;


public class UserRoutineFragment extends Fragment {
    //    private Button add;
    private WorkoutsPageAdapter adapter;
    private static int numOfWorkouts;
    private static List<Workout> workouts;
    private long currentWorkoutId;
    private boolean inEditMode;

    ViewPager viewPager;
    PagerSlidingTabStrip tabs;

    private static String LOG_TAG = "User routine fragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_user_routine, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.routine_pager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.routine_tabs);

        //TODO: check if init of vars needs to move to onCreate
        workouts = Workout.getAll();
        numOfWorkouts = workouts.size();
        currentWorkoutId = 1;
        inEditMode = false;

        adapter = new WorkoutsPageAdapter(getFragmentManager(), false);
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);

//        if(adapter.isEmpty() && savedInstanceState == null){
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            transaction.add(R.id.user_routine_root, new NoWorkoutsFragment());
//            transaction.commit();
//        }
//        else {
//
//        }

        return view;
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


    //TODO: make the update only when needed with eventbus sticky event
    @Override
    public void onResume() {
        super.onResume();
        adapter.update();
    }

}
