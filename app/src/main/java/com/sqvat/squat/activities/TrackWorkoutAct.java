package com.sqvat.squat.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.sqvat.squat.R;
import com.sqvat.squat.adapters.TrackWorkoutActPageAdapter;
import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.UserData;
import com.sqvat.squat.data.Workout;
import com.sqvat.squat.events.WorkoutCompleted;

import de.greenrobot.event.EventBus;


public class TrackWorkoutAct extends ActionBarActivity{

    private static int currentSessionOrder = -1;
    public Intent intent;
    Toolbar toolbar;
    private Workout workout;

    public static int getSessionOrder(){
        currentSessionOrder++;
        return currentSessionOrder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        long workoutId = intent.getLongExtra("workoutId", 1);
        workout = Workout.load(Workout.class, workoutId);

        getSupportActionBar().setTitle("Workout" + " " + workout.name);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new TrackWorkoutFragment())
                    .commit();
        }


    }

    public static class TrackWorkoutFragment extends Fragment {
        private static final String LOG_TAG = "Track Workout Fragment";
        Intent intent;
        Workout workout;
        ViewPager viewPager;
        PagerSlidingTabStrip tabs;
        private TrackWorkoutActPageAdapter adapter;
        private CompletedWorkout completedWorkout;

        private static int RC_SIGN_IN = 9001;

        private boolean mResolvingConnectionFailure = false;
        private boolean mAutoStartSignInflow = true;
        private boolean mSignInClicked = false;

        public TrackWorkoutFragment() {

        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.track_workut, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if(id == R.id.action_finish_track){
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            intent = activity.getIntent();
            long workoutId = intent.getLongExtra("workoutId", 1);
            workout = Workout.load(Workout.class, workoutId);

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
//                            // add other APIs and scopes here as needed
//                    .build();
        }

//        @Override
//        public void onConnected(Bundle bundle) {
//
//        }
//
//        @Override
//        public void onConnectionSuspended(int i) {
//            // Attempt to reconnect
//            mGoogleApiClient.connect();
//        }
//
//        public void onActivityResult(int requestCode, int resultCode,
//                                     Intent intent) {
//            if (requestCode == RC_SIGN_IN) {
//                mSignInClicked = false;
//                mResolvingConnectionFailure = false;
//                if (resultCode == RESULT_OK) {
//                    mGoogleApiClient.connect();
//                } else {
//                    // Bring up an error dialog to alert the user that sign-in
//                    // failed. The R.string.signin_failure should reference an error
//                    // string in your strings.xml file that tells the user they
//                    // could not be signed in, such as "Unable to sign in."
//                    BaseGameUtils.showActivityResultError(getActivity(),
//                            requestCode, resultCode, -1);
//                }
//            }
//        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_track_workout, container, false);

            tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
            viewPager = (ViewPager) view.findViewById(R.id.track_workout_pager);
            //TODO: make a better solution, maybe restore fragments
            viewPager.setOffscreenPageLimit(15);

            FragmentManager fm = getChildFragmentManager();
            completedWorkout = new CompletedWorkout(workout);
            completedWorkout.save();
            adapter = new TrackWorkoutActPageAdapter(fm, workout, completedWorkout);
            viewPager.setAdapter(adapter);
            tabs.setViewPager(viewPager);


            return view;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (completedWorkout.getCompletedSessions().size() == 0){
                completedWorkout.delete();
            }
            else {
                UserData userData = UserData.load(UserData.class,1);
                for (CompletedSession completedSession : completedWorkout.getCompletedSessions()){
                   for(CompletedSet completedSet : completedSession.getCompletedSets()){
                       userData.totalWeight += completedSet.weight * completedSet.reps;
                       userData.totalReps += completedSet.reps;

                       Log.d(LOG_TAG,"total weight:" + userData.totalWeight + " totak reps: " + userData.totalReps);


                   }
                }
                userData.save();
                EventBus.getDefault().postSticky(new WorkoutCompleted());



            }

        }

//        @Override
//        public void onStart() {
//            super.onStart();
//            mGoogleApiClient.connect();
//        }
//
//        @Override
//        public void onStop() {
//            super.onStop();
//            mGoogleApiClient.disconnect();
//        }
//
//        @Override
//        public void onConnectionFailed(ConnectionResult connectionResult) {
//            if (mResolvingConnectionFailure) {
//                // already resolving
//                return;
//            }
//
//            // if the sign-in button was clicked or if auto sign-in is enabled,
//            // launch the sign-in flow
//            if (mSignInClicked || mAutoStartSignInflow) {
//                mAutoStartSignInflow = false;
//                mSignInClicked = false;
//                mResolvingConnectionFailure = true;
//
//                // Attempt to resolve the connection failure using BaseGameUtils.
//                // The R.string.signin_other_error value should reference a generic
//                // error string in your strings.xml file, such as "There was
//                // an issue with sign-in, please try again later."
//                if (!BaseGameUtils.resolveConnectionFailure(getActivity(),
//                        mGoogleApiClient, connectionResult,
//                        RC_SIGN_IN,"sign in other error")) {
//                    mResolvingConnectionFailure = false;
//                }
//            }
//
//            // Put code here to display the sign-in button
//        }
    }



}
