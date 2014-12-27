package com.sqvat.squat;


import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    //TODO: replace seconds of rest in Session to type short
    private static final String SECONDS = "seconds";

    private int seconds;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param seconds Parameter 1.
     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(int seconds) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putInt(SECONDS, seconds);
        fragment.setArguments(args);
        return fragment;
    }

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            seconds = getArguments().getInt(SECONDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_timer, container, false);
        final TextView secondsTv = (TextView) view.findViewById(R.id.seconds);

        new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsTv.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        };
        return view;
    }


}
