package com.donbosco.android.porlosjovenes.util;

import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

import java.text.SimpleDateFormat;

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

    public static float foundsFromInitialAndDistance(float meters, WorkoutConfig workoutConfig)
    {
        return workoutConfig.getInitialAmount() + foundsFromDistance(meters, workoutConfig);
    }

    public static String net2JavaDate(String netDate)
    {
        String formattedDate = "";
        try
        {
            formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(netDate));
        }
        catch (Exception e)
        {
        }

        return formattedDate;
    }
}
