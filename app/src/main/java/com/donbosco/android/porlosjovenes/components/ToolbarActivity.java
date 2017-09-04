package com.donbosco.android.porlosjovenes.components;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.donbosco.android.porlosjovenes.R;

public class ToolbarActivity extends AppCompatActivity
{
    private Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
