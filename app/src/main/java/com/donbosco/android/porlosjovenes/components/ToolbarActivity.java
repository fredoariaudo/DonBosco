package com.donbosco.android.porlosjovenes.components;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.donbosco.android.porlosjovenes.R;

public abstract class ToolbarActivity extends AppCompatActivity
{
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);

        //Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
