package com.sqvat.squat;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Workout;


public class BaseActivity extends Activity {

    private ActionBar actionBar;

    private String[] categories;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private String drawerClose;
    private String drawerOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Base
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        actionBar = getActionBar();

        //check if the app first run and populate the db if it is
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun){
            populateDb();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        }

//        Muscle legs = new Muscle();
//        legs.name = "legs";
//        legs.exercise = Exercise.load(Exercise.class, 1);
//        legs.save();
//
//        Muscle chest = new Muscle();
//        chest.name = "chest";
//        chest.exercise = Exercise.load(Exercise.class, 2);
//        chest.save();
//
//        Muscle triceps = new Muscle();
//        triceps.name = "triceps";
//        triceps.exercise = Exercise.load(Exercise.class, 2);
//        triceps.save();


        //Nav Drawer
        categories = getResources().getStringArray(R.array.categories);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.nav_drawer_li, R.id.drawer_li_textview, categories));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerClose = "Squat";
        drawerOpen = "";
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Intent intent = getIntent();
        int category = intent.getIntExtra("category", 0);
        selectItem(category);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                fragmentTransaction.replace(R.id.content_frame, new UserRoutineFragment());
                actionBar.setTitle("Your Routine");
                break;
            case 1:
                fragmentTransaction.replace(R.id.content_frame, new HistoryFragment());
                actionBar.setTitle("History");
                break;
            case 2:
                Intent intent = new Intent(this, TrackWorkoutAct.class);
                startActivity(intent);



        }
        fragmentTransaction.commit();

        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    private void populateDb(){
        ActiveAndroid.beginTransaction();
        String[] exercisesNames = {"Squat", "Bench press", "Dips", "Dead lift", "Overhead press", "Pull up", "Curls"};
        String[] workoutsNames = {"A", "B", "C"};
        String[] completedWorkoutDates = {"22/9/14", "25/7/14", "13/6/14", "11/06/14", "8/01/1954"};
        try {
            for (String name : exercisesNames) {
                Exercise exercise = new Exercise();
                exercise.name = name;
                exercise.save();
            }

            for (String name : workoutsNames){
                Workout workout = new Workout();
                workout.name = name;
                workout.save();
            }



                    ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }




}
