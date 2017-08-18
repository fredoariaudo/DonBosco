package com.donbosco.android.porlosjovenes.adapters;

import android.view.View;

public interface RvAdapterListener
{
    void onItemClick(View v, int itemPosition);
    boolean onItemLongClick(View v, int itemPosition);
}
