package com.sqvat.squat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sqvat.squat.R;
import com.sqvat.squat.adapters.WorkoutInfoAdapter;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.events.SetCompleted;

import de.greenrobot.event.EventBus;

/**
 * Created by GAL on 12/26/2014.
 */
public class LogSetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_log_set,container, false);

        //if i put it inside onClick it returns fucking null
        final EditText repsInput = (EditText) view.findViewById(R.id.completed_reps_input);
        final EditText weightInput = (EditText) view.findViewById(R.id.completed_weight_input);

        Button completeSet = (Button) view.findViewById(R.id.complete_set);
        completeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true){
                    int reps = Integer.parseInt(repsInput.getText().toString());
                    double weight = Integer.parseInt(weightInput.getText().toString());
                    if(reps != 0 && weight != 0)
                        EventBus.getDefault().post(new SetCompleted(reps, weight));
                    //the fragment isnt replacing! only moving left WTF???!
                }







            }
        });

        return view;
    }
}
