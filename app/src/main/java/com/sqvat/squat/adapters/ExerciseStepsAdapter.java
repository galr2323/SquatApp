package com.sqvat.squat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.ExerciseStep;
import com.sqvat.squat.data.Muscle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by GAL on 9/26/2014.
 */


public class ExerciseStepsAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    private Exercise exercise;
    private List<ExerciseStep> exerciseSteps;
    TextView description;

    public ExerciseStepsAdapter(Context context, Exercise exercise) {
        inflater = LayoutInflater.from(context);

        this.exercise = exercise;

        exerciseSteps = exercise.getSteps();
        Collections.sort(exerciseSteps, new Comparator<ExerciseStep>() {
            @Override
            public int compare(final ExerciseStep object1, final ExerciseStep object2) {
                return object1.order - object2.order;
            }
        });

    }

    public int getCount() {
        return exerciseSteps.size();
    }

    public ExerciseStep getItem(int position) {
        return exerciseSteps.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        description = null;
        if(convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);

            description = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(description);
        } else {
            description = (TextView) convertView.getTag();
        }

        description.setText(String.valueOf(position + 1) + ". " + getItem(position).description);
        return convertView;
    }

}
