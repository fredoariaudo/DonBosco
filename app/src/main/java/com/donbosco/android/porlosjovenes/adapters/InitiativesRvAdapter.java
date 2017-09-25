package com.donbosco.android.porlosjovenes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.Initiative;

public class InitiativesRvAdapter extends ArrayRvAdapter<Initiative, InitiativesRvAdapter.InitiativeViewHolder>
{
    protected static class InitiativeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivIliImage;
        TextView tvIliTitle;
        TextView tvIliDescription;

        public InitiativeViewHolder(View itemView)
        {
            super(itemView);
            ivIliImage = itemView.findViewById(R.id.iv_ili_image);
            tvIliTitle = itemView.findViewById(R.id.tv_ili_title);
            tvIliDescription = itemView.findViewById(R.id.tv_ili_description);
        }
    }

    @Override
    public InitiativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.initiative_rv_item, parent, false);
        return new InitiativeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InitiativeViewHolder holder, int position)
    {
        Initiative initiative = getItems().get(position);
        holder.tvIliTitle.setText(initiative.getTitle());
        holder.tvIliDescription.setText(initiative.getDescription());
    }
}
