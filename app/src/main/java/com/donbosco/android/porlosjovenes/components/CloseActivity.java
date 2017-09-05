package com.donbosco.android.porlosjovenes.components;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.util.ResourceUtil;

public abstract class CloseActivity extends ToolbarActivity
{
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        //Enable button in Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set close button
        getSupportActionBar().setHomeAsUpIndicator(ResourceUtil.tintDrawable(this, R.drawable.ic_close_black_24dp, ContextCompat.getColor(this, android.R.color.white)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
