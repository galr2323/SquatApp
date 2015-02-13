package com.sqvat.squat.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by GAL on 2/12/2015.
 */
@Table(name = "UserData")
public class UserData extends Model {
    @Column(name = "TotlaWeight")
    public double totalWeight;

    @Column(name = "totalReps")
    public long totalReps;

    public UserData() {
        super();
    }

    public UserData(double totalWeight, long totalReps) {
        super();
        this.totalWeight = totalWeight;
        this.totalReps = totalReps;
    }
}
