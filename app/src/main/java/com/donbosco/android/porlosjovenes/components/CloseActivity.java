package com.donbosco.android.porlosjovenes.components;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.util.ResourceUtil;

public abstract class CloseActivity extends NavUpActivity
{
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        //Set close button
        getSupportActionBar().setHomeAsUpIndicator(ResourceUtil.tintDrawable(this, R.drawable.ic_close_black_24dp, ContextCompat.getColor(this, android.R.color.white)));
    }
}
