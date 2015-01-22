package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Muscles")
public class Muscle extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Exercise")
    public Exercise exercise;

    public Muscle(String name, Exercise exercise) {
        this.name = name;
        this.exercise = exercise;
    }

    public Muscle(String name) {
        this.name = name;
    }

    public Muscle(){
        super();
    }
}

