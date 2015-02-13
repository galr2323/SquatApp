package com.sqvat.squat.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.sqvat.squat.Util;

import java.util.List;

import static com.activeandroid.Cache.getContext;


@Table(name = "Workouts")
public class Workout extends Model {

    @Column(name = "Routine")
    public Routine routine;

    @Column(name = "Name")
    public String name;

    @Column(name = "OrderCol")
    public int order;

    public Workout() {
        super();
    }

    public Workout(String name, int order) {
        this.name = name;
        this.order = order;

        this.routine = Util.getUsersRoutine(getContext());
    }

    public List<Session> getSessions(){
        return getMany(Session.class, "Workout");
    }

    public static List<Workout> getAll() {
        return new Select()
                .from(Workout.class)
                .execute();
    }


    public int totalAmountOfSets(){
        int sets = 0;
        for (Session session : this.getSessions()){
            sets += session.sets;
        }
        return sets;
    }

    public String toString(){
        return getSessions().size() + " exercises • " + totalAmountOfSets() + " sets";
    }

}
