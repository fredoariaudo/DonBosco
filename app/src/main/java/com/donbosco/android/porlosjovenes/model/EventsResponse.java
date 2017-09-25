package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsResponse
{
    @SerializedName("EventoInscripto")
    private long singedEvent;
    @SerializedName("Eventos")
    private ArrayList<Event> events = new ArrayList<>();

    public long getSingedEvent() {
        return singedEvent;
    }

    public void setSingedEvent(long singedEvent) {
        this.singedEvent = singedEvent;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}