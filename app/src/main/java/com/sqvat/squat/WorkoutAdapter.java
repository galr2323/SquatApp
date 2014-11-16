package com.sqvat.squat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Set;
import com.sqvat.squat.data.Workout;

import java.util.List;

/**
 * Created by GAL on 9/13/2014.
 */
public class WorkoutAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Session> sessions;
    final static String LOG_TAG = "Workput Adapter";

    private class ViewHolder {
        TextView exerciseName;
        TextView info;
    }

    public WorkoutAdapter(Context context, Workout workout) {
        inflater = LayoutInflater.from(context);
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
        Session session = sessions.get(position);
        holder.exerciseName.setText(session.exercise.name);

        //TODO: fix this:   getSets() length is 0 ??! wtf
        Log.d(LOG_TAG, "amount of sets: " + String.valueOf(sessions.get(position).getSets().size()));
        for(Set set : session.getSets())
            Log.d(LOG_TAG, set.toString());


        holder.info.setText(String.valueOf(session.getSets().size()) + " sets with " + session.rest + " seconds of rest");
        return convertView;
    }

    public void updateWorkout(){
        notifyDataSetChanged();
    }
}
