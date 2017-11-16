package com.donbosco.android.porlosjovenes.application;

import android.app.Application;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;

import com.donbosco.android.porlosjovenes.receivers.NetworkStateReceiver;

import java.util.Locale;

public class PljApplication extends Application
{
    private Locale locale;
    private Configuration config;

    @Override
    public void onCreate()
    {
        super.onCreate();

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, intentFilter);

        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale("es", "AR");
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
