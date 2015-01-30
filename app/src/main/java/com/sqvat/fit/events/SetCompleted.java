package com.sqvat.fit.events;

/**
 * Created by GAL on 1/1/2015.
 */
public class SetCompleted {
    public final int reps;
    public final double weight;
    public final int position;

    public SetCompleted(int reps, double weight, int position) {
        this.reps = reps;
        this.weight = weight;
        this.position = position;
    }
}
