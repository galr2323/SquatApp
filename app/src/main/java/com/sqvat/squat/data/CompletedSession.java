package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CompletedSessions")
public class CompletedSession extends Model {

    @Column(name = "Session")
    public Session session;

    @Column(name = "CompletedWorkout")
    public CompletedWorkout completedWorkout;

    @Column(name = "OrderCol")
    public int order;

    public CompletedSession() {
        super();
    }
}
