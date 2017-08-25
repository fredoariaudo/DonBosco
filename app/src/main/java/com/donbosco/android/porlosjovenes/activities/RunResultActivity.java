package com.donbosco.android.porlosjovenes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Run;
import com.donbosco.android.porlosjovenes.model.RunConfig;
import com.donbosco.android.porlosjovenes.model.RunResultResponse;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunResultActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_result);

        Run run = (Run) getIntent().getSerializableExtra(ExtraKeys.RUN);
        RunConfig runConfig = (RunConfig) getIntent().getSerializableExtra(ExtraKeys.RUN_CONFIG);

        TextView tvRunResultDistance = findViewById(R.id.tv_run_result_distance);
        TextView tvRunResultCollected = findViewById(R.id.tv_run_result_collected);

        tvRunResultDistance.setText(getString(R.string.distance_format, ConversionUtils.meterToKm(run.getDistance())));
        tvRunResultCollected.setText(getString(R.string.founds_collected_format, run.getCollected()));

        User user = UserSerializer.getInstance().load(this);

        HashMap<String, String> runData = new HashMap<>();
        runData.put(RestApiConstants.PARAM_EMAIL, user.getEmail());
        runData.put(RestApiConstants.PARAM_DISTANCE, String.valueOf(run.getDistance()));
        runData.put(RestApiConstants.PARAM_END_LAT, String.valueOf(0));
        runData.put(RestApiConstants.PARAM_END_LNG, String.valueOf(0));
        runData.put(RestApiConstants.PARAM_SPONSOR_ID, String.valueOf(runConfig.getSponsorId()));
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
}
