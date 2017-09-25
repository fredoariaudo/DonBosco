package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

public class Initiative
{
    @SerializedName("Imagen")
    private String image;
    @SerializedName("Nombre")
    private String title;
    @SerializedName("Descripcion")
    private String description;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
