package com.sqvat.squat.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.sqvat.squat.adapters.NavDrawerAdapter;
import com.sqvat.squat.data.Muscle;
import com.sqvat.squat.data.MusclesToExercises;
import com.sqvat.squat.fragments.HistoryFragment;
import com.sqvat.squat.R;
import com.sqvat.squat.fragments.SettingsFragment;
import com.sqvat.squat.fragments.UserRoutineFragment;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Workout;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class BaseActivity extends ActionBarActivity {
    private String[] categories;
    private static String exercisesUrl = "https://api.myjson.com/bins/4a3kb";

    DrawerLayout drawerLayout;
    ListView drawerList;
    Toolbar toolbar;

    ActionBarDrawerToggle drawerToggle;

//    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Base
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_lv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        initInFirstRun();

        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String unit = sharedPref.getString("weight_unit", "");

        //-----
        Log.d("Base act", "weight unit:" + unit);
        //--------------------------------

        //categories = getResources().getStringArray(R.array.categories);
        //drawerList.setAdapter(new ArrayAdapter<String>(this,
          //      R.layout.nav_drawer_li, R.id.drawer_li_textview, categories));

        NavDrawerAdapter adapter = new NavDrawerAdapter(this);
        drawerList.setAdapter(adapter);

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
                fragmentTransaction.replace(R.id.content_frame, new SettingsFragment());
                toolbar.setTitle("Settings");
                break;



        }
        fragmentTransaction.addToBackStack(null);
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
            new GetExerciseReqTask().execute(exercisesUrl);

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        }
    }
    private void populateDb(String data) throws JSONException {
        ActiveAndroid.beginTransaction();
        String[] musclesNames = {"Legs", "Chest", "Biceps", "Triceps", "Back", "Shoulders"};
        try {
//            for (String name : musclesNames) {
//                Muscle muscle = new Muscle(name);
//                muscle.save();
//            }

            JSONObject reader = new JSONObject(data);
            JSONArray exercises = reader.getJSONArray("exercises");
            for (int i = 0; i < exercises.length(); i++){
                JSONObject current = exercises.getJSONObject(i);

                Exercise exercise = new Exercise(current);
                exercise.save();

                JSONArray muscles = current.getJSONArray("muscles");
                for(int c = 0; c < muscles.length(); c++){
                    Muscle muscle = new Muscle(muscles.getString(c), exercise);
                    muscle.save();

//                    Muscle muscle = new Select()
//                            .from(Muscle.class)
//                            .where("Name = ?", muscles.getString(c))
//                            .executeSingle();

//                    MusclesToExercises rel = new MusclesToExercises(exercise, muscle);
//                    rel.save();
                }
            }

            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }




    }

    class GetExerciseReqTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                populateDb(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}