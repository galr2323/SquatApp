package com.sqvat.squat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.data.CompletedWorkout;

import java.util.List;

/**
* Created by GAL on 9/13/2014.
*/
public class HistoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CompletedWorkout> completedWorkouts;

    private class ViewHolder {
        TextView header;
        TextView date;
    }

    public HistoryAdapter(Context context, List<CompletedWorkout> CompletedWorkouts) {
        inflater = LayoutInflater.from(context);
        completedWorkouts = CompletedWorkouts;
    }

    public int getCount() {
        return completedWorkouts.size();
    }

    public CompletedWorkout getItem(int position) {
        return completedWorkouts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fancy_li, null);
            holder.header = (TextView) convertView.findViewById(R.id.fancy_li_header);
            holder.date = (TextView) convertView.findViewById(R.id.fancy_li_sub);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.header.setText("Workout " + completedWorkouts.get(position).workout.name);
        holder.date.setText("On " + completedWorkouts.get(position).time);
        return convertView;
    }
}
