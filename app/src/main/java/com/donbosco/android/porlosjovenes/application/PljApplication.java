package com.donbosco.android.porlosjovenes.application;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.donbosco.android.porlosjovenes.receivers.NetworkStateReceiver;

public class PljApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, intentFilter);
    }
}
