package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.BuildConfig;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.components.CloseActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Workout;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.model.WorkoutResultResponse;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;
import com.donbosco.android.porlosjovenes.util.ResourceUtil;
import com.donbosco.android.porlosjovenes.util.WorkoutUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutResultActivity extends CloseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_result);

        Workout workout = (Workout) getIntent().getSerializableExtra(ExtraKeys.WORKOUT);
        WorkoutConfig workoutConfig = (WorkoutConfig) getIntent().getSerializableExtra(ExtraKeys.WORKOUT_CONFIG);

        ImageView ivWorkoutResultLogo = findViewById(R.id.iv_workout_result_logo);
        Glide.with(this).load(workoutConfig.getSponsorLogo()).into(ivWorkoutResultLogo);

        TextView tvWorkoutResultTogetherCollected = findViewById(R.id.tv_workout_result_together_collected);
        ResourceUtil.setCompoundDrawableLeft(this, tvWorkoutResultTogetherCollected, ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_favorite_black_24dp);

        TextView tvWorkoutResultCollected = findViewById(R.id.tv_workout_result_collected);
        tvWorkoutResultCollected.setText(getString(R.string.founds_collected_format, workout.getCollected()));

        TextView tvWorkoutResultDistanceTraveled = findViewById(R.id.tv_workout_result_distance_traveled);
        tvWorkoutResultDistanceTraveled.setText(getString(R.string.distance_format, ConversionUtils.meterToKm(workout.getDistance())));
        ResourceUtil.setCompoundDrawableLeftDp(this, tvWorkoutResultDistanceTraveled, ContextCompat.getColor(this, R.color.colorPrimary), WorkoutUtils.getWorkoutIcon(workoutConfig.getWorkoutType()), 36);

        User user = UserSerializer.getInstance().load(this);
        String token = FirebaseInstanceId.getInstance().getToken();

        HashMap<String, String> workoutData = new HashMap<>();
        workoutData.put(RestApiConstants.PARAM_EMAIL, user.getEmail());
        workoutData.put(RestApiConstants.PARAM_DISTANCE, String.valueOf(ConversionUtils.meterToKm(workout.getDistance())));
        workoutData.put(RestApiConstants.PARAM_END_LAT, String.valueOf(0));
        workoutData.put(RestApiConstants.PARAM_END_LNG, String.valueOf(0));
        workoutData.put(RestApiConstants.PARAM_SPONSOR_ID, String.valueOf(workoutConfig.getSponsorId()));
        workoutData.put(RestApiConstants.PARAM_DEVICE_ID, token);
        workoutData.put(RestApiConstants.PARAM_EVENT_ID, String.valueOf(workoutConfig.getEventId()));
        workoutData.put(RestApiConstants.PARAM_WORKOUT_TYPE, String.valueOf(workoutConfig.getWorkoutType()));
        workoutData.put(RestApiConstants.PARAM_PLATFORM, RestApiConstants.ANDROID);
        workoutData.put(RestApiConstants.PARAM_OS_VERSION, Build.VERSION.RELEASE);
        workoutData.put(RestApiConstants.PARAM_OS_APP, BuildConfig.VERSION_NAME);

        RestApi.getInstance().sendWorkoutResult(workoutData, new Callback<WorkoutResultResponse>() {
            @Override
            public void onResponse(Call<WorkoutResultResponse> call, Response<WorkoutResultResponse> response)
            {
            }

            @Override
            public void onFailure(Call<WorkoutResultResponse> call, Throwable t)
            {
            }
        });
    }

}
