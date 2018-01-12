package com.donbosco.android.porlosjovenes.data.api;

import android.content.Context;

import com.donbosco.android.porlosjovenes.data.ObjectSerializer;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

public class WorkoutConfigurationSerializer extends ObjectSerializer
{
    //User file name
    private final static String FILE_NAME = "WorkoutConfig.dat";

    //Singleton instance
    private static WorkoutConfigurationSerializer workoutConfigSerializer;

    private WorkoutConfigurationSerializer()
    {
        //Prevent initialization from the reflection api.
        if(workoutConfigSerializer != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized WorkoutConfigurationSerializer getInstance()
    {
        if(workoutConfigSerializer == null)
            workoutConfigSerializer = new WorkoutConfigurationSerializer();

        return workoutConfigSerializer;
    }

    public void save(Context context, WorkoutConfig workoutConfig)
    {
        save(context, FILE_NAME, workoutConfig);
    }

    public WorkoutConfig load(Context context)
    {
        return (WorkoutConfig) load(context, FILE_NAME);
    }

    public void clear(Context context)
    {
        save(context, null);
    }
}
