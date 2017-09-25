package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsResponse
{
    @SerializedName("EventoInscripto")
    private long signedEvent;
    @SerializedName("Eventos")
    private ArrayList<Event> events = new ArrayList<>();

    public long getSignedEvent() {
        return signedEvent;
    }

    public void setSignedEvent(long signedEvent) {
        this.signedEvent = signedEvent;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}