package com.donbosco.android.porlosjovenes.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.services.LocationService;
import com.donbosco.android.porlosjovenes.util.DistanceUtils;
import com.donbosco.android.porlosjovenes.util.Helper;

public class RunActivity extends AppCompatActivity
{
    private LocationService mLocationService;
    private boolean isServiceBound;

    private ServiceConnection mServiceConnection;
    private BroadcastReceiver locationReceiver;

    private Chronometer crRunTime;
    private TextView tvRunDistanceDebug;
    private TextView tvRunDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        crRunTime = findViewById(R.id.cr_run_time);

        tvRunDistanceDebug = findViewById(R.id.tv_run_distance_debug);
        tvRunDistance = findViewById(R.id.tv_run_distance);

        FloatingActionButton fabRunFinish = findViewById(R.id.fab_run_finish);
        fabRunFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finishRun();
            }
        });

        mServiceConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder)
            {
                isServiceBound = true;
                LocationService.LocalBinder localBinder = (LocationService.LocalBinder) binder;
                mLocationService = localBinder.getService();

                /*if(mLocationService.isUserWalking())
                {
                    updateStartWalkUI();
                }*/
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
                isServiceBound = false;
            }
        };

        locationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                int resultCode = intent.getIntExtra(Helper.INTENT_EXTRA_RESULT_CODE, RESULT_CANCELED);

                if (resultCode == RESULT_OK)
                {
                    Toast.makeText(RunActivity.this, "new location", Toast.LENGTH_SHORT).show();
                    updateUi();
                    //Location userLocation = intent.getParcelableExtra(Helper.INTENT_USER_LAT_LNG);
                    //LatLng latLng = getLatLng(userLocation);
                    //updateUserMarkerLocation(latLng);
                }
            }
        };
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(Helper.ACTION_NAME_SPACE);
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver, intentFilter);
        startLocationService();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);

        if(isServiceBound)
        {
            mLocationService.stopBroadcasting();
            if(!mLocationService.isUserWalking())
            {
                stopLocationService();
            }
        }
        unbindService(mServiceConnection);
        isServiceBound = false;
    }

    private void startLocationService()
    {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        bindService(intent, mServiceConnection , Context.BIND_AUTO_CREATE);
    }

    private void stopLocationService()
    {
        Intent intentService = new Intent(this, LocationService.class);
        stopService(intentService);
    }

    private void initializeWalkService()
    {
        mLocationService.startUserWalk();
        mLocationService.startBroadcasting();
        mLocationService.startForeground();
    }

    private void stopWalkService()
    {
        mLocationService.stopUserWalk();
        mLocationService.stopNotification();
    }

    private void updateUi()
    {
        tvRunDistanceDebug.setText(""+mLocationService.distanceCovered());
        tvRunDistance.setText(getString(R.string.distance_format, DistanceUtils.meterToKm(mLocationService.distanceCovered())));
    }

    private void finishRun()
    {
        //Intent intent = new Intent(this, RunResultActivity.class);
        //startActivity(intent);

        if (isServiceBound && !mLocationService.isUserWalking())
        {
            initializeWalkService();
            crRunTime.start();
            //updateWalkPref(true);
            //updateStartWalkUI();
        }
        else if (isServiceBound && mLocationService.isUserWalking())
        {
            stopWalkService();
            crRunTime.setBase(SystemClock.elapsedRealtime());
            crRunTime.stop();
        }
    }
}
