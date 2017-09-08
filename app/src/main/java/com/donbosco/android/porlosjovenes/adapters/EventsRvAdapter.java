package com.donbosco.android.porlosjovenes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.donbosco.android.porlosjovenes.model.Event;

public class EventsRvAdapter extends ArrayRvAdapter<Event, EventsRvAdapter.EventViewHolder>
{
    protected static class EventViewHolder extends RecyclerView.ViewHolder
    {
        public EventViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position)
    {
    }
}
