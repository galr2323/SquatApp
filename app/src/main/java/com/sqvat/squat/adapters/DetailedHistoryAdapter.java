package com.sqvat.squat.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.data.CompletedWorkout;
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
    private ViewHolder holder;

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

        holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.detailed_history_li, null);

            holder.name = (TextView) convertView.findViewById(R.id.detailed_history_exercise_name);
            holder.sets = (LinearLayout) convertView.findViewById(R.id.sets);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag(R.id.sets_adapter_holder);
        }

        CompletedSession completedSession = getItem(position);
        holder.name.setText(completedSession.session.exercise.name);

        ListAdapter adapter = new ArrayAdapter<CompletedSet>(context, R.layout.simple_li,R.id.li_text, completedSession.getCompletedSets());
        for (CompletedSet completedSet : completedSession.getCompletedSets()){
            View view = inflater.inflate(R.layout.simple_li, null);

            TextView textView = (TextView) view.findViewById(R.id.li_text);
            textView.setText(completedSet.toString());

            holder.sets.addView(view);
        }

        return convertView;
    }
}
