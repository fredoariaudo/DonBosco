package com.donbosco.android.porlosjovenes.util;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.RunConfig;

public class WorkoutUtils
{
    public static int getWorkoutIcon(int type)
    {
        switch (type)
        {
            case RunConfig.WORKOUT_TYPE_WALK:
                return R.drawable.ic_directions_walk_black_96dp;

            case RunConfig.WORKOUT_TYPE_RUN:
                return R.drawable.ic_directions_run_black_96dp;

            case RunConfig.WORKOUT_TYPE_BIKE:
                return R.drawable.ic_directions_bike_black_96dp;

            default:
                return R.drawable.ic_directions_walk_black_96dp;
        }
    }
}
