package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Sets")
public class Set extends Model {

    @Column(name = "Session")
    public Session session;

    @Column(name = "OrderCol")
    public int order;

    @Column(name = "targetReps")
    public int targetReps;

    public Set(){
        super();
    }

    @Override
    public String toString() {
        return "SET-  in session: " + session + " , order: " + order + " , targetReps: " + targetReps;
    }


}
