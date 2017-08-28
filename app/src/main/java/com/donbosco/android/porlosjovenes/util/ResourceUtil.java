package com.donbosco.android.porlosjovenes.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.TextView;

public class ResourceUtil
{
    public static void setCompoundDrawableLeft(Context context, TextView textView, int color, int drawable)
    {
        Drawable compoundDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawable));
        DrawableCompat.setTint(compoundDrawable, color);
        textView.setCompoundDrawablesWithIntrinsicBounds(compoundDrawable, null, null, null);
    }
}
