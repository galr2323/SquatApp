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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
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
    private ViewGroup view;
    private boolean firstRunAfterFill;

    ViewPager viewPager;
    PagerSlidingTabStrip tabs;

    private static String LOG_TAG = "User routine fragment";
    private GoogleApiClient mGoogleApiClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_user_routine, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.routine_pager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);


        adapter = new WorkoutsPageAdapter(getChildFragmentManager(), getActivity(), false);
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);

        if(adapter.isEmpty()){
            tabs.setVisibility(View.GONE);
        }

//        if (adapter.isEmpty()) {
//
//            tabs.setVisibility(View.GONE);
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//            RoutineEmptyView routineEmptyView = new RoutineEmptyView(getActivity());
//            routineEmptyView.setLayoutParams(lp);
//            view.addView(routineEmptyView);
//
//        }

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
            startActivity(intent);
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
        if (adapter.isEmpty()){
            tabs.setVisibility(View.GONE);
        }
        else {
            tabs.setVisibility(View.VISIBLE);
        }

//        else {
//            View routineEmptyViewMain = view.findViewById(R.id.routine_empty_view);
//            if(routineEmptyViewMain != null) {
//                if(routineEmptyViewMain.getVisibility())
//                tabs.setVisibility(View.VISIBLE);
//                view.removeView((View) routineEmptyView);
//            }
//            //showcase the workout now fab-----------------
//
//
////            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
////            boolean needWorkoutNowTutorial = sharedPref.getBoolean("needWorkoutNowTutorial", true);
////            if (needWorkoutNowTutorial) {
////                View workoutNow = view.findViewById(R.id.workout_fab);
////                ViewTarget target = new ViewTarget(workoutNow);
////
////                new ShowcaseView.Builder(getActivity())
////                        .setTarget(target)
////                        .setContentTitle("ShowcaseView")
////                        .setContentText("This is highlighting the Home button")
////                        .hideOnTouchOutside()
////                        .build();
////
////                sharedPref
////                        .edit()
////                        .putBoolean("needWorkoutNowTutorial", false)
////                        .commit();
////            }
//            //end0-------
//
//        }

    }

//    public void onEvent(RoutineDeleted event){
//        adapter = new WorkoutsPageAdapter(getFragmentManager(),getActivity(),false);
//        viewPager.setAdapter(adapter);
//        tabs.setViewPager(viewPager);
//        adapter.update();
//        tabs.setVisibility(View.GONE);
////        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////
////        RoutineEmptyView routineEmptyView = new RoutineEmptyView(getActivity());
////        routineEmptyView.setLayoutParams(lp);
////        view.addView(routineEmptyView);
//
//        EventBus.getDefault().removeStickyEvent(event);
//    }
//
//    public void onEvent(WorkoutAdded event){
//        adapter = new WorkoutsPageAdapter(getFragmentManager(),getActivity(),false);
//        viewPager.setAdapter(adapter);
//        tabs.setViewPager(viewPager);
//        adapter.update();
//        tabs.setVisibility(View.VISIBLE);
////
////        View routineEmptyViewMain = view.findViewById(R.id.routine_empty_view);
////        view.removeView((View) routineEmptyViewMain.getRootView());
//
//        EventBus.getDefault().removeStickyEvent(event);
//    }


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
