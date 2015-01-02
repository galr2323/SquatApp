package com.sqvat.squat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sqvat.squat.R;
import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedSet;
import com.sqvat.squat.data.Exercise;
import com.sqvat.squat.data.Muscle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by GAL on 9/13/2014.
 */
public class WorkoutInfoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private CompletedSession completedSession;
    private List<CompletedSet> completedSets;

    private class ViewHolder {
        TextView setNum;
        TextView mainInfo;
    }

    public WorkoutInfoAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.completedSets = new ArrayList<CompletedSet>();
    }

    public WorkoutInfoAdapter(Context context, CompletedSession completedSession) {
        this.inflater = LayoutInflater.from(context);
        this.completedSession = completedSession;

        this.completedSets = completedSession.getCompletedSets();

        if(completedSets == null) {
            completedSets = new ArrayList<CompletedSet>();
        }
        else {
            Collections.sort(completedSets, new Comparator<CompletedSet>() {
                @Override
                public int compare(CompletedSet lhs, CompletedSet rhs) {
                    return lhs.order > rhs.order ? lhs.order : rhs.order;
                }
            });
        }


    }

    public int getCount() {
        if(completedSets != null){
            return completedSets.size();
        }

        return 0;

    }

    public CompletedSet getItem(int position) {
        return completedSets.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.workout_info_li, null);
            holder.setNum = (TextView) convertView.findViewById(R.id.workout_info_num);
            holder.mainInfo = (TextView) convertView.findViewById(R.id.workout_info_main);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CompletedSet completedSet = getItem(position);

        holder.setNum.setText(String.valueOf(completedSet.order));

        //TODO: print KG or LB according to the users settings
        holder.mainInfo.setText(completedSet.reps + " REPS x " + completedSet.weight + "KG");
        return convertView;
    }

    public void update(){
        completedSets = completedSession.getCompletedSets();
        notifyDataSetChanged();
    }

    public void setCompletedSession(CompletedSession completedSession){
        this.completedSession = completedSession;
    }
}
