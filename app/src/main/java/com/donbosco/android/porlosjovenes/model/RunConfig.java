package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

public class RunConfig
{
    @SerializedName("IdSponsor")
    private long sponsorId;
    @SerializedName("Sponsor")
    private String sponsorName;
    @SerializedName("UrlImagen")
    private String sponsorImage;
    @SerializedName("ValorKms")
    private double amountPerDistance;

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

    public double getAmountPerDistance() {
        return amountPerDistance;
    }

    public void setAmountPerDistance(double amountPerDistance) {
        this.amountPerDistance = amountPerDistance;
    }
}
