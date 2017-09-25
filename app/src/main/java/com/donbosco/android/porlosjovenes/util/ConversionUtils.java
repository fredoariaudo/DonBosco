package com.donbosco.android.porlosjovenes.util;

import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

import java.text.ParseException;
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

    public static String net2JavaDate(String netDate)
    {
        String formattedDate = "";
        try
        {
            formattedDate = new SimpleDateFormat("dd/MM/YYYY").format(new SimpleDateFormat("yyyy-MM-dd").parse(netDate));
        }
        catch (ParseException e)
        {
        }

        return formattedDate;
    }
}
