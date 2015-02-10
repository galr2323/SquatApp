package com.sqvat.squat.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.data.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by GAL on 9/13/2014.
 */
public class ExercisesAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflater;
    private List<Exercise> exercises;
    private List<Exercise> filteredExercises;
    private ValFilter filter;

    @Override
    public Filter getFilter() {
        if(filter==null) {

            filter=new ValFilter();
        }

        return filter;
    }

    private class ViewHolder {
        TextView exerciseName;
        TextView muscles;
    }

    public ExercisesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.exercises = Exercise.getAll();
        Collections.sort(exercises);
        this.filteredExercises = Exercise.getAll();
        getFilter().filter("");
        Collections.sort(filteredExercises);
        Log.d("Exercises Adapter", "first exercise:" + exercises.get(0));
        Log.d("Exercises Adapter", "second exercise:" + exercises.get(1));
    }

    public int getCount() {
        return filteredExercises.size();
    }

    public Exercise getItem(int position) {
        return filteredExercises.get(position);
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
        Exercise exercise = getItem(position);
        holder.exerciseName.setText(exercise.name);

        holder.muscles.setText(exercise.getMusclesStr());
        return convertView;
    }



    public void update(){
        this.filteredExercises = Exercise.getAll();
        this.exercises = Exercise.getAll();
        getFilter().filter("");
        Collections.sort(filteredExercises);
        Collections.sort(exercises);
        notifyDataSetChanged();
    }

    private class ValFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                List<Exercise> tempList = new ArrayList<>();
                for(int i = 0; i < exercises.size(); i++){
                    if(exercises.get(i).name.toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(exercises.get(i));
                    }
                }
                results.count = tempList.size();
                results.values = tempList;
            }
            else {
                results.count = exercises.size();
                results.values = exercises;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredExercises = (ArrayList<Exercise>) results.values;
            notifyDataSetChanged();
        }
    }
}
