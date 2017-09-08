package com.donbosco.android.porlosjovenes.model;

import java.io.Serializable;

public class Event implements Serializable
{
    private static final long serialVersionUID = 6405148119412415478L;

    private String title;
    private String date;
    private String hour;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
