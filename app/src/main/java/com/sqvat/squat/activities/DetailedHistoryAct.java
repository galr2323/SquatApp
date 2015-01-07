package com.sqvat.squat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sqvat.squat.adapters.DetailedHistoryAdapter;
import com.sqvat.squat.R;
import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;

import java.util.List;


public class DetailedHistoryAct extends ActionBarActivity {
    CompletedWorkout completedWorkout;
    DetailedHistoryAdapter adapter;
    ListView detailedHistoryLv;

    private final static String LOG_TAG = "detailed history act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_history);

        detailedHistoryLv = (ListView) findViewById(R.id.detailed_history_lv);

        Intent intent = getIntent();
        final long completedWorkoutId = intent.getLongExtra("completedWorkoutId", -1);

        Log.d(LOG_TAG, "completed workout id:  " + completedWorkoutId);

        completedWorkout = CompletedWorkout.load(CompletedWorkout.class, completedWorkoutId);

        adapter = new DetailedHistoryAdapter(this, completedWorkout);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
