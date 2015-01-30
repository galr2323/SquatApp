package com.sqvat.fit.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by GAL on 10/7/2014.
 */

@Table(name = "MusclesToExercises")
public class MusclesToExercises extends Model {

    @Column(name = "Exercise", onDelete= Column.ForeignKeyAction.CASCADE)
    Exercise exercise;

    @Column(name = "Muscle", onDelete= Column.ForeignKeyAction.CASCADE)
    Muscle muscle;

    public List<Muscle> getMuscles(){
        return getMany(Muscle.class, "MusclesToExercises");
    }
}
