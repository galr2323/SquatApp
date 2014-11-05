package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by GAL on 9/25/2014.
 */
@Table(name = "ExercisesSteps")
public class ExerciseStep extends Model {

    @Column(name = "Exercise")
    public Exercise exercise;

    @Column(name = "OrderCol")
    public int order;

    @Column(name = "Description")
    public String description;

    public ExerciseStep(){
        super();
    }
}
