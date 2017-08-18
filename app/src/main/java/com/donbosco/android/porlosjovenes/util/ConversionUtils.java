package com.donbosco.android.porlosjovenes.util;

public class ConversionUtils
{
    public static float meterToKm(float meters)
    {
        return meters / 1000;
    }

    public static float foundsFromDistance(float meters)
    {
        float km = meterToKm(meters);
        return km * 100;
    }
}
