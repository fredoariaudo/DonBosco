package com.donbosco.android.porlosjovenes.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.donbosco.android.porlosjovenes.application.PljApplication;

/**
 * Created by pablo on 5/1/18.
 */

public class LocalIdManager {


    private static final String ID = "id";
    private static final String LAST_SENT = "lastSend";

    private static SharedPreferences sharedPreference;


    private static SharedPreferences getSharedPreference() {

        if (sharedPreference ==  null) {
            sharedPreference = PljApplication.getAppContext().getSharedPreferences("com.donbosco.android.porlosjovenes.application", Context.MODE_PRIVATE);
        }

        return  sharedPreference;
    }

    public static String get(String key) {
        return  getSharedPreference().getString(key,null);
    }

    public static int getCurrentId() {
        return getSharedPreference().getInt(ID,0);
    }

    public static int getLastSent() {
        return getSharedPreference().getInt(LAST_SENT,-1);
    }

    public static void setLastSent(int id) {
        getSharedPreference().edit().putInt(LAST_SENT,id).apply();
    }

    public static int generateNextId() {
        int currentId = getCurrentId();
        getSharedPreference().edit().putInt(ID,currentId + 1).apply();
        return currentId + 1;
    }

}
