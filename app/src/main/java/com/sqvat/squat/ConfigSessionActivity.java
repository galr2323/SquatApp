package com.sqvat.squat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Set;
import com.sqvat.squat.data.Workout;


public class ConfigSessionActivity extends Activity {
    private Session session;
    private ConfigSessionSetsAdapter adapter;
    private int order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_session);

        order = 0;

        Intent intent = getIntent();
        Exercise exercise = Exercise.load(Exercise.class, intent.getIntExtra("exerciseId",1));
        session = new Session();
        session.workout = Workout.load(Workout.class, intent.getIntExtra("workoutId", 1));
        session.exercise = exercise;
        session.targetOrder = session.workout.getSessions().size();
        //TODO: take rest as input from user
        session.rest = 90;
        session.save();

        //Sets list adapter
        ListView setsList = (ListView) findViewById(R.id.sets_list);
        adapter = new ConfigSessionSetsAdapter(this, session);
        setsList.setAdapter(adapter);

        //setting up add set button and action
        Button addSet = (Button) findViewById(R.id.add_set_btn);
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set set = new Set();
                set.session = session;
                //keeps putting 0
                set.order = order;
                order++;

                set.targetReps = 10;
                Log.d("YOLO", set.toString());
                adapter.addSet(set);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.config_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            ActiveAndroid.beginTransaction();
            try {
                for (Set set : adapter.getSets()) {
                    Log.d("Saveing sets", set.toString());
                    set.save();
                }

            }
            finally {
                ActiveAndroid.endTransaction();
            }

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}