package com.sqvat.fit.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.astuetz.PagerSlidingTabStrip;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sqvat.fit.R;
import com.sqvat.fit.Util;
import com.sqvat.fit.adapters.WorkoutsPageAdapter;
import com.sqvat.fit.data.Routine;
import com.sqvat.fit.data.Workout;
import com.sqvat.fit.events.RestFinished;
import com.sqvat.fit.events.RoutineDeleted;
import com.sqvat.fit.events.WorkoutAdded;
import com.sqvat.fit.events.WorkoutEdited;

import java.util.List;

import de.greenrobot.event.EventBus;


public class EditRoutineAct extends ActionBarActivity {
 Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new EditRoutineFragment())
                    .commit();
        }
    }


    public static class EditRoutineFragment extends Fragment{
        ViewPager viewPager;
        PagerSlidingTabStrip tabs;

        private WorkoutsPageAdapter adapter;
        private static List<Workout> workouts;
        EditText addWorkoutNameEt;


        public EditRoutineFragment() {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_edit_routine, container, false);
            viewPager = (ViewPager) view.findViewById(R.id.edit_routine_pager);
            tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

            workouts = Workout.getAll();

            adapter = new WorkoutsPageAdapter(getChildFragmentManager(), getActivity(), true);
            viewPager.setAdapter(adapter);
            tabs.setViewPager(viewPager);
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            if(adapter.isEmpty()){
                tabs.setVisibility(View.GONE);
            }
            else {
                tabs.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.edit_routine, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_add_workout) {
                addWorkout();
                return true;
            }
            else if(id == R.id.action_delete_routine){
                if(Util.getUsersRoutine(getActivity()).getWorkouts().size() > 0) {
                    new MaterialDialog.Builder(getActivity())
                            .title("Delete Routine?")
                            .content("You will have to create a new routine!")
                            .positiveText("Delete")
                            .negativeText("Cancel")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    Routine routine = new Routine("Your" + (Routine.getAll().size() + 1) + "routine");
                                    routine.save();

                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    sharedPref
                                            .edit()
                                            .putLong("usersRoutineId", Routine.getAll().size())
                                            .commit();

                                    tabs.setVisibility(View.GONE);
                                    adapter.update();
                                    //EventBus.getDefault().postSticky(new RoutineDeleted());


                                    super.onPositive(dialog);
                                }
                            })
                            .show();
                }

                return true;

            }
            else if (id == android.R.id.home){
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


        private void addWorkout(){
            addWorkoutNameEt = null;
            MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Add Workout")
                    .customView(R.layout.add_workout_dialog)
                    .positiveText("Add")
                    .positiveColor(getResources().getColor(R.color.colorPrimary))
                    .negativeText("Cancel")
                    .negativeColor((getResources().getColor(R.color.colorPrimary)))
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            Editable val = addWorkoutNameEt.getText();
                            String name = val.toString();

                            Workout workout = new Workout(name, Workout.getAll().size());
                            workout.save();

//                    adapter = new WorkoutsPageAdapter(getFragmentManager(),getActivity(),true);
//                    viewPager.setAdapter(adapter);
//                    tabs.setViewPager(viewPager);
                            adapter.update();
                            tabs.setVisibility(View.VISIBLE);
//                    EventBus.getDefault().postSticky(new WorkoutAdded());
//                    EventBus.getDefault().postSticky(new WorkoutEdited());
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                        }
                    }).build();

            final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
            addWorkoutNameEt = (EditText) dialog.getCustomView().findViewById(R.id.add_workout_et);
            addWorkoutNameEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    positiveAction.setEnabled(s.toString().trim().length() > 0);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            dialog.show();
            positiveAction.setEnabled(false);






//            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//            alert.setTitle("Add Workout");
//
//            // Set an EditText view to get user input
//            final MaterialEditText input = new MaterialEditText(getActivity());
//            input.setHint("Workout name");
//            input.setPaddings(16,0,16,0);
//            input.setFloatingLabel(2);
//            input.setFloatingLabelText(input.getHint());
//            alert.setView(input);
//
//            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    Editable val = input.getText();
//                    String name = val.toString();
//
//                    Workout workout = new Workout(name, Workout.getAll().size());
//                    workout.save();
//
////                    adapter = new WorkoutsPageAdapter(getFragmentManager(),getActivity(),true);
////                    viewPager.setAdapter(adapter);
////                    tabs.setViewPager(viewPager);
//                    adapter.update();
//                    tabs.setVisibility(View.VISIBLE);
////                    EventBus.getDefault().postSticky(new WorkoutAdded());
////                    EventBus.getDefault().postSticky(new WorkoutEdited());
//
//
//                }
//            });
//
//            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    // Canceled.
//                }
//            });
//
//            alert.show();
        }

    }
}
