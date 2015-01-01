package com.sqvat.squat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.shamanland.fab.FloatingActionButton;
import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackSessionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TrackSessionFragment extends Fragment {

    private Session session;
    private CompletedSession completedSession;
    private EditText repsInput;
    private EditText weightInput;
    //0 based
    private int setNum;
    private boolean firstSet;

    private final String LOG_TAG = "track session fragment";
    HasCurrentFragment parent;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param session
     * @return A new instance of fragment TrackSessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackSessionFragment newInstance(Session session, CompletedWorkout completedWorkout) {
        TrackSessionFragment fragment = new TrackSessionFragment();
        Bundle args = new Bundle();
        args.putLong("sessionId", session.getId());
        args.putLong("completedWorkoutId", completedWorkout.getId());

        fragment.setArguments(args);
        return fragment;

        //sets the args currectly
    }
    public TrackSessionFragment() {
        // Required empty public constructor
    }

    public interface HasCurrentFragment {
        public void setCurrent(TrackSessionFragment fragment);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            parent = (HasCurrentFragment) activity;
            parent.setCurrent(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement HasCurrentFragment");
        }

    }

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

        View view = inflater.inflate(R.layout.fragment_track_session, container, false);

        setNum = 0;

        firstSet = true;

        repsInput = (EditText) view.findViewById(R.id.completed_reps_input);
        weightInput = (EditText) view.findViewById(R.id.completed_weight_input);

        final ListView lastWorkoutLv = (ListView) view.findViewById(R.id.last_workout_info_lv);

//        WorkoutInfoAdapter lastWorkoutAdapter = new WorkoutInfoAdapter(getActivity(), completedSession);
//        lastWorkoutLv.setAdapter(lastWorkoutAdapter);

        final ListView currentWorkoutLv = (ListView) view.findViewById(R.id.current_workout_info_lv);



//        int targetReps = session.getSets().get(0).targetReps;
//        EditText reps = (EditText) view.findViewById(R.id.completed_reps_input);
//        reps.setHint(targetReps);

        Button completeSet = (Button) view.findViewById(R.id.complete_set);
        completeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstSet){
                    completedSession.order = TrackWorkoutAct.getSessionOrder();
                    completedSession.save();
                    firstSet = false;
                }

                final WorkoutInfoAdapter currentWorkoutAdapter = new WorkoutInfoAdapter(getActivity(), completedSession);
                currentWorkoutLv.setAdapter(currentWorkoutAdapter);

                //TODO: check that input isnt null
                int reps = Integer.parseInt(repsInput.getText().toString());
                Log.d(LOG_TAG, "reps: " + reps);
                int weight = Integer.parseInt(weightInput.getText().toString());
                Log.d(LOG_TAG, "weight: " + weight);

                CompletedSet completedSet = new CompletedSet();
                completedSet.reps = reps;
                completedSet.weight = weight;
                completedSet.order = setNum;
                completedSet.completedSession = completedSession;
                completedSet.save();

                currentWorkoutAdapter.update();

                replaceToTimerFragment();


            }
        });


        return view;

    }

    private void setRepsHint(int i){
        EditText reps = (EditText) getView().findViewById(R.id.completed_reps_input);
        reps.setHint(session.targetReps);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public void replaceToTimerFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.log_set, TimerFragment.newInstance(session.rest));
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void replaceToLogSetFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.log_set,new LogSetFragment());
        transaction.addToBackStack(null);

        transaction.commit();
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


}
