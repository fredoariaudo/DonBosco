package com.donbosco.android.porlosjovenes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;

public class EventsRvAdapter extends ArrayRvAdapter<Event, EventsRvAdapter.EventViewHolder>
{
    private RvAdapterListener rvAdapterListener;

    protected static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RvAdapterListener rvAdapterListener;

        TextView tvEliTitle;
        TextView tvEliDate;
        TextView tvEliLocation;
        ImageView ivEliImage;

        public EventViewHolder(View itemView, RvAdapterListener rvAdapterListener)
        {
            super(itemView);

            this.rvAdapterListener = rvAdapterListener;
            itemView.setOnClickListener(this);

            tvEliTitle = itemView.findViewById(R.id.tv_eli_title);
            tvEliDate = itemView.findViewById(R.id.tv_eli_date);
            tvEliLocation = itemView.findViewById(R.id.tv_eli_location);
            ivEliImage = itemView.findViewById(R.id.iv_eli_image);
        }

        @Override
        public void onClick(View view)
        {
            rvAdapterListener.onItemClick(view, getLayoutPosition());
        }
    }

    public EventsRvAdapter(RvAdapterListener rvAdapterListener)
    {
        this.rvAdapterListener = rvAdapterListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.event_rv_item, parent, false);
        return new EventViewHolder(itemView, rvAdapterListener);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position)
    {
        Event event = getItems().get(position);
        holder.tvEliTitle.setText(event.getTitle());
        if(!event.isStandardEvent())
        {
            holder.tvEliDate.setText(ConversionUtils.net2JavaDate(event.getStartDate()));
            holder.tvEliLocation.setText(event.getLocation());
        }
        else
        {
            holder.tvEliDate.setVisibility(View.GONE);
            holder.tvEliLocation.setVisibility(View.GONE);
        }
        Glide.with(holder.ivEliImage.getContext()).load(event.getLogo()).into(holder.ivEliImage);
    }
}
