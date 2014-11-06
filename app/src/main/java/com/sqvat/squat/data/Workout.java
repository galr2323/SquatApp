package com.sqvat.squat.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name = "Workouts")
public class Workout extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "OrderCol")
    public String order;

    public Workout() {
        super();
    }

    public List<Session> getSessions(){
        return getMany(Session.class, "Workout");
    }

    public static List<Workout> getAll() {
        return new Select()
                .from(Workout.class)
                .execute();
    }

}