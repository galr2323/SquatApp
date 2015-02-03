package com.sqvat.fit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.shamanland.fab.FloatingActionButton;
import com.sqvat.fit.R;
import com.sqvat.fit.Util;
import com.sqvat.fit.data.Exercise;
import com.sqvat.fit.data.Session;

public class UpdateSessionActivity extends ActionBarActivity {
    Session session;

    Toolbar toolbar;
//    EditText setsEt;
//    EditText targetEt;
//    EditText restEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_session);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        Intent intent = getIntent();
        long sessionId = intent.getLongExtra("sessionId", -1);
        session = Session.load(Session.class, sessionId);

        getSupportActionBar().setTitle(session.exercise.name);

        final MaterialEditText setsEt = (MaterialEditText) findViewById(R.id.config_sets_et);
        final MaterialEditText targetEt = (MaterialEditText)findViewById(R.id.config_target_et);
        final MaterialEditText restEt = (MaterialEditText)findViewById(R.id.config_rest_et);

        setsEt.setText(String.valueOf(session.sets));
        targetEt.setText(String.valueOf(session.target));
        restEt.setText(String.valueOf(session.rest));

        targetEt.setHint(Exercise.TARGETS_STR[session.exercise.targetType]);
        targetEt.setFloatingLabelText(Exercise.TARGETS_STR[session.exercise.targetType]);

        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save_config);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.isNotEmpty(setsEt) && Util.isNotEmpty(targetEt) && Util.isNotEmpty(restEt)) {
                    int sets = Integer.parseInt(setsEt.getText().toString());
                    int target = Integer.parseInt(targetEt.getText().toString());
                    int rest = Integer.parseInt(restEt.getText().toString());

                    session.sets = sets;
                    session.target = target;
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
