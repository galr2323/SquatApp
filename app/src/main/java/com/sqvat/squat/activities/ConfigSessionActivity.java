package com.sqvat.squat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.shamanland.fab.FloatingActionButton;
import com.sqvat.squat.R;
import com.sqvat.squat.Util;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Workout;


public class ConfigSessionActivity extends ActionBarActivity {
    private Session session;
    private int order;
    private static final String LOG_TAG = "Config Session Activity";

    int sets;
    int target;
    int rest;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_session);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        Intent intent = getIntent();
        final long workoutId = intent.getLongExtra("workoutId", -1);
        final Workout workout = Workout.load(Workout.class, workoutId);

        final long exerciseId = intent.getLongExtra("exerciseId", -1);
        final Exercise exercise = Exercise.load(Exercise.class, exerciseId);
        getSupportActionBar().setTitle(exercise.name);

        final MaterialEditText setsEt = (MaterialEditText) findViewById(R.id.config_sets_et);
        final MaterialEditText targetEt = (MaterialEditText)findViewById(R.id.config_target_et);
        final MaterialEditText restEt = (MaterialEditText)findViewById(R.id.config_rest_et);

        targetEt.setHint(Exercise.TARGETS_STR[exercise.targetType]);
        targetEt.setFloatingLabelText(Exercise.TARGETS_STR[exercise.targetType]);
        Log.d(LOG_TAG, "exercise target type: " + exercise.targetType);

        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save_config);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Util.isNotEmpty(setsEt) && Util.isNotEmpty(targetEt) && Util.isNotEmpty(restEt)) {
                    sets = Integer.parseInt(setsEt.getText().toString());
                    target = Integer.parseInt(targetEt.getText().toString());
                    rest = Integer.parseInt(restEt.getText().toString());

                    session = new Session(workout, workout.getSessions().size(), exercise, sets, target, rest);
                    session.save();

                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                }

            }
        });




    }
}


//        Intent intent = getIntent();
//        Exercise exercise = Exercise.load(Exercise.class, intent.getIntExtra("exerciseId",-1));
//        session = new Session();
//        session.workout = Workout.load(Workout.class, intent.getLongExtra("workoutId", -1));
//        session.exercise = exercise;
//        session.targetOrder = session.workout.getSessions().size();
//        //TODO: take restEt as input from user
//
//        //session.save();
//
//        workouts = Workout.getAll();
//        numOfWorkouts = workouts.size();
//        currentWorkoutId = 1;
//        inEditMode = false;
//
//        adapter = new WorkoutsPageAdapter(getFragmentManager(), false);
//
//        viewPager = (ViewPager) view.findViewById(R.id.routine_pager);
//        viewPager.setAdapter(adapter);
//
//        actionBar = getActivity().getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        viewPager.setOnPageChangeListener(
//                new ViewPager.SimpleOnPageChangeListener() {
//                    @Override
//                    public void onPageSelected(int position) {
//                        getActivity().getActionBar().setSelectedNavigationItem(position);
//                    }
//                });
//
//
//
//        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//            @Override
//            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//                viewPager.setCurrentItem(tab.getPosition());
//                currentWorkoutId = tab.getPosition() + 1;
//            }
//
//            @Override
//            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//
//            }
//
//            @Override
//            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//
//            }
//        };
//        if(actionBar.getTabCount() == 0){
//            for (int i = 0; i < numOfWorkouts; i++) {
//                actionBar.addTab(
//                        actionBar.newTab()
//                                .setText(workouts.get(i).name)
//                                .setTabListener(tabListener));
//            }
//        }
//
////
////        order = 0;
////
////        Intent intent = getIntent();
////        Exercise exercise = Exercise.load(Exercise.class, intent.getIntExtra("exerciseId",1));
////        session = new Session();
////        session.workout = Workout.load(Workout.class, intent.getLongExtra("workoutId", -2));
////        session.exercise = exercise;
////        session.targetOrder = session.workout.getSessions().size();
////        //TODO: take restEt as input from user
////        session.restEt = 90;
////        session.save();
////
////        //Sets list adapter
////        ListView setsList = (ListView) findViewById(R.id.sets_list);
////        adapter = new ConfigSessionSetsAdapter(this, session);
////        setsList.setAdapter(adapter);
////
////        //setting up add set button and action
////        Button addSet = (Button) findViewById(R.id.add_set_btn);
////        addSet.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Set set = new Set();
////                set.session = session;
////                //keeps putting 0
////                set.order = order;
////                order++;
////
////                set.target = 10;
//////                Log.d("YOLO", set.toString());
////                adapter.addSet(set);
////            }
////        });
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.config_session, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_save) {
//            Log.d(LOG_TAG, "save button has been pressed");
//            ActiveAndroid.beginTransaction();
//            try {
//
//                Log.d(LOG_TAG, String.valueOf(adapter.getSets().size()));
//                for (Set set : adapter.getSets()) {
//                    Log.d(LOG_TAG, "saving set " + set.toString());
//                    set.save();
//                }
//
//                ActiveAndroid.setTransactionSuccessful();
//
//
//            }
//            finally {
//                ActiveAndroid.endTransaction();
//            }
//
//            Intent backIntent = new Intent(this, BaseActivity.class);
//            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            backIntent.putExtra("category", 0);
//
//            startActivity(backIntent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
