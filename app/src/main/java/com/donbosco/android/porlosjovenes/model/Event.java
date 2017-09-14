package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event implements Serializable
{
    private static final long serialVersionUID = 6405148119412415478L;

    @SerializedName("IdEvento")
    private long id;
    @SerializedName("Nombre")
    private String title;
    private String date;
    private String hour;
    @SerializedName("Ubicacion")
    private String location;
    private String description;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
