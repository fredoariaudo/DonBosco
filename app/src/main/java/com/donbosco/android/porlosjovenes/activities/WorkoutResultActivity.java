package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.components.CloseActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Run;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.model.RunResultResponse;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;
import com.donbosco.android.porlosjovenes.util.ResourceUtil;
import com.donbosco.android.porlosjovenes.util.WorkoutUtils;

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

        Run run = (Run) getIntent().getSerializableExtra(ExtraKeys.RUN);
        WorkoutConfig workoutConfig = (WorkoutConfig) getIntent().getSerializableExtra(ExtraKeys.WORKOUT_CONFIG);

        ImageView ivWorkoutResultLogo = findViewById(R.id.iv_workout_result_logo);
        Glide.with(this).load(workoutConfig.getSponsorLogo()).into(ivWorkoutResultLogo);

        TextView tvWorkoutResultTogetherCollected = findViewById(R.id.tv_workout_result_together_collected);
        ResourceUtil.setCompoundDrawableLeft(this, tvWorkoutResultTogetherCollected, ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_favorite_black_24dp);

        TextView tvWorkoutResultCollected = findViewById(R.id.tv_workout_result_collected);
        tvWorkoutResultCollected.setText(getString(R.string.founds_collected_format, run.getCollected()));

        TextView tvWorkoutResultDistanceTraveled = findViewById(R.id.tv_workout_result_distance_traveled);
        tvWorkoutResultDistanceTraveled.setText(getString(R.string.distance_format, ConversionUtils.meterToKm(run.getDistance())));
        ResourceUtil.setCompoundDrawableLeftDp(this, tvWorkoutResultDistanceTraveled, ContextCompat.getColor(this, R.color.colorPrimary), WorkoutUtils.getWorkoutIcon(workoutConfig.getWorkoutType()), 36);

        Button btnWorkoutResultDonateMore = findViewById(R.id.btn_workout_result_donate_more);
        btnWorkoutResultDonateMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                donateMore();
            }
        });

        User user = UserSerializer.getInstance().load(this);

        HashMap<String, String> runData = new HashMap<>();
        runData.put(RestApiConstants.PARAM_EMAIL, user.getEmail());
        runData.put(RestApiConstants.PARAM_DISTANCE, String.valueOf(ConversionUtils.meterToKm(run.getDistance())));
        runData.put(RestApiConstants.PARAM_END_LAT, String.valueOf(0));
        runData.put(RestApiConstants.PARAM_END_LNG, String.valueOf(0));
        runData.put(RestApiConstants.PARAM_SPONSOR_ID, String.valueOf(workoutConfig.getSponsorId()));
        runData.put(RestApiConstants.PARAM_DEVICE_ID, String.valueOf(0));

        RestApi.getInstance().sendRunResult(runData, new Callback<RunResultResponse>() {
            @Override
            public void onResponse(Call<RunResultResponse> call, Response<RunResultResponse> response)
            {
            }

            @Override
            public void onFailure(Call<RunResultResponse> call, Throwable t)
            {
            }
        });
    }

    private void donateMore()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RestApiConstants.DONATE_MORE_URL));
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}
