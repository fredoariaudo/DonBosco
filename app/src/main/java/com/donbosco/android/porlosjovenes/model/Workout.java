package com.donbosco.android.porlosjovenes.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Workout extends SugarRecord implements Serializable
{
    private static final long serialVersionUID = 1951151032906319932L;

    private float distance;
    private long time;
    private float collected;
    private int synced;
    private int syncing;
    private float endLatitude;
    private float endLongitude;
    private long sponsor;
    private int type;
    private long event;
    private String email;


    public int getSyncing() {
        return syncing;
    }

    public void setSyncing(int syncing) {
        this.syncing = syncing;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(float endLatitude) {
        this.endLatitude = endLatitude;
    }

    public float getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(float endLongitude) {
        this.endLongitude = endLongitude;
    }

    public long getSponsor() {
        return sponsor;
    }

    public void setSponsor(long sponsor) {
        this.sponsor = sponsor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getEvent() {
        return event;
    }

    public void setEvent(long event) {
        this.event = event;
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

    public int getSynced() {
        return synced;
    }

    public void setSynced(int synced) {
        this.synced = synced;
    }
}
