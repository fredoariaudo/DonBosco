package com.donbosco.android.porlosjovenes.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.components.NavUpActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.model.Event;

public class EventDetailActivity extends NavUpActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Event event = (Event) getIntent().getSerializableExtra(ExtraKeys.EVENT);

        ImageView ivEventDetailImage = findViewById(R.id.iv_event_detail_image);
        Glide.with(this).load("http://clubdecorredores.com/carreras/2016/c508/e705/flyer.jpg").into(ivEventDetailImage);

        TextView tvEventDetailDate = findViewById(R.id.tv_event_detail_date);
        TextView tvEventDetailLocation = findViewById(R.id.tv_event_detail_location);
        TextView tvEventDetailDescription = findViewById(R.id.tv_event_detail_description);

        tvEventDetailDate.setText(event.getStartDate());
        tvEventDetailLocation.setText(event.getLocation());
        tvEventDetailDescription.setText(event.getDescription());
    }
}
