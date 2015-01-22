package com.sqvat.squat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sqvat.squat.adapters.ExercisesAdapter;
import com.sqvat.squat.R;
import com.sqvat.squat.events.ExerciseAdded;
import com.sqvat.squat.events.RestFinished;

import de.greenrobot.event.EventBus;


public class ChooseExerciseActivity extends ActionBarActivity {
    Intent intent;
    final static String LOG_TAG = "ChooseExerciseActivity";
    Toolbar toolbar;
    ExercisesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ListView exercisesList = (ListView) findViewById(R.id.exercises_list);
        adapter = new ExercisesAdapter(this);
        exercisesList.setAdapter(adapter);

        intent = getIntent();
        Log.d(LOG_TAG, "workout id:  " + intent.getLongExtra("workoutId", -1));
        intent.setClass(this, ExerciseActivity.class);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("exerciseId", position + 1);
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_add_custom_exercise){
            Intent customExerciseIntent = new Intent(this, AddCustomExerciseActivity.class);
            startActivity(customExerciseIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.update();
    }


}
