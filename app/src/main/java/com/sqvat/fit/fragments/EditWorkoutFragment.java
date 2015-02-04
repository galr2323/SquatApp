package com.sqvat.fit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.shamanland.fab.FloatingActionButton;
import com.sqvat.fit.R;
import com.sqvat.fit.activities.ChooseExerciseActivity;
import com.sqvat.fit.adapters.WorkoutAdapter;
import com.sqvat.fit.events.WorkoutAdded;
import com.sqvat.fit.events.WorkoutEdited;

import de.greenrobot.event.EventBus;


public class EditWorkoutFragment extends WorkoutFragment {

//    private long workoutId;
//    private Workout workout;
//
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param workoutId Parameter 1.
//     * @return A new instance of fragment EditWorkoutFragment.
//     */
//
//    public static EditWorkoutFragment newInstance(long workoutId) {
//        EditWorkoutFragment fragment = new EditWorkoutFragment();
//        Bundle args = new Bundle();
//        args.putLong("workoutId", workoutId);
//        fragment.setArguments(args);
//        return fragment;
//    }
//    public EditWorkoutFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            workoutId = getArguments().getLong("workoutId");
//            workout = Workout.load(Workout.class, workoutId);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        adapter = new WorkoutAdapter(getActivity(), workout);
        final ListView sessionsList = (ListView) view.findViewById(R.id.sessions_list);
        sessionsList.setAdapter(adapter);

//        sessionsList.setLongClickable(true);
//        sessionsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ConfigSessionActivity.class);
//                intent.putExtra("sessionId", adapter.getItem(position).getId());
//                startActivityForResult(intent, 0);
//                return true;
//            }
//        });


        TextView name = (TextView) view.findViewById(R.id.workout_name);
        name.append(workout.name);

        TextView info = (TextView) view.findViewById(R.id.workout_info);
        info.setText(workout.getSessions().size() + " exercises * " + workout.totalAmountOfSets() + " sets");


        FloatingActionButton addExercise = (FloatingActionButton) view.findViewById(R.id.workout_fab);
        addExercise.setImageResource(R.drawable.ic_action_add);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseExerciseActivity.class);
                intent.putExtra("workoutId", workout.getId());
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }


//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
//                .getMenuInfo();
//        if (item.getTitle() == "Edit Item") {
//            long rowId = info.id;
//            DialogFragment_Item idFragment = new DialogFragment_Item();
//            idFragment.show(getFragmentManager(), "dialog");
//        } else if (item.getTitle() == "Delete Item") {
//            mDbHelper.deleteItem(info.id);
//            return true;
//        }
//        return super.onContextItemSelected(item);
//    }

    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        adapter.update();
        EventBus.getDefault().postSticky(new WorkoutEdited());

    }


}
