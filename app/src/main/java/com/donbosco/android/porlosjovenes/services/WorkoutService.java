package com.donbosco.android.porlosjovenes.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Workout;
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

public class WorkoutService extends Service {

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
        sendWorkouts();
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

    private void sendWorkouts()
    {

        final List<Workout> workouts = Workout.find(Workout.class, "");
        executorService.execute(new Runnable() {
            @Override
            public void run()
            {

                for (final Workout workout: workouts) {

                    workout.setSyncing(1);
                    HashMap<String, String> workoutData = new HashMap<>();
                    workoutData.put(RestApiConstants.PARAM_EMAIL, workout.getEmail());
                    workoutData.put(RestApiConstants.PARAM_DISTANCE,String.valueOf(workout.getDistance()));
                    workoutData.put(RestApiConstants.PARAM_END_LAT, String.valueOf(workout.getEndLatitude()));
                    workoutData.put(RestApiConstants.PARAM_END_LNG, String.valueOf(workout.getEndLongitude()));
                    workoutData.put(RestApiConstants.PARAM_SPONSOR_ID, String.valueOf(workout.getSponsor()));
                    workoutData.put(RestApiConstants.PARAM_DEVICE_ID, FirebaseInstanceId.getInstance().getToken());
                    workoutData.put(RestApiConstants.PARAM_EVENT_ID, String.valueOf(workout.getEvent()));
                    workoutData.put(RestApiConstants.PARAM_WORKOUT_TYPE, String.valueOf(workout.getType()));

                    RestApi.getInstance().sendWorkoutResult(workoutData, new Callback<WorkoutResultResponse>() {
                        @Override
                        public void onResponse(Call<WorkoutResultResponse> call, Response<WorkoutResultResponse> response)
                        {
                            workout.delete();
                        }

                        @Override
                        public void onFailure(Call<WorkoutResultResponse> call, Throwable t)
                        {
                            workout.setSyncing(0);
                        }
                    });
                }
            }
        });
    }

}
