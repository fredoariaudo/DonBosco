package com.donbosco.android.porlosjovenes.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.IntentActions;
import com.donbosco.android.porlosjovenes.services.LocationService;
import com.donbosco.android.porlosjovenes.util.ConvertionUtils;

import java.lang.ref.WeakReference;

public class RunActivity extends AppCompatActivity
{
    private final static int MESSAGE_UPDATE_UI = 0;
    private final static int UI_UPDATE_RATE = 1000; //1 second

    private LocationService mLocationService;
    private boolean isServiceBound;

    private ServiceConnection mServiceConnection;
    private BroadcastReceiver locationReceiver;

    private Chronometer crRunTime;
    private FloatingActionButton fabRunStartFinish;
    private TextView tvRunDistanceDebug;
    private TextView tvRunDistance;
    private TextView tvRunFoundsCollected;

    private final Handler mUIUpdateHandler = new UIUpdateHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        crRunTime = findViewById(R.id.cr_run_time);

        //Distance traveled debug TextView
        tvRunDistanceDebug = findViewById(R.id.tv_run_distance_debug);

        //Distance traveled TextView
        tvRunDistance = findViewById(R.id.tv_run_distance);
        tvRunDistance.setText(getString(R.string.distance_format, 0f));

        //Founds collected TextView
        tvRunFoundsCollected = findViewById(R.id.tv_run_founds_collected);
        tvRunFoundsCollected.setText(getString(R.string.founds_collected_format, 0f));

        fabRunStartFinish = findViewById(R.id.fab_run_finish);
        fabRunStartFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startFinishRun();
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

                if(mLocationService.isUserWalking())
                {
                    updateStartRunUI();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
                isServiceBound = false;
            }
        };

        locationReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                int resultCode = intent.getIntExtra(ExtraKeys.LOCATION_SERVICE_RESULT_CODE, RESULT_CANCELED);

                if (resultCode == RESULT_OK)
                {
                    Toast.makeText(RunActivity.this, "new location", Toast.LENGTH_SHORT).show();
                    ///updateUI();
                }
            }
        };
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(IntentActions.LOCATION_UPDATED);
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
        updateStopRunUI();
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

    private void updateStartRunUI()
    {
        mUIUpdateHandler.sendEmptyMessage(MESSAGE_UPDATE_UI);
        fabRunStartFinish.setImageResource(R.drawable.ic_stop_black_24dp);
    }

    private void updateStopRunUI()
    {
        if(mUIUpdateHandler.hasMessages(MESSAGE_UPDATE_UI))
        {
            mUIUpdateHandler.removeMessages(MESSAGE_UPDATE_UI);
        }
    }

    private void updateUI()
    {
        if (isServiceBound)
        {
            tvRunDistanceDebug.setText("" + mLocationService.distanceCovered());
            tvRunDistance.setText(getString(R.string.distance_format, ConvertionUtils.meterToKm(mLocationService.distanceCovered())));
        }
    }

    private void startFinishRun()
    {
        //Intent intent = new Intent(this, RunResultActivity.class);
        //startActivity(intent);

        if (isServiceBound && !mLocationService.isUserWalking())
        {
            initializeWalkService();
            updateStartRunUI();

            crRunTime.start();
        }
        else if (isServiceBound && mLocationService.isUserWalking())
        {
            stopWalkService();
            updateStopRunUI();

            crRunTime.setBase(SystemClock.elapsedRealtime());
            crRunTime.stop();
        }
    }

    private static class UIUpdateHandler extends Handler
    {
        private final WeakReference<RunActivity> activity;

        public UIUpdateHandler(RunActivity activity)
        {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message)
        {
            if (MESSAGE_UPDATE_UI == message.what)
            {
                activity.get().updateUI();
                sendEmptyMessageDelayed(MESSAGE_UPDATE_UI, UI_UPDATE_RATE);
            }
        }
    }
}
