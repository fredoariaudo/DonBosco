package com.donbosco.android.porlosjovenes.util;

import com.donbosco.android.porlosjovenes.model.RunConfig;

public class ConversionUtils
{
    public static float meterToKm(float meters)
    {
        return meters / 1000;
    }

    public static float foundsFromDistance(float meters, RunConfig runConfig)
    {
        float valuePerDistance = runConfig.getAmountPerDistance() / runConfig.getDistance();
        return meterToKm(meters) * valuePerDistance;
    }
}
