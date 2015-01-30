package com.sqvat.fit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.fit.R;
import com.sqvat.fit.data.CompletedWorkout;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

    public HistoryAdapter(Context context, List<CompletedWorkout> completedWorkouts) {
        inflater = LayoutInflater.from(context);
        Collections.reverse(completedWorkouts);
        this.completedWorkouts = completedWorkouts;
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
        Date time = completedWorkouts.get(position).time;
        String timeStr = new SimpleDateFormat("'On' EEE, MMM dd yyyy 'at' HH:mm").format(time);
        holder.date.setText(timeStr);
        return convertView;
    }
}
