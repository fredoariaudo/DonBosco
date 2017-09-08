package com.donbosco.android.porlosjovenes.model;

public class ProfileOption
{
    public static final int CHANGE_PASSWORD_ID = 1;
    public static final int LOGOUT = 2;

    private int id;
    private String title;

    public ProfileOption(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
