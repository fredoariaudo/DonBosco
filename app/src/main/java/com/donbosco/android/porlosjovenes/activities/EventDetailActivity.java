package com.donbosco.android.porlosjovenes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        Event event = (Event) getIntent().getSerializableExtra(ExtraKeys.EVENT);
    }
}
