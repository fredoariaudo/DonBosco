package com.donbosco.android.porlosjovenes.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.model.Run;
import com.donbosco.android.porlosjovenes.services.LocationService;
import com.donbosco.android.porlosjovenes.util.ConvertionUtils;

import java.lang.ref.WeakReference;

public class RunActivity extends AppCompatActivity
{
    private final static int MESSAGE_UPDATE_UI = 0;
    private final static int UI_UPDATE_RATE = 1000; //1 second

    private ServiceConnection serviceConnection;
    private LocationService locationService;
    private boolean isServiceBound;

    private Chronometer crRunTime;
    private FloatingActionButton fabRunStartFinish;
    private TextView tvRunDistanceDebug;
    private TextView tvRunDistance;
    private TextView tvRunFoundsCollected;

    private final Handler mUIUpdateHandler = new UIUpdateHandler(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState)
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

        serviceConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder)
            {
                isServiceBound = true;
                LocationService.LocalBinder localBinder = (LocationService.LocalBinder) binder;
                locationService = localBinder.getService();

                if(locationService.isUserWalking())
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
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        startLocationService();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(isServiceBound)
        {
            if(!locationService.isUserWalking())
            {
                stopLocationService();
            }
        }
        updateStopRunUI();
        unbindService(serviceConnection);
        isServiceBound = false;
    }

    @Override
    public void onBackPressed()
    {
        if(isServiceBound && locationService.isUserWalking())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.want_finish_run);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    stopWalkService();
                    updateStopRunUI();

                    Intent intent = new Intent(RunActivity.this, RunResultActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton(R.string.no, null);
            builder.show();
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void startLocationService()
    {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopLocationService()
    {
        Intent intentService = new Intent(this, LocationService.class);
        stopService(intentService);
    }

    private void initializeWalkService()
    {
        locationService.startUserWalk();
        locationService.startForeground();
    }

    private void stopWalkService()
    {
        locationService.stopUserWalk();
        locationService.stopNotification();
    }

    private void updateStartRunUI()
    {
        mUIUpdateHandler.sendEmptyMessage(MESSAGE_UPDATE_UI);
        fabRunStartFinish.setImageResource(R.drawable.ic_stop_black_24dp);

        //Set chronometer base time according if service is running or not
        long chronoBase = SystemClock.elapsedRealtime() - (locationService.elapsedTime() * 1000);
        crRunTime.setBase(chronoBase);
        crRunTime.start();
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
            float distance = locationService.distanceCovered();
            tvRunDistanceDebug.setText("" + distance);
            tvRunDistance.setText(getString(R.string.distance_format, ConvertionUtils.meterToKm(distance)));
            tvRunFoundsCollected.setText(getString(R.string.founds_collected_format, ConvertionUtils.foundsFromDistance(distance)));
        }
    }

    private void startFinishRun()
    {
        if (isServiceBound && !locationService.isUserWalking())
        {
            initializeWalkService();
            updateStartRunUI();
        }
        else if (isServiceBound && locationService.isUserWalking())
        {
            showFinishAlert();
        }
    }

    private void showFinishAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.want_finish_run);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finishRun();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

    private void finishRun()
    {
        stopWalkService();
        updateStopRunUI();

        float distance = locationService.distanceCovered();

        Run run = new Run();
        run.setDistance(distance);
        run.setTime(SystemClock.elapsedRealtime() - crRunTime.getBase());
        run.setCollected(ConvertionUtils.foundsFromDistance(distance));

        Intent intent = new Intent(RunActivity.this, RunResultActivity.class);
        intent.putExtra(ExtraKeys.RUN, run);
        startActivity(intent);
        finish();
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
