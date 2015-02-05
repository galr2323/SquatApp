package com.sqvat.fit.data;

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
    public static final int TARGET_REPS = 0;
    public static final int TARGET_TIME = 1;
    public static final int TARGET_DISTANCE = 2;
    public static final String[] TARGETS_STR = {"Reps","Time","Distance"};

    public static final int WORK_WEIGHT = 0;
    public static final int WORK_TIME = 1;
    public static final int WORK_REPS = 2;
    public static final int WORK_DISTANCE = 3;

    @Column(name = "Name")
    public String name;

    @Column(name = "VideoId")
    public String videoId;

    @Column(name = "Custom")
    public boolean custom;

    @Column(name = "TargetType")
    public int targetType;

    @Column(name = "WorkoType")
    public int workType;

    @Column(name = "Recommended")
    public boolean recommended;

    @Column(name = "Compound")
    public boolean compound;

    public Exercise(){
        super();
    }

    public Exercise(String name, String videoId) {
        this.name = name;
        this.videoId = videoId;
        this.custom = false;
    }

    public Exercise(String name){
        this.name = name;
        this.custom = true;
    }

    public Exercise(JSONObject jsonObject){
        try {
            this.name = jsonObject.getString("name");
            this.videoId = jsonObject.getString("videoId");

            if(jsonObject.has("recommended")) {
                this.recommended = jsonObject.getBoolean("recommended");
            }
            else {
                this.recommended = false;
            }

            if(jsonObject.has("compound")){
                this.compound = jsonObject.getBoolean("compound");
            }
            else {
                this.compound = false;
            }

            if(jsonObject.has("targetType") && jsonObject.has("workType")){
                this.targetType = jsonObject.getInt("targetType");
                this.workType = jsonObject.getInt("workType");
            }
            else {
                this.targetType = TARGET_REPS;
                this.workType = WORK_WEIGHT;
            }
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
