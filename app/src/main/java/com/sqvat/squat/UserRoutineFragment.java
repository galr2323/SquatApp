package com.sqvat.squat;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

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
                        getActivity().getActionBar().setSelectedNavigationItem(position);
                    }
                });

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        TabListener tabListener = new TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_routine, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_routine){
            startEditMode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startEditMode(){
        actionBar.addTab(
                actionBar.newTab()
                        .setText("+")
                        .setTabListener(new TabListener() {
                            @Override
                            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                                alert.setTitle("Workout name");


                                // Set an EditText view to get user input
                                final EditText input = new EditText(getActivity());
                                alert.setView(input);

                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Editable value = input.getText();
                                        Workout workout = new Workout();
                                        workout.name = String.valueOf(value);
                                        workout.order = Workout.getAll().size();
                                        workout.save();
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Canceled.
                                    }
                                });

                                alert.show();
                            }

                            @Override
                            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                            }

                            @Override
                            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                            }
                        }));
    }

    public static int getNumOfWorkouts(){
        return numOfWorkouts;
    }

    public static Workout getWorkout(int position){
        return workouts.get(position);
    }
}
