package com.donbosco.android.porlosjovenes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.model.Run;
import com.donbosco.android.porlosjovenes.util.ConvertionUtils;

public class RunResultActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_result);

        Run run = (Run) getIntent().getSerializableExtra(ExtraKeys.RUN);

        TextView tvRunResultDistance = findViewById(R.id.tv_run_result_distance);
        TextView tvRunResultCollected = findViewById(R.id.tv_run_result_collected);

        tvRunResultDistance.setText(getString(R.string.distance_format, ConvertionUtils.meterToKm(run.getDistance())));
        tvRunResultCollected.setText(getString(R.string.founds_collected_format, run.getCollected()));
    }
}
