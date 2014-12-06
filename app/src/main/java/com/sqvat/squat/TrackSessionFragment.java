package com.sqvat.squat;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;


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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param session
     * @return A new instance of fragment TrackSessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackSessionFragment newInstance(Session session) {
        TrackSessionFragment fragment = new TrackSessionFragment();
        Bundle args = new Bundle();
        args.putInt("sessionId", session.getId().intValue());

        fragment.setArguments(args);
        return fragment;
    }
    public TrackSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //CompletedWorkout completedWorkout = getCompletedWorkout();
            session = Session.load(Session.class, getArguments().getInt("sessionId"));
            completedSession = new CompletedSession();
            completedSession.session = session;
            Log.d(LOG_TAG, "order:  " + completedSession.order);
            //completedSession.completedWorkout = completedWorkout;
            //completedSession.save();
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

//        int targetReps = session.getSets().get(0).targetReps;
//        EditText reps = (EditText) view.findViewById(R.id.completed_reps_input);
//        reps.setHint(targetReps);

        Button completeSet = (Button) view.findViewById(R.id.complete_set);
        completeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstSet){
                    completedSession.order = TrackWorkoutAct.getSessionOrder();
                    firstSet = false;
                }

                //TODO: check that input isnt null
                int reps = Integer.parseInt(repsInput.getText().toString());
                int weight = Integer.parseInt(weightInput.getText().toString());

                CompletedSet completedSet = new CompletedSet();
                completedSet.reps = reps;
                completedSet.weight = weight;
                completedSet.set = session.getSets().get(setNum);
                completedSet.completedSession = completedSession;
                completedSet.save();
            }
        });


        return view;

    }

    private void setRepsHint(int i){
        int targetReps = session.getSets().get(i).targetReps;
        EditText reps = (EditText) getView().findViewById(R.id.completed_reps_input);
        reps.setHint(targetReps);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
