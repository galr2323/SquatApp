package com.sqvat.squat;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sqvat.squat.data.Workout;

import java.util.List;


public class EditRoutineAct extends Activity {

    private ViewPager viewPager;
    private UserRoutineFragmentPageAdapter adapter;
    private static int numOfWorkouts;
    private static List<Workout> workouts;
    private ActionBar actionBar;
    private long currentWorkoutId;
    private boolean inEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_routine);

        //TODO: check if init of vars needs to move to onCreate
        workouts = Workout.getAll();
        numOfWorkouts = workouts.size();

        adapter = new UserRoutineFragmentPageAdapter(getFragmentManager());

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



        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
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
