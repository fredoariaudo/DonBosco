package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

public class Sponsor
{
    @SerializedName("IdSponsor")
    private long id;
    @SerializedName("Nombre")
    private String name;
    @SerializedName("Url")
    private String url;
    @SerializedName("UrlLogo")
    private String logo;
    @SerializedName("UrlImagen")
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
