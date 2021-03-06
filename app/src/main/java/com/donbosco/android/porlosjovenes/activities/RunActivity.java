package com.donbosco.android.porlosjovenes.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.BuildConfig;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.application.AppInfo;
import com.donbosco.android.porlosjovenes.components.SmartChronometer;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.model.Workout;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.services.LocationService;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;
import com.donbosco.android.porlosjovenes.util.ResourceUtil;
import com.donbosco.android.porlosjovenes.util.WorkoutUtils;

import java.lang.ref.WeakReference;

public class RunActivity extends AppCompatActivity
{
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private final static int MESSAGE_UPDATE_UI = 0;
    private final static int UI_UPDATE_RATE = 2000; //2 seconds

    private ServiceConnection serviceConnection;
    private LocationService locationService;
    private boolean isServiceBound;
    private final Handler uiUpdateHandler = new UIUpdateHandler(this);

    private BroadcastReceiver locationStatusReceiver;
    private Snackbar sbLocationStatus;

    private CoordinatorLayout clRunContainer;
    private SmartChronometer crRunTime;
    private FloatingActionButton fabRunStartFinish;
    private TextView tvRunDistance;
    private TextView tvRunFoundsCollected;

    private WorkoutConfig workoutConfig;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        clRunContainer = findViewById(R.id.cl_run_container);

        workoutConfig = (WorkoutConfig) getIntent().getSerializableExtra(ExtraKeys.WORKOUT_CONFIG);
        if(workoutConfig != null)
        {
            ImageView ivRunBackground = findViewById(R.id.iv_run_background);
            Glide.with(this).load(workoutConfig.getSponsorImage()).into(ivRunBackground);

            ImageView ivRunWorkoutType = findViewById(R.id.iv_run_workout_type);
            ivRunWorkoutType.setImageResource(WorkoutUtils.getWorkoutIcon(workoutConfig.getWorkoutType()));
        }

        crRunTime = findViewById(R.id.cr_run_time);

        //Set drawables to distance tracked and amount collected titles
        TextView tvRunDistanceTitle = findViewById(R.id.tv_run_distance_title);
        TextView tvRunFoundsCollectedTitle = findViewById(R.id.tv_run_founds_collected_title);
        ResourceUtil.setCompoundDrawableLeft(this, tvRunDistanceTitle, ContextCompat.getColor(this, android.R.color.white), R.drawable.ic_timer_black_18dp);
        ResourceUtil.setCompoundDrawableLeft(this, tvRunFoundsCollectedTitle, ContextCompat.getColor(this, android.R.color.white), R.drawable.ic_favorite_border_black_18dp);

        //Distance traveled TextView
        tvRunDistance = findViewById(R.id.tv_run_distance);
        setDistanceText(0f);

        //Founds collected TextView
        tvRunFoundsCollected = findViewById(R.id.tv_run_founds_collected);
        setCollectedText(0f);

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
                else
                {
                    initializeWalkService();
                    updateStartRunUI();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
                isServiceBound = false;
            }
        };

        //Create location status receiver to receive location status updates
        locationStatusReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION))
                {
                    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    {
                        if(sbLocationStatus == null || !sbLocationStatus.isShownOrQueued())
                        {
                            sbLocationStatus = Snackbar.make(clRunContainer, R.string.location_disabled_message, Snackbar.LENGTH_INDEFINITE);
                            sbLocationStatus.show();
                        }
                    }
                    else
                    {
                        if(sbLocationStatus != null && sbLocationStatus.isShownOrQueued())
                        {
                            sbLocationStatus.dismiss();
                        }
                    }
                }
            }
        };

        if(!checkPermissions())
            requestPermissions();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(checkPermissions())
            startLocationService();

        registerReceiver(locationStatusReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
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
            unbindService(serviceConnection);
            isServiceBound = false;
        }
        updateStopRunUI();

        if(locationStatusReceiver != null)
            unregisterReceiver(locationStatusReceiver);
    }

    @Override
    public void onBackPressed()
    {
        if(isServiceBound && locationService.isUserWalking())
            showFinishAlert();
        else
            super.onBackPressed();
    }

    private boolean checkPermissions()
    {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions()
    {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale)
        {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_run_container), R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.allow, new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ActivityCompat.requestPermissions(RunActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            })
            .show();
        }
        else
        {
            ActivityCompat.requestPermissions(RunActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                startLocationService();
            }
            else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
            {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_run_container), R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                snackbar.show();
            }
        }
    }

    private void startLocationService()
    {
        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra(ExtraKeys.WORKOUT_CONFIG, workoutConfig);
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
        uiUpdateHandler.sendEmptyMessage(MESSAGE_UPDATE_UI);

        if(!crRunTime.isRunning())
        {
            long chronoBase = SystemClock.elapsedRealtime() - (locationService.elapsedTime() * 1000);
            crRunTime.setBase(chronoBase);
            crRunTime.start();
        }
    }

    private void updateStopRunUI()
    {
        if(uiUpdateHandler.hasMessages(MESSAGE_UPDATE_UI))
        {
            uiUpdateHandler.removeMessages(MESSAGE_UPDATE_UI);
        }
    }

    private void setDistanceText(float distance)
    {
        SpannableString spannableString = new SpannableString(getString(R.string.distance_format, distance));
        spannableString.setSpan(new RelativeSizeSpan(0.6f), spannableString.length() - 2, spannableString.length(), 0);
        tvRunDistance.setText(spannableString);
    }

    private void setCollectedText(float collected)
    {
        SpannableString spannableString = new SpannableString(getString(R.string.founds_collected_format, collected));
        spannableString.setSpan(new RelativeSizeSpan(0.6f), 0, 1, 0);
        tvRunFoundsCollected.setText(spannableString);
    }

    private void updateUI()
    {
        if (isServiceBound)
        {
            float distance = locationService.distanceCovered();
            setDistanceText(ConversionUtils.meterToKm(distance));
            setCollectedText(ConversionUtils.foundsFromInitialAndDistance(distance, workoutConfig));
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
        float distance = locationService.distanceCovered();
        float collected = ConversionUtils.foundsFromInitialAndDistance(distance, workoutConfig);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        if(collected > 0)
        {
            builder.setTitle(R.string.finish_run);
            builder.setMessage(R.string.want_finish_run);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    finishRun();
                }
            });
            builder.setNegativeButton(R.string.no, null);
        }
        else
        {
            builder.setTitle(R.string.donation);
            builder.setMessage(R.string.want_discard_run);
            builder.setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    discardRun();
                }
            });
            builder.setNegativeButton(R.string.to_continue, null);
        }


        builder.show();
    }

    private void discardRun()
    {
        stopWalkService();
        updateStopRunUI();
        finish();
    }

    private void finishRun()
    {
        stopWalkService();
        updateStopRunUI();

        float distance = locationService.distanceCovered();

        Workout workout = new Workout();
        workout.setDistance(distance);
        workout.setTime(SystemClock.elapsedRealtime() - crRunTime.getBase());
        workout.setCollected(ConversionUtils.foundsFromInitialAndDistance(distance, workoutConfig));

        Intent intent = new Intent(RunActivity.this, WorkoutResultActivity.class);
        intent.putExtra(ExtraKeys.WORKOUT, workout);
        intent.putExtra(ExtraKeys.WORKOUT_CONFIG, workoutConfig);
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
