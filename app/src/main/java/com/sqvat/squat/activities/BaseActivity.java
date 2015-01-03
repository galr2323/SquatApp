package com.sqvat.squat.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.sqvat.squat.fragments.HistoryFragment;
import com.sqvat.squat.R;
import com.sqvat.squat.fragments.UserRoutineFragment;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Workout;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BaseActivity extends ActionBarActivity {
    private String[] categories;

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.drawer_lv) ListView drawerList;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    ActionBarDrawerToggle drawerToggle;

//    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Base
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.inject(this);
        initInFirstRun();

        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);



        categories = getResources().getStringArray(R.array.categories);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.nav_drawer_li, R.id.drawer_li_textview, categories));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        Intent intent = getIntent();
        int category = intent.getIntExtra("category", 0);
        selectItem(category);
    }


    private void selectItem(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                fragmentTransaction.replace(R.id.content_frame, new UserRoutineFragment());
                toolbar.setTitle("Your Routine");
                break;
            case 1:
                fragmentTransaction.replace(R.id.content_frame, new HistoryFragment());
                toolbar.setTitle("History");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.base,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void initInFirstRun(){
        //check if the app first run and populate the db if it is
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun){
            populateDb();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        }
    }
    private void populateDb(){
        ActiveAndroid.beginTransaction();
        String[] exercisesNames = {"Squat", "Bench press", "Dips", "Dead lift", "Overhead press", "Pull up", "Curls", "Row", "Standing Row", "Cable pull"};
        String[] workoutsNames = {"A", "B"};
        //String[] completedWorkoutDates = {"22/9/14", "25/7/14", "13/6/14", "11/06/14", "8/01/1954"};
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

            Workout workout = new Workout("C", 2);
            workout.save();


                    ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }




}
