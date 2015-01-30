package com.sqvat.fit.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by GAL on 1/24/2015.
 */
@Table(name = "Routines")
public class Routine extends Model {

    @Column(name = "Name")
    public String name;

    public Routine(String name) {
        super();
        this.name = name;
    }

    public Routine() {
        super();
    }

    public static List<Routine> getAll() {
        return new Select()
                .from(Routine.class)
                .execute();
    }

    public List<Workout> getWorkouts(){
        return getMany(Workout.class, "Routine");
    }

}
