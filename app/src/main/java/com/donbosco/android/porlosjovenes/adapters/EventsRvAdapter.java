package com.donbosco.android.porlosjovenes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.Event;

public class EventsRvAdapter extends ArrayRvAdapter<Event, EventsRvAdapter.EventViewHolder>
{
    protected static class EventViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvEliTitle;
        TextView tvEliDate;
        TextView tvEliHour;

        public EventViewHolder(View itemView)
        {
            super(itemView);

            tvEliTitle = itemView.findViewById(R.id.tv_eli_title);
            tvEliDate = itemView.findViewById(R.id.tv_eli_date);
            tvEliHour = itemView.findViewById(R.id.tv_eli_hour);
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.event_rv_item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position)
    {
        Event event = getItems().get(position);
        holder.tvEliTitle.setText(event.getTitle());
        holder.tvEliDate.setText(event.getDate());
        holder.tvEliHour.setText(event.getHour());
    }
}