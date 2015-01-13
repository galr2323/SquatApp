package com.sqvat.squat.data;

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
        //TODO: add option to use lbs
        return reps + " reps * " + weight + " kg";
    }


}
