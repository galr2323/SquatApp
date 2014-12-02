package com.sqvat.squat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sqvat.squat.data.CompletedSession;
import com.sqvat.squat.data.CompletedWorkout;
import com.sqvat.squat.data.Session;
import com.sqvat.squat.data.Set;

import java.util.List;

/**
 * Created by GAL on 12/2/2014.
 */
public class DetailedHistoryAdapter extends BaseAdapter {
    private class ViewHolder {
        TextView order;
        EditText repsInput;
        Button duplicateBtn;

    }

    private LayoutInflater inflater;
    private List<CompletedSession> completedSessions;
    private ViewHolder holder;



    public DetailedHistoryAdapter(Context context, CompletedWorkout completedWorkout) {
        inflater = LayoutInflater.from(context);
        this.completedSessions = completedWorkout.getCompletedSessions();
    }

    public int getCount() {
        return sets.size();
    }

    public Set getItem(int position) {
        return sets.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.config_set_li, null);
            holder.order = (TextView) convertView.findViewById(R.id.set_order);
            holder.repsInput = (EditText) convertView.findViewById(R.id.reps_input);
//            holder.saveReps = (Button) convertView.findViewById(R.id.save_reps);
            holder.duplicateBtn = (Button) convertView.findViewById(R.id.duplicate_btn);
            convertView.setTag(R.id.sets_adapter_holder, holder);
            convertView.setTag(R.id.sets_adapter_position, position);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.sets_adapter_holder);
        }
        //final Set set = getItem(position);

        holder.order.setText("Set " + (position + 1) + ":");

        holder.repsInput.setText("10");
//        holder.saveReps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Editable repsInputEditable = holder.repsInput.getText();
//                String repsInputStr = String.valueOf(repsInputEditable);
//                int reps = Integer.parseInt(repsInputStr);
//                Log.d("YOLO", repsInputStr);
//
//                //some problem here
//                try {
//                    sets.get(position).targetReps = reps;
//
//                }
//                catch (NullPointerException exception){
//                    sets.get(position).targetReps = 10;
//                    Log.d("After sving", "caught null pointer exception");
//                }
//                Log.d("After svaing", sets.get(position).toString());
//            }
//        });
        holder.repsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String repsInputStr = String.valueOf(s);
                int reps = Integer.parseInt(repsInputStr);
                Log.d("YOLO", repsInputStr);

                //some problem here
                try {
                    sets.get(position).targetReps = reps;

                }
                catch (NullPointerException exception){
                    sets.get(position).targetReps = 10;
                    Log.d("After editTextChange", "caught null pointer exception");
                }
                Log.d("After editTextChange", sets.get(position).toString());
            }
        });

        holder.duplicateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //duplicate item
                Set setToAdd = getItem((Integer) v.getTag(R.id.sets_adapter_position));
                addSet(setToAdd);
            }
        });

        return convertView;
    }
}
