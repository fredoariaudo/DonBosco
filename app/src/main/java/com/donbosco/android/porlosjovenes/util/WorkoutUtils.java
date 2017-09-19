package com.donbosco.android.porlosjovenes.util;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

public class WorkoutUtils
{
    public static int getWorkoutIcon(int type)
    {
        switch (type)
        {
            case WorkoutConfig.WORKOUT_TYPE_WALK:
                return R.drawable.ic_directions_walk_black_96dp;

            case WorkoutConfig.WORKOUT_TYPE_RUN:
                return R.drawable.ic_directions_run_black_96dp;

            case WorkoutConfig.WORKOUT_TYPE_BIKE:
                return R.drawable.ic_directions_bike_black_96dp;

            default:
                return R.drawable.ic_directions_walk_black_96dp;
        }
    }
}
