package com.sqvat.squat.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.sqvat.squat.R;
import com.sqvat.squat.activities.EditRoutineAct;
import com.sqvat.squat.adapters.WorkoutsPageAdapter;
import com.sqvat.squat.data.Workout;

import java.util.List;


public class UserRoutineFragment extends Fragment {
    //    private Button add;
    private WorkoutsPageAdapter adapter;
    private static int numOfWorkouts;
    private static List<Workout> workouts;
    private long currentWorkoutId;

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
        View view = inflater.inflate(R.layout.fragment_user_routine, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.routine_pager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);


        adapter = new WorkoutsPageAdapter(getChildFragmentManager(), getActivity(), false);
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);

        //ViewTarget target = new ViewTarget()
//        new ShowcaseView.Builder(getActivity())
//                .setTarget(new ActionViewTarget(getActivity(), ))
//                .setContentTitle("ShowcaseView")
//                .setContentText("This is highlighting the Home button")
//                .hideOnTouchOutside()
//                .build();

        return view;
    }

    private void setActionBarElevation(Activity activity, float elevation) {
        ActionBarActivity actionBarActivity = (ActionBarActivity) activity;
        actionBarActivity.getSupportActionBar().setElevation(elevation);
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


    //TODO: make the update only when needed with eventbus sticky event
    @Override
    public void onResume() {
        super.onResume();
        adapter.update();
    }

    @Override
    public void onDestroy() {
        super.onDetach();
//        setActionBarElevation(getActivity(), 7);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
//        setActionBarElevation(activity, 0);
    }



}
