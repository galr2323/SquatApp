package com.sqvat.squat.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Muscle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by GAL on 9/13/2014.
 */
public class ExercisesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Exercise> exercises;

    private class ViewHolder {
        TextView exerciseName;
        TextView muscles;
    }

    public ExercisesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.exercises = Exercise.getAll();
        Collections.sort(exercises);
        Log.d("Exercises Adapter", "first exercise:" + exercises.get(0));
        Log.d("Exercises Adapter", "second exercise:" + exercises.get(1));
    }

    public int getCount() {
        return exercises.size();
    }

    public Exercise getItem(int position) {
        return exercises.get(position);
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
            holder.muscles = (TextView) convertView.findViewById(R.id.fancy_li_sub);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Exercise exercise = exercises.get(position);
        holder.exerciseName.setText(exercise.name);

        holder.muscles.setText(exercise.getMusclesStr());
        return convertView;
    }



    public void update(){
        this.exercises = Exercise.getAll();
        Collections.sort(exercises);
        notifyDataSetChanged();
    }
}
