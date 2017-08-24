package com.donbosco.android.porlosjovenes.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.model.RunConfig;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.services.LocationService;

public class SplashActivity extends AppCompatActivity
{
    private ServiceConnection serviceConnection;
    private boolean isServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        serviceConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder)
            {
                isServiceBound = true;

                LocationService.LocalBinder localBinder = (LocationService.LocalBinder) iBinder;
                LocationService locationService = localBinder.getService();

                if(locationService != null && locationService.isUserWalking() && locationService.getRunConfig() != null)
                    resumeRunActivity(locationService.getRunConfig());
                else
                    startNexActivity();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
                isServiceBound = false;
            }
        };

        bindService(new Intent(this, LocationService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(isServiceBound)
            unbindService(serviceConnection);

        isServiceBound = false;
    }

    private void startNexActivity()
    {
        User user = UserSerializer.getInstance().load(this);
        Intent intent;

        if(user == null)
            intent = new Intent(this, LoginActivity.class);
        else
            intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();
    }

    private void resumeRunActivity(RunConfig runConfig)
    {
        Intent intent = new Intent(SplashActivity.this, RunActivity.class);
        intent.putExtra(ExtraKeys.RUN_CONFIG, runConfig);
        startActivity(intent);
        finish();
    }
}
