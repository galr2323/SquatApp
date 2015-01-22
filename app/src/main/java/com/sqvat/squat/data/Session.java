package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Sessions")
public class Session extends Model {

    @Column(name = "Workout")
    public Workout workout;

    //0 based
    @Column(name = "TargetOrder")
    public int targetOrder;

    @Column(name = "Exercise")
    public Exercise exercise;

    @Column(name = "SetsCol")
    public int sets;

    @Column(name = "targetReps")
    public int targetReps;

    //seconds
    @Column(name = "Rest")
    public int rest;

    public Session() {
        super();
    }

    public String toString(){
        return sets + " sets • " + targetReps + " reps • " + rest + " seconds of rest";
    }


}
