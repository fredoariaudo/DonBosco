package com.donbosco.android.porlosjovenes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.ProfileOption;

public class ProfileOptionRvAdapter extends ArrayRvAdapter<ProfileOption, ProfileOptionRvAdapter.ProfileOptionViewHolder>
{
    private RvAdapterListener rvAdapterListener;

    protected static class ProfileOptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RvAdapterListener rvAdapterListener;

        TextView tv_profile_option_title;

        public ProfileOptionViewHolder(View itemView, RvAdapterListener rvAdapterListener)
        {
            super(itemView);

            this.rvAdapterListener = rvAdapterListener;
            itemView.setOnClickListener(this);

            tv_profile_option_title = itemView.findViewById(R.id.tv_profile_option_title);
        }

        @Override
        public void onClick(View view)
        {
            rvAdapterListener.onItemClick(view, getLayoutPosition());
        }
    }

    public ProfileOptionRvAdapter(RvAdapterListener rvAdapterListener)
    {
        this.rvAdapterListener = rvAdapterListener;
    }

    @Override
    public ProfileOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.profile_option_rv_item, parent, false);
        return new ProfileOptionViewHolder(itemView, rvAdapterListener);
    }

    @Override
    public void onBindViewHolder(ProfileOptionViewHolder holder, int position)
    {
        ProfileOption profileOption = getItems().get(position);
        holder.tv_profile_option_title.setText(profileOption.getTitle());
    }
}
