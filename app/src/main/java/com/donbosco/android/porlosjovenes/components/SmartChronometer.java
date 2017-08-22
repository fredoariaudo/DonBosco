package com.donbosco.android.porlosjovenes.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class SmartChronometer extends Chronometer
{
    private boolean running;

    public SmartChronometer(Context context)
    {
        super(context);
    }

    public SmartChronometer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SmartChronometer(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void start()
    {
        super.start();
        running = true;
    }

    public boolean isRunning()
    {
        return running;
    }
}
