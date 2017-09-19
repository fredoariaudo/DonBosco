package com.donbosco.android.porlosjovenes.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.util.WorkoutUtils;

public class WorkoutTypePagerAdapter extends PagerAdapter
{
    private int[] items = new int[]{WorkoutConfig.WORKOUT_TYPE_WALK, WorkoutConfig.WORKOUT_TYPE_RUN, WorkoutConfig.WORKOUT_TYPE_BIKE};
    private Context context;

    public WorkoutTypePagerAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return items.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        int workoutType = items[position];

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.workout_type_pager_item, container, false);
        ImageView ivWtiIcon = view.findViewById(R.id.iv_wti_icon);
        ivWtiIcon.setImageResource(WorkoutUtils.getWorkoutIcon(workoutType));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view)
    {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    public int getWorkoutTypeAt(int position)
    {
        return position < items.length ? items[position] : -1;
    }
}
