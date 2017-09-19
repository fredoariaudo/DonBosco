package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

public class WorkoutResultResponse
{
    @SerializedName("IdActividad")
    private long workoutId;

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }
}
