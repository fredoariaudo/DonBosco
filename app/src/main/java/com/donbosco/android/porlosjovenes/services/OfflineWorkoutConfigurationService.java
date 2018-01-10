package com.donbosco.android.porlosjovenes.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.donbosco.android.porlosjovenes.application.PljApplication;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.IntentActions;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.HistoryResponse;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.model.Workout;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.model.WorkoutResultResponse;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pablo on 29/12/17.
 */

public class OfflineWorkoutConfigurationService extends Service {

    private ExecutorService executorService;
    private boolean running = true;

    @Override
    public void onCreate()
    {
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        downloadOfflineWorkoutConfiguration(intent.getStringExtra(ExtraKeys.EMAIL));
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(executorService != null)
        {
            running = false;
            executorService.shutdownNow();
        }
    }

    private void downloadOfflineWorkoutConfiguration(String email)
    {
        WorkoutConfig workoutConfig = RestApi.getInstance().getWorkoutConfig(email, true);
        //Send broadcast to tell masters are updated
        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra(ExtraKeys.WORKOUT_CONFIG,workoutConfig);
        broadcastIntent.setAction(IntentActions.ACTION_OFFLINE_CONFIGUARION_WORKOUT);
        sendBroadcast(broadcastIntent);
    }

}
