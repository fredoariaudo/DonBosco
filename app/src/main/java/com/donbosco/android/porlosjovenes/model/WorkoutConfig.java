package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkoutConfig extends GenericResponse implements Serializable
{
    private static final long serialVersionUID = -7315471573977186057L;

    public static final int WORKOUT_TYPE_WALK = 0;
    public static final int WORKOUT_TYPE_RUN = 1;
    public static final int WORKOUT_TYPE_BIKE = 2;

    @SerializedName("IdEmpresa")
    private long sponsorId;
    @SerializedName("Empresa")
    private String sponsorName;
    @SerializedName("UrlImagen")
    private String sponsorImage;
    @SerializedName("UrlLogo")
    private String sponsorLogo;
    @SerializedName("IdEvento")
    private long eventId;
    @SerializedName("ValorKms")
    private float amountPerDistance;
    @SerializedName("Kms")
    private float distance;
    @SerializedName("ValorParticipacion")
    private float initialAmount;
    @SerializedName("DonarPorKms")
    private boolean donateByDistance;
    private int workoutType;

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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
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

    public float getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(float initialAmount) {
        this.initialAmount = initialAmount;
    }

    public boolean isDonateByDistance() {
        return donateByDistance;
    }

    public void setDonateByDistance(boolean donateByDistance) {
        this.donateByDistance = donateByDistance;
    }

    public int getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(int workoutType) {
        this.workoutType = workoutType;
    }
}
