package com.donbosco.android.porlosjovenes.model;

import java.io.Serializable;

public class Run implements Serializable
{
    private static final long serialVersionUID = 1951151032906319932L;

    private float distance;
    private long time;
    private float collected;

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
