package com.donbosco.android.porlosjovenes.components;

import android.support.annotation.LayoutRes;
import android.view.MenuItem;

public abstract class NavUpActivity extends ToolbarActivity
{
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        //Enable button in Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
