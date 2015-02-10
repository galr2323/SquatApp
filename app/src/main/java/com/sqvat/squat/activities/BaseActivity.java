package com.sqvat.squat.activities;

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
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.sqvat.squat.R;
import com.sqvat.squat.adapters.NavDrawerAdapter;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Muscle;
import com.sqvat.squat.data.Routine;
import com.sqvat.squat.fragments.HistoryFragment;
import com.sqvat.squat.fragments.SettingsFragment;
import com.sqvat.squat.fragments.UserRoutineFragment;

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

import com.google.example.games.basegameutils.BaseGameUtils;


public class BaseActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = "Base Activity";
    private static String exercisesUrl = "https://api.myjson.com/bins/45rhz";
    DrawerLayout drawerLayout;
    ListView drawerList;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    private String[] categories;
    private GoogleApiClient mGoogleApiClient;

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

//    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Base
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initInFirstRun();
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_lv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String unit = sharedPref.getString("weight_unit", "");

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
        toolbar.setTitle("Your Routine");
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
        else if(item.getItemId() == R.id.test_achievements){
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 0);
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstrun = sharedPref.getBoolean("firstrun", true);
        if (firstrun){
            new GetExerciseReqTask().execute(exercisesUrl);


            sharedPref
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();

            sharedPref
                    .edit()
                    .putFloat("totalWeightLifted", 0)
                    .commit();


            Routine routine = new Routine("Your first routine");
            routine.save();

            sharedPref
                    .edit()
                    .putLong("usersRoutineId", 1)
                    .commit();

        }
    }
    private void populateDb(String data) throws JSONException {
        ActiveAndroid.beginTransaction();
//        String[] musclesNames = {"Legs", "Chest", "Biceps", "Triceps", "Back", "Shoulders"};
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

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // The player is signed in. Hide the sign-in button and allow the
        // player to proceed.
        Log.d(LOG_TAG, "onConnected called");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN,"sign in other error")) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, -1);
            }
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