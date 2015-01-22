package com.sqvat.squat.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CompletedSets")
public class CompletedSet extends Model {

//    @Column(name = "Session")
//    public Session session;


    @Column(name = "CompletedSession")
    public CompletedSession completedSession;

    //0 based
    @Column(name = "OrderCol")
    public int order;

    @Column(name = "Weight")
    public double weight;

    @Column(name = "Reps")
    public int reps;

    public CompletedSet(){
        super();
    }

    @Override
    public String toString() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String unit = sharedPref.getString("weight_unit", "");

        return reps + " reps • " + weight + unit;
    }


}
