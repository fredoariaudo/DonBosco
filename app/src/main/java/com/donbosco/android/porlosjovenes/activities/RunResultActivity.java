package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.donbosco.android.porlosjovenes.util.ResourceUtil;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Run run = (Run) getIntent().getSerializableExtra(ExtraKeys.RUN);
        RunConfig runConfig = (RunConfig) getIntent().getSerializableExtra(ExtraKeys.RUN_CONFIG);

        ImageView ivRunResultLogo = findViewById(R.id.iv_run_result_logo);
        Glide.with(this).load(runConfig.getSponsorLogo()).into(ivRunResultLogo);

        TextView tvRunResultTogetherCollected = findViewById(R.id.tv_run_result_together_collected);
        ResourceUtil.setCompoundDrawableLeft(this, tvRunResultTogetherCollected, ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_favorite_black_24dp);

        TextView tvRunResultCollected = findViewById(R.id.tv_run_result_collected);
        tvRunResultCollected.setText(getString(R.string.founds_collected_format, run.getCollected()));

        Button btnRunResultDonateMore = findViewById(R.id.btn_run_result_donate_more);
        btnRunResultDonateMore.setOnClickListener(new View.OnClickListener() {
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

    private void donateMore()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RestApiConstants.DONATE_MORE_URL));
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}
