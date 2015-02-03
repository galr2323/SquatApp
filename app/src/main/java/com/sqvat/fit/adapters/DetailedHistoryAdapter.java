package com.sqvat.fit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqvat.fit.R;
import com.sqvat.fit.data.CompletedSession;
import com.sqvat.fit.data.CompletedSet;
import com.sqvat.fit.data.CompletedWorkout;

import java.util.List;

/**
* Created by GAL on 12/2/2014.
*/
public class DetailedHistoryAdapter extends BaseAdapter {
    private class ViewHolder {
        TextView name;
        LinearLayout sets;

    }

    private Context context;
    private LayoutInflater inflater;
    private List<CompletedSession> completedSessions;

    private final static String LOG_TAG = "DetailedHistoryAdapter";



    public DetailedHistoryAdapter(Context context, CompletedWorkout completedWorkout) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.completedSessions = completedWorkout.getCompletedSessions();
    }

    public int getCount() {
        Log.d(LOG_TAG, "number of completed sessions:" + completedSessions.size());
        return completedSessions.size();
    }

    public CompletedSession getItem(int position) {
        return completedSessions.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        CompletedSession completedSession = getItem(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.detailed_history_li, null);

            holder.name = (TextView) convertView.findViewById(R.id.detailed_history_exercise_name);
            holder.sets = (LinearLayout) convertView.findViewById(R.id.detailed_history_sets);

            for (CompletedSet completedSet : completedSession.getCompletedSets()){
                View view = inflater.inflate(R.layout.simple_li, null);

                TextView textView = (TextView) view.findViewById(R.id.li_text);
                textView.setText(completedSet.getDetailedString());

                holder.sets.addView(view);
            }

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.name.setText(completedSession.session.exercise.name);



        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
