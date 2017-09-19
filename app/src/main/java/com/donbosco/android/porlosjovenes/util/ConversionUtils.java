package com.donbosco.android.porlosjovenes.util;

import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

public class ConversionUtils
{
    public static float meterToKm(float meters)
    {
        return meters / 1000;
    }

    public static float foundsFromDistance(float meters, WorkoutConfig workoutConfig)
    {
        float valuePerDistance = workoutConfig.getAmountPerDistance() / workoutConfig.getDistance();
        return meterToKm(meters) * valuePerDistance;
    }
}
