package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CompletedSets")
public class CompletedSet extends Model {

    @Column(name = "SetCol")
    public Set set;

    @Column(name = "CompletedSession")
    public CompletedSession completedSession;

    @Column(name = "Weight")
    public int weight;

    public CompletedSet(){
        super();
    }


}
