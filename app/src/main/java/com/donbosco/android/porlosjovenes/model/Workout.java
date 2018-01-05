package com.donbosco.android.porlosjovenes.model;

import java.io.Serializable;

public class Workout implements Serializable
{
    private static final long serialVersionUID = 1951151032906319932L;

    private float distance;
    private long time;
    private float collected;
    private int localId;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getCollected() {
        return collected;
    }

    public void setCollected(float collected) {
        this.collected = collected;
    }
}
