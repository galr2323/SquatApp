package com.sqvat.squat.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.astuetz.PagerSlidingTabStrip;
import com.sqvat.squat.PromptDialog;
import com.sqvat.squat.R;
import com.sqvat.squat.adapters.WorkoutsPageAdapter;
import com.sqvat.squat.data.Workout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class EditRoutineAct extends ActionBarActivity {
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.edit_routine_pager) ViewPager viewPager;
    @InjectView(R.id.edit_routine_tabs) PagerSlidingTabStrip tabs;

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
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        workouts = Workout.getAll();
        numOfWorkouts = workouts.size();

        adapter = new WorkoutsPageAdapter(getFragmentManager(), true);
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);


        //initTabs(this);


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
