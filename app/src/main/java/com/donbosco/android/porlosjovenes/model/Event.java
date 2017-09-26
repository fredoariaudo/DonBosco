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
    @SerializedName("VigenciaDesde")
    private String startDate;
    @SerializedName("VigenciaHasta")
    private String endDate;
    @SerializedName("Ubicacion")
    private String location;
    @SerializedName("Descripcion")
    private String description;
    @SerializedName("UrlLogo")
    private String logo;
    @SerializedName("UrlImagen")
    private String image;
    @SerializedName("EsEventoEstandar")
    private boolean standardEvent;
    @SerializedName("EventoEnCurso")
    private boolean active;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStandardEvent() {
        return standardEvent;
    }

    public void setStandardEvent(boolean standardEvent) {
        this.standardEvent = standardEvent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
