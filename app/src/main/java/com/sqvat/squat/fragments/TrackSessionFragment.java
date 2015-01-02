package com.sqvat.squat.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sqvat.squat.R;
import com.sqvat.squat.activities.TrackWorkoutAct;
import com.sqvat.squat.adapters.WorkoutInfoAdapter;
import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.events.RestFinished;
import com.sqvat.squat.events.SetCompleted;

import de.greenrobot.event.EventBus;



public class TrackSessionFragment extends Fragment {

    private Session session;
    private CompletedSession completedSession;
    private EditText repsInput;
    private EditText weightInput;
    //0 based
    private int setNum;
    private boolean firstSet;

    ListView lastWorkoutLv;
    ListView currentWorkoutLv;
    WorkoutInfoAdapter currentWorkoutAdapter;


    private final String LOG_TAG = "track session fragment";

    public static TrackSessionFragment newInstance(Session session, CompletedWorkout completedWorkout) {
        TrackSessionFragment fragment = new TrackSessionFragment();
        Bundle args = new Bundle();
        args.putLong("sessionId", session.getId());
        args.putLong("completedWorkoutId", completedWorkout.getId());

        fragment.setArguments(args);
        return fragment;

    }
    public TrackSessionFragment() {}



//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        try {
//            parent = (HasCurrentFragment) activity;
//            parent.setCurrent(this);
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement HasCurrentFragment");
//        }
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //CompletedWorkout completedWorkout = getCompletedWorkout();

            long sessionId = getArguments().getLong("sessionId", -1);
            session = Session.load(Session.class, sessionId);
            completedSession = new CompletedSession();
            completedSession.session = session;

            Log.d(LOG_TAG, "order:  " + completedSession.order);
            CompletedWorkout completedWorkout = CompletedWorkout.load(CompletedWorkout.class, getArguments().getLong("completedWorkoutId", -1));
            completedSession.completedWorkout = completedWorkout;

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_track_session, container, false);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment logSetFragment = new LogSetFragment();
        ft.add(R.id.function_card, logSetFragment);
        ft.commit();


        setNum = 0;

        firstSet = true;

//        repsInput = (EditText) view.findViewById(R.id.completed_reps_input);
//        weightInput = (EditText) view.findViewById(R.id.completed_weight_input);

//        lastWorkoutLv = (ListView) view.findViewById(R.id.last_workout_info_lv);

//        WorkoutInfoAdapter lastWorkoutAdapter = new WorkoutInfoAdapter(getActivity(), completedSession);
//        lastWorkoutLv.setAdapter(lastWorkoutAdapter);

        currentWorkoutLv = (ListView) view.findViewById(R.id.current_workout_info_lv);
        currentWorkoutAdapter = new WorkoutInfoAdapter(getActivity());
        currentWorkoutLv.setAdapter(currentWorkoutAdapter);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.function_card, new LogSetFragment());
//        fragmentTransaction.commit();

        //TODO: show target reps

        return view;

    }

    public void replaceToTimerFragment(){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment timerFragment = TimerFragment.newInstance(session.rest);
        ft.replace(R.id.function_card, timerFragment);
        ft.commit();

    }

    public void replaceToLogSetFragment(){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment logSetFragment = new LogSetFragment();
        ft.replace(R.id.function_card, logSetFragment);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(RestFinished event){
        replaceToLogSetFragment();
    }

    public void onEvent(SetCompleted event){
        if(firstSet){
            completedSession.order = TrackWorkoutAct.getSessionOrder();
            completedSession.save();
            firstSet = false;
            currentWorkoutAdapter.setCompletedSession(completedSession);
        }





        CompletedSet completedSet = new CompletedSet();
        completedSet.reps = event.reps;
        completedSet.weight = event.weight;
        completedSet.order = setNum;
        completedSet.completedSession = completedSession;
        completedSet.save();

        currentWorkoutAdapter.update();

        replaceToTimerFragment();
    }



}
