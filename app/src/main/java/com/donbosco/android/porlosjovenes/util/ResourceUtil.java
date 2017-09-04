package com.donbosco.android.porlosjovenes.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.widget.TextView;

public class ResourceUtil
{
    public static Drawable tintDrawable(Context context, int drawable, int color)
    {
        Drawable compoundDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawable));
        DrawableCompat.setTint(compoundDrawable, color);
        return compoundDrawable;
    }

    public static void setCompoundDrawableLeft(Context context, TextView textView, int color, int drawable)
    {
        Drawable compoundDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawable));
        DrawableCompat.setTint(compoundDrawable, color);
        textView.setCompoundDrawablesWithIntrinsicBounds(compoundDrawable, null, null, null);
    }

    public static void setCompoundDrawableLeftDp(Context context, TextView textView, int color, int drawable, int dp)
    {
        Drawable compoundDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawable));
        DrawableCompat.setTint(compoundDrawable, color);
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        compoundDrawable.setBounds(0, 0, (int) size, (int) size);
        textView.setCompoundDrawables(compoundDrawable, null, null, null);
    }
}
