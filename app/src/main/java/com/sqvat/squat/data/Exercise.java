package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Exercises")
public class Exercise extends Model {

    @Column(name = "Name")
    public String name;

    public Exercise(){
        super();
    }

    public Exercise(String name){
        this.name = name;
    }

    public static List<Exercise> getAll(){
        return new Select()
                .from(Exercise.class)
                .execute();
    }

    public List<Muscle> getMuscles(){
        return getMany(Muscle.class, "Exercise");
    }

    public String getMusclesStr(){
        String musclesStr = "";
        final List<Muscle> muscles = this.getMuscles();
        for(int i = 0; i < muscles.size(); i++){
            if(i != 0)
                musclesStr += " • ";

            musclesStr += muscles.get(i).name;
        }

        return musclesStr;
    }

    public List<ExerciseStep> getSteps(){
        return getMany(ExerciseStep.class, "Exercise");
    }


}
