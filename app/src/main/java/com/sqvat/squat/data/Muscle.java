package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Muscles")
public class Muscle extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Exercise")
    public Exercise exercise;

    public Muscle(String name, Exercise exercise) {
        super();
        this.name = name;
        this.exercise = exercise;
    }

    public Muscle(String name) {
        super();
        this.name = name;
    }

    public Muscle(){
        super();
    }

    public static List<Muscle> getAll(){
        return new Select()
                .from(Muscle.class)
                .execute();
    }
}

