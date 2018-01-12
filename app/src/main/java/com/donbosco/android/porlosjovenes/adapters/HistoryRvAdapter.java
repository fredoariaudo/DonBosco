package com.donbosco.android.porlosjovenes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.model.Workout;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;
import com.donbosco.android.porlosjovenes.util.WorkoutUtils;

import java.util.ArrayList;
import java.util.List;

public class HistoryRvAdapter<T> extends SectionedRecyclerViewAdapter<SectionedViewHolder>
{
    private RvAdapterListener rvAdapterListener;
    private ArrayList<T> items;
    private List<Workout> pending;
    private List<Workout> sent;
    private Context context;

    public void setSent(ArrayList<Workout> sent) {
        this.sent = sent;
        notifyDataSetChanged();
    }

    public void setPending(List<Workout> pending) {
        this.pending = pending;
        notifyDataSetChanged();
    }

    protected static class HitoryViewHolder extends SectionedViewHolder implements View.OnClickListener
    {
        private RvAdapterListener rvAdapterListener;

        TextView tvHliDistance;
        TextView tvHliDate;
        TextView tvHliDonation;
        ImageView ivHliImage;

        public HitoryViewHolder(View itemView, RvAdapterListener rvAdapterListener)
        {
            super(itemView);

            this.rvAdapterListener = rvAdapterListener;
            itemView.setOnClickListener(this);

            tvHliDate = itemView.findViewById(R.id.tv_hli_date);
            tvHliDonation = itemView.findViewById(R.id.tv_hli_dontation);
            tvHliDistance = itemView.findViewById(R.id.tv_hli_distance);
            ivHliImage = itemView.findViewById(R.id.iv_hli_image);
        }

        @Override
        public void onClick(View view)
        {
            rvAdapterListener.onItemClick(view, getLayoutPosition());
        }
    }

    public static class HeaderViewHolder  extends SectionedViewHolder implements View.OnClickListener {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }


    public HistoryRvAdapter(Context context,RvAdapterListener rvAdapterListener)
    {
        this.context = context;
        this.rvAdapterListener = rvAdapterListener;
    }

    @Override
    public SectionedViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch(viewType) {
            case VIEW_TYPE_HEADER:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_activated_1, parent, false);
                return new HeaderViewHolder(v);
            default:
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View itemView = inflater.inflate(R.layout.history_rv_item, parent, false);
                return new HitoryViewHolder(itemView, rvAdapterListener);
        }
    }

    @Override
    public int getSectionCount() {
        return 2;
    }

    @Override
    public int getItemCount(int section) {
        if (section == 0)
        {
            return pending == null ? 0 : pending.size();
        }
        else
        {
            return sent == null ? 0 : sent.size();
        }
    }

    @Override
    public void onBindHeaderViewHolder(SectionedViewHolder holder, int section, boolean expanded) {
        HeaderViewHolder header = (HeaderViewHolder) holder;
        TextView tv = header.itemView.findViewById(android.R.id.text1);

        if (section == 0)
        {
            tv.setText(R.string.pending_workouts);
        }
        else
        {
            tv.setText(R.string.sent_workouts);
        }
    }

    @Override
    public void onBindFooterViewHolder(SectionedViewHolder holder, int section) {

    }

    @Override
    public void onBindViewHolder(SectionedViewHolder holder, int section, int relativePosition, int absolutePosition) {

        HitoryViewHolder history = (HitoryViewHolder) holder;
        Workout workout = null;
        if (section == 0)
        {
            workout = pending.get(relativePosition);
            history.tvHliDate.setText(workout.getDate() + "");
        }
        else
        {
            workout = sent.get(relativePosition);
            history.tvHliDate.setText(workout.getServerDate());

        }


        history.tvHliDistance.setText(context.getString(R.string.distance_format, ConversionUtils.meterToKm(workout.getDistance())));
        history.tvHliDonation.setText(context.getString(R.string.founds_collected_format,workout.getCollected()));
        history.ivHliImage.setImageResource(WorkoutUtils.getWorkoutIcon(workout.getType()));
    }


}
