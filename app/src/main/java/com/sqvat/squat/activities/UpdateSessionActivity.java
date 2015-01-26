package com.sqvat.squat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.shamanland.fab.FloatingActionButton;
import com.sqvat.squat.R;
import com.sqvat.squat.Util;
import com.sqvat.squat.data.Session;

public class UpdateSessionActivity extends ActionBarActivity {
    Session session;

    Toolbar toolbar;
    EditText setsEt;
    EditText repsEt;
    EditText restEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_session);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long sessionId = intent.getLongExtra("sessionId", -1);
        session = Session.load(Session.class, sessionId);

        getSupportActionBar().setTitle(session.exercise.name);

        setsEt = (EditText) findViewById(R.id.config_sets_et);
        repsEt = (EditText) findViewById(R.id.config_reps_et);
        restEt = (EditText) findViewById(R.id.config_rest_et);

        setsEt.setText(String.valueOf(session.sets));
        repsEt.setText(String.valueOf(session.targetReps));
        restEt.setText(String.valueOf(session.rest));

        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save_config);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText setsEt = (EditText)findViewById(R.id.config_sets_et);
                EditText repsEt = (EditText)findViewById(R.id.config_reps_et);
                EditText restEt = (EditText)findViewById(R.id.config_rest_et);

                if(Util.isNotEmpty(setsEt) && Util.isNotEmpty(repsEt) && Util.isNotEmpty(restEt)) {
                    int sets = Integer.parseInt(setsEt.getText().toString());
                    int reps = Integer.parseInt(repsEt.getText().toString());
                    int rest = Integer.parseInt(restEt.getText().toString());

                    session.sets = sets;
                    session.targetReps = reps;
                    session.rest = rest;
                    session.save();

                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            session.delete();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
