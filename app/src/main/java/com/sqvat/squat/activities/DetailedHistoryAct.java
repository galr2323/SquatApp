package com.sqvat.squat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sqvat.squat.R;
import com.sqvat.squat.adapters.DetailedHistoryAdapter;
import com.sqvat.squat.data.CompletedWorkout;


public class DetailedHistoryAct extends ActionBarActivity {
    CompletedWorkout completedWorkout;
    DetailedHistoryAdapter adapter;
    ListView detailedHistoryLv;
    Toolbar toolbar;

    private final static String LOG_TAG = "detailed history act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_history);

        detailedHistoryLv = (ListView) findViewById(R.id.detailed_history_lv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        Intent intent = getIntent();
        final long completedWorkoutId = intent.getLongExtra("completedWorkoutId", -1);

        Log.d(LOG_TAG, "completed workout id:  " + completedWorkoutId);

        completedWorkout = CompletedWorkout.load(CompletedWorkout.class, completedWorkoutId);

        getSupportActionBar().setTitle("Workout " + completedWorkout.workout.name);

        adapter = new DetailedHistoryAdapter(this, completedWorkout);
        //ListAdapter adapter = new ArrayAdapter<CompletedSet>(this, R.layout.simple_li,R.id.li_text, completedWorkout.getCompletedSessions().get(0).getCompletedSets());
        detailedHistoryLv.setAdapter(adapter);

//        List<CompletedSession> completedSessions = completedWorkout.getCompletedSessions();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailed_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
