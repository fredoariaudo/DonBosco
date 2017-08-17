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
}
