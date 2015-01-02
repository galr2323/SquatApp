package com.sqvat.squat.events;

/**
 * Created by GAL on 1/1/2015.
 */
public class SetCompleted {
    public final int reps;
    public final double weight;

    public SetCompleted(int reps, double weight) {
        this.reps = reps;
        this.weight = weight;
    }
}
