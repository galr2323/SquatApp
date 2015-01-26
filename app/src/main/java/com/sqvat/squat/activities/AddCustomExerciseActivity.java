package com.sqvat.squat.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.shamanland.fab.FloatingActionButton;
import com.sqvat.squat.R;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.events.ExerciseAdded;

import de.greenrobot.event.EventBus;

public class AddCustomExerciseActivity extends ActionBarActivity {
    EditText nameEt;
    FloatingActionButton save;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_exercise);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEt = (EditText) findViewById(R.id.custom_exercise_name_et);
        save = (FloatingActionButton) findViewById(R.id.save_custom_exercise);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameEt.getText());
                Exercise exercise = new Exercise(name);
                exercise.save();
                EventBus.getDefault().post(new ExerciseAdded());
                finish();
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_custom_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


}
