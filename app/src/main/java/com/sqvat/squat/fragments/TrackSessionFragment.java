package com.sqvat.squat.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.sqvat.squat.R;
import com.sqvat.squat.activities.TrackWorkoutAct;
import com.sqvat.squat.adapters.SessionInfoAdapter;
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
    SessionInfoAdapter currentWorkoutAdapter;
    int position;


    private final String LOG_TAG = "track session fragment";

    public static TrackSessionFragment newInstance(Session session, CompletedWorkout completedWorkout, int position) {
        TrackSessionFragment fragment = new TrackSessionFragment();
        Bundle args = new Bundle();
        args.putLong("sessionId", session.getId());
        args.putLong("completedWorkoutId", completedWorkout.getId());
        args.putInt("position", position);

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

            position = getArguments().getInt("position");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_track_session, container, false);
        currentWorkoutLv = (ListView) view.findViewById(R.id.current_workout_info_lv);

        setNum = 1;
        firstSet = true;

        if(savedInstanceState == null)
            replaceToLogSetFragment();
        else {
            firstSet = false;
            completedSession = CompletedSession.load(CompletedSession.class,savedInstanceState.getLong("completedSessionId"));
            currentWorkoutAdapter = new SessionInfoAdapter(getActivity(), completedSession);
            currentWorkoutLv.setAdapter(currentWorkoutAdapter);
            currentWorkoutAdapter.update();
        }




//        repsInput = (EditText) view.findViewById(R.id.completed_reps_input);
//        weightInput = (EditText) view.findViewById(R.id.completed_weight_input);

//        lastWorkoutLv = (ListView) view.findViewById(R.id.last_workout_info_lv);

//        SessionInfoAdapter lastWorkoutAdapter = new SessionInfoAdapter(getActivity(), completedSession);
//        lastWorkoutLv.setAdapter(lastWorkoutAdapter);



        //currentWorkoutLv.setAdapter(currentWorkoutAdapter);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.function_card, new LogSetFragment());
//        fragmentTransaction.commit();

        //TODO: show target reps

        return view;

    }

    private void addLogSetFragment(){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment logSetFragment = LogSetFragment.newInstance(position);
        ft.add(R.id.function_card, logSetFragment);
        ft.commit();
    }

    public void replaceToTimerFragment(){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment timerFragment = TimerFragment.newInstance(session.rest, position);
        ft.replace(R.id.function_card, timerFragment);
        ft.commit();

    }

    public void replaceToLogSetFragment(){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment logSetFragment = LogSetFragment.newInstance(position);
        ft.replace(R.id.function_card, logSetFragment);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("completedSessionId", completedSession.getId());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(RestFinished event){
        if(event.position != position)
            return;

        replaceToLogSetFragment();
    }

    public void onEvent(SetCompleted event){
        if(event.position != position)
            return;

        initIfFirstSet();

        CompletedSet completedSet = new CompletedSet();
        completedSet.reps = event.reps;
        completedSet.weight = event.weight;
        completedSet.order = setNum;
        setNum++;
        completedSet.completedSession = completedSession;
        completedSet.save();

        currentWorkoutAdapter.update();

        replaceToTimerFragment();
    }

    private void initIfFirstSet(){
        if(firstSet){
            completedSession.order = TrackWorkoutAct.getSessionOrder();
            completedSession.save();

            currentWorkoutAdapter = new SessionInfoAdapter(getActivity(), completedSession);
            currentWorkoutLv.setAdapter(currentWorkoutAdapter);

            firstSet = false;
        }
    }



}
