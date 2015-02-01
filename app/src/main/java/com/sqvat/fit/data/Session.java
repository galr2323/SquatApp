package com.sqvat.fit.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

    @Column(name = "target")
    public int target;

    //seconds
    @Column(name = "Rest")
    public int rest;

    public Session() {
        super();
    }

    public Session(Workout workout, int targetOrder, Exercise exercise, int sets, int target, int rest) {
        this.workout = workout;
        this.targetOrder = targetOrder;
        this.exercise = exercise;
        this.sets = sets;
        this.target = target;
        this.rest = rest;
    }

    public String toString(){
        return sets + " sets • " + target + " reps • " + rest + " seconds of rest";
    }


}
