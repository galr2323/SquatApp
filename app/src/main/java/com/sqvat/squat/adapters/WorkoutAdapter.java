package com.sqvat.squat.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.activities.ExerciseActivity;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Workout;

import java.util.List;

/**
 * Created by GAL on 9/13/2014.
 */
public class WorkoutAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private Workout workout;
    private List<Session> sessions;

    final static String LOG_TAG = "Workput Adapter";

    private class ViewHolder {
        TextView exerciseName;
        TextView info;
    }

    public WorkoutAdapter(Context context, Workout workout) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.workout = workout;
        this.sessions = workout.getSessions();
    }

    public int getCount() {
        return sessions.size();
    }

    public Session getItem(int position) {
        return sessions.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fancy_li, null);
            holder.exerciseName = (TextView) convertView.findViewById(R.id.fancy_li_header);
            holder.info = (TextView) convertView.findViewById(R.id.fancy_li_sub);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Session session = sessions.get(position);
        holder.exerciseName.setText(session.exercise.name);

//        Log.d(LOG_TAG, "amount of sets: " + String.valueOf(sessions.get(position).sets));
//        for(Set set : session.getSets())
//            Log.d(LOG_TAG, set.toString());


        holder.info.setText(session.toString());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("exerciseId", session.exercise.getId().intValue());

                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void update(){
        this.sessions = workout.getSessions();
        notifyDataSetChanged();
    }
}
