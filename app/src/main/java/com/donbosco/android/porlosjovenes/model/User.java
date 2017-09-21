package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
    private static final long serialVersionUID = 3606451276155312032L;

    @SerializedName("Usuario")
    private String userName;
    @SerializedName("Email")
    private String email;
    private String password;
    private ArrayList<Long> activeEvents = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Long> getActiveEvents() {
        return activeEvents;
    }

    public void setActiveEvents(ArrayList<Long> activeEvents) {
        this.activeEvents = activeEvents;
    }
}
