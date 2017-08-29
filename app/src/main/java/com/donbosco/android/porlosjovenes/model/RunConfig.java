package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RunConfig implements Serializable
{
    private static final long serialVersionUID = -7315471573977186057L;

    @SerializedName("IdSponsor")
    private long sponsorId;
    @SerializedName("Sponsor")
    private String sponsorName;
    @SerializedName("UrlImagen")
    private String sponsorImage;
    @SerializedName("UrlLogo")
    private String sponsorLogo;
    @SerializedName("ValorKms")
    private float amountPerDistance;
    @SerializedName("Kms")
    private float distance;

    public long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorImage() {
        return sponsorImage;
    }

    public void setSponsorImage(String sponsorImage) {
        this.sponsorImage = sponsorImage;
    }

    public String getSponsorLogo() {
        return sponsorLogo;
    }

    public void setSponsorLogo(String sponsorLogo) {
        this.sponsorLogo = sponsorLogo;
    }

    public float getAmountPerDistance() {
        return amountPerDistance;
    }

    public void setAmountPerDistance(float amountPerDistance) {
        this.amountPerDistance = amountPerDistance;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
