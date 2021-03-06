package com.sqvat.squat.fragments;


import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.activities.TrackWorkoutAct;
import com.sqvat.squat.events.RestFinished;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    //TODO: replace seconds of rest in Session to type short
    private static final String SECONDS = "seconds";

    private int seconds;
    int position;
    Button completeRest;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param seconds Parameter 1.
     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(int seconds, int position) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putInt(SECONDS, seconds);
        args.putInt("position", position);
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
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_timer, container, false);
        final TextView secondsTv = (TextView) view.findViewById(R.id.seconds);
        completeRest = (Button) view.findViewById(R.id.complete_rest);

        secondsTv.setText(String.valueOf(seconds));

        final CountDownTimer timer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsTv.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().postSticky(new RestFinished(position));
                MediaPlayer mPlayer = MediaPlayer.create(getActivity(), R.raw.timer_finish);
//                mPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                mPlayer.start();

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.ic_action_workout_now)
                                .setContentTitle("Rest finished")
                                .setContentText("It's time for your next set")
                                .setPriority(NotificationCompat.PRIORITY_MAX);

// Creates an explicit intent for an Activity in your app
                Intent notificationIntent = new Intent(getActivity(), TrackWorkoutAct.class);

                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,
                        notificationIntent, 0);


                mBuilder.setContentIntent(contentIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(1, mBuilder.build());
            }
        };
        timer.start();


        completeRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                EventBus.getDefault().postSticky(new RestFinished(position));

            }
        });

        return view;
    }

//    public interface OnTimerFinishListener {
//        public void onFinish();
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            callBack = (OnTimerFinishListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnTimerFinishListener");
//        }
//    }


}
