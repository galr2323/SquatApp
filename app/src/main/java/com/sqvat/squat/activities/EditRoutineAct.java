package com.sqvat.squat.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sqvat.squat.PromptDialog;
import com.sqvat.squat.R;
import com.sqvat.squat.adapters.WorkoutsPageAdapter;
import com.sqvat.squat.data.Workout;

import java.util.List;


public class EditRoutineAct extends Activity {

    private ViewPager viewPager;
    private WorkoutsPageAdapter adapter;
    private static int numOfWorkouts;
    private static List<Workout> workouts;
    private ActionBar actionBar;
    private long currentWorkoutId;
    private boolean inEditMode;
    private ActionBar.TabListener tabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_routine);

        //TODO: check if init of vars needs to move to onCreate
        workouts = Workout.getAll();
        numOfWorkouts = workouts.size();

        adapter = new WorkoutsPageAdapter(getFragmentManager(), true);

        viewPager = (ViewPager) findViewById(R.id.edit_routine_pager);
        viewPager.setAdapter(adapter);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });



        tabListener = new ActionBar.TabListener() {
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

        initTabs(this);


    }

    private void initTabs(final Context context){
        if(actionBar.getTabCount() == 0){
            for (int i = 0; i < numOfWorkouts; i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(workouts.get(i).name)
                                .setTabListener(tabListener));
            }

            ActionBar.TabListener plusTabListener = new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Workout name");

                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Editable val = input.getText();
                            String name = val.toString();

                            Workout workout = new Workout(name, Workout.getAll().size());
                            workout.save();

                            appendTab(name);
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
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                }
            };

            actionBar.addTab(
                    actionBar.newTab()
                            .setText("+")
                            .setTabListener(plusTabListener));

        }
    }

    private void appendTab(String name) {
        actionBar.addTab(
                actionBar.newTab()
                        .setText(name)
                        .setTabListener(tabListener)
                        ,actionBar.getTabCount() - 1, true);

        viewPager.setCurrentItem(actionBar.getTabCount() - 1);

        adapter.update();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_routine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
