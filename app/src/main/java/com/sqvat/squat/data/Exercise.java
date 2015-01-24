package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;

@Table(name = "Exercises")
public class Exercise extends Model implements Comparable<Exercise> {

    @Column(name = "Name")
    public String name;

    @Column(name = "VideoId")
    public String videoId;

    public Exercise(){
        super();
    }

    public Exercise(String name, String videoId) {
        this.name = name;
        this.videoId = videoId;
    }

    public Exercise(String name){
        this.name = name;
    }

    public Exercise(JSONObject jsonObject){
        try {
            this.name = jsonObject.getString("name");
            this.videoId = jsonObject.getString("videoId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String toString(){
        return this.name;
    }


    @Override
    public int compareTo(Exercise another) {
        return Comparators.NAME.compare(this, another);
    }

    public static class Comparators {
        public static Comparator<Exercise> NAME = new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                return o1.name.compareTo(o2.name);
            }
        };

    }
}
