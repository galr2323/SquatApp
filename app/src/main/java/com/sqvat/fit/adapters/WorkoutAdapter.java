package com.sqvat.fit.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.fit.R;
import com.sqvat.fit.activities.ExerciseActivity;
import com.sqvat.fit.activities.UpdateSessionActivity;
import com.sqvat.fit.data.Session;
import com.sqvat.fit.data.Workout;

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

    @Override
    public boolean hasStableIds() {
        return true;
    }

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

    public View getView(final int position, View convertView, ViewGroup parent) {
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
                intent.putExtra("exerciseId", session.exercise.getId());

                context.startActivity(intent);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(LOG_TAG, "long clicked on: " + position);
                Intent intent = new Intent(context, UpdateSessionActivity.class);
                intent.putExtra("sessionId", session.getId());
                context.startActivity(intent);
                return true;
            }
        });
        return convertView;
    }

    public void update(){
        this.sessions = workout.getSessions();
        notifyDataSetChanged();
    }
}
