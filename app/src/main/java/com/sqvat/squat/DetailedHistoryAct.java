package com.sqvat.squat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;

import java.util.List;


public class DetailedHistoryAct extends Activity {
    CompletedWorkout completedWorkout;

    private final static String LOG_TAG = "detailed history act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_history);

        Intent intent = getIntent();
        final long completedWorkoutId = intent.getLongExtra("completedWorkoutId", -1);

        Log.d(LOG_TAG, "completed workout id:  " + completedWorkoutId);

        completedWorkout = CompletedWorkout.load(CompletedWorkout.class, completedWorkoutId);

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
