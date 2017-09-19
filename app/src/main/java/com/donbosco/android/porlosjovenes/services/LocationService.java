package com.donbosco.android.porlosjovenes.services;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.activities.RunActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.IntentActions;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

public class LocationService extends Service implements LocationProvider.LocationCallback
{
    private static final int NOTIFICATION_ID = 11;

    private LocationProvider mLocationProvider;
    private boolean isUserWalking;
    private Location mCurrentLocation;
    private Location mPreviousLocation;
    private boolean isBroadcastAllow;
    private float mDistanceCovered;
    private long startTime;

    private IBinder mIBinder = new LocalBinder();

    private WorkoutConfig workoutConfig;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mLocationProvider = new LocationProvider(this, this);
        mLocationProvider.connect();
        isUserWalking = false;
        startTime = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        workoutConfig = (WorkoutConfig) intent.getSerializableExtra(ExtraKeys.WORKOUT_CONFIG);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mIBinder;
    }

    @Override
    public void handleInitialLocation(Location location)
    {
        mCurrentLocation = location;
    }

    @Override
    public void handleNewLocation(Location location)
    {
        mCurrentLocation = location;
        calculateDistance();
        broadCastLocation(mCurrentLocation);
    }

    // Assume this algorithm calculates precise distance
    private void calculateDistance()
    {
        if(isUserWalking)
        {
            if(mPreviousLocation != null && mCurrentLocation != null)
            {
                float distanceDiff = mPreviousLocation.distanceTo(mCurrentLocation); // Return meter unit
                mDistanceCovered = mDistanceCovered + distanceDiff;
            }
            mPreviousLocation = mCurrentLocation;
        }
    }

    public void startBroadcasting()
    {
        isBroadcastAllow = true;
        broadcastFirstLocation();
    }

    private void broadcastFirstLocation()
    {
        if(mCurrentLocation != null)
        {
            broadCastLocation(mCurrentLocation);
        }
    }

    public void stopBroadcasting()
    {
        isBroadcastAllow = false;
    }

    @Override
    public void onDestroy()
    {
        isUserWalking = false;
        mLocationProvider.disconnect();
    }

    private void broadCastLocation(Location location)
    {
        if(isBroadcastAllow)
        {
            broadcastUserLocation(location);
        }
    }

    private void broadcastUserLocation(Location location)
    {
        Intent intent = new Intent(IntentActions.LOCATION_UPDATED);
        intent.putExtra(ExtraKeys.LOCATION_SERVICE_RESULT_CODE, Activity.RESULT_OK);
        intent.putExtra(ExtraKeys.USER_POSITION, location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public long elapsedTime()
    {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public float distanceCovered()
    {
        return mDistanceCovered;
    }

    public boolean isUserWalking()
    {
        return isUserWalking;
    }

    public void startUserWalk()
    {
        if(!isUserWalking)
        {
            startTime = System.currentTimeMillis();
            mPreviousLocation = mCurrentLocation;
            mDistanceCovered = 0;
            isUserWalking = true;
        }
    }

    public void stopUserWalk()
    {
        if(isUserWalking)
        {
            isUserWalking = false;
        }
    }

    public Location getUserLocation()
    {
        return mCurrentLocation;
    }

    public WorkoutConfig getWorkoutConfig() {
        return workoutConfig;
    }

    public class LocalBinder extends Binder
    {
        public LocationService getService()
        {
            return LocationService.this;
        }
    }

    // Prevent system killing the background service
    public void startForeground()
    {
        startForeground(NOTIFICATION_ID, createNotification());
    }

    public void stopNotification()
    {
        stopForeground(true);
    }

    private Notification createNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.run_in_progress))
                .setSmallIcon(R.drawable.ic_stat_noti_small)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        Intent resultIntent = new Intent(this, RunActivity.class);
        resultIntent.putExtra(ExtraKeys.WORKOUT_CONFIG, workoutConfig);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        return builder.build();
    }
}
