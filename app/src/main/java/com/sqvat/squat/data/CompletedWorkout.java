package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "CompletedWorkouts")
public class CompletedWorkout extends Model {

    @Column(name = "Workout")
    public Workout workout;

    @Column(name = "Time")
    public Date time;

    public CompletedWorkout(Workout workout) {
        this.workout = workout;
        this.time = new Date();
    }

    public CompletedWorkout(){
        super();
    }

    public static List<CompletedWorkout> getAll() {
        return new Select()
                .from(CompletedWorkout.class)
                .execute();
    }

    public List<CompletedSession> getCompletedSessions(){
        return getMany(CompletedSession.class, "CompletedWorkout");
    }

}