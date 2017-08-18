package com.donbosco.android.porlosjovenes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.Sponsor;

public class SponsorsRvAdapter extends ArrayRvAdapter<Sponsor, SponsorsRvAdapter.SponsorViewHolder>
{
    private RvAdapterListener rvAdapterListener;

    protected static class SponsorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RvAdapterListener rvAdapterListener;

        ImageView iv_sli_logo;
        TextView tv_sli_title;

        public SponsorViewHolder(View itemView, RvAdapterListener rvAdapterListener)
        {
            super(itemView);

            this.rvAdapterListener = rvAdapterListener;
            itemView.setOnClickListener(this);

            iv_sli_logo = itemView.findViewById(R.id.iv_sli_logo);
            tv_sli_title = itemView.findViewById(R.id.tv_sli_title);
        }

        @Override
        public void onClick(View view)
        {
            rvAdapterListener.onItemClick(view, getLayoutPosition());
        }
    }

    public SponsorsRvAdapter(RvAdapterListener rvAdapterListener)
    {
        this.rvAdapterListener = rvAdapterListener;
    }

    @Override
    public SponsorViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.sponsor_rv_item, parent, false);
        return new SponsorViewHolder(itemView, rvAdapterListener);
    }

    @Override
    public void onBindViewHolder(SponsorViewHolder holder, int position)
    {
        Sponsor sponsor = getItems().get(position);
        holder.tv_sli_title.setText(sponsor.getName());
        Glide.with(holder.iv_sli_logo.getContext()).load(sponsor.getLogo()).into(holder.iv_sli_logo);
    }
}
