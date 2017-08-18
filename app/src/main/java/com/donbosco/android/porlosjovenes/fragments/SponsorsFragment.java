package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.adapters.RvAdapterListener;
import com.donbosco.android.porlosjovenes.adapters.SponsorsRvAdapter;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Sponsor;

import java.util.ArrayList;

public class SponsorsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Sponsor>>, RvAdapterListener
{
    private static final int SPONSORS_LOADER_ID = 1;

    private ProgressBar pbSponsors;
    private SponsorsRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_sponsors, container, false);

        pbSponsors = rootView.findViewById(R.id.pb_sponsors);

        RecyclerView rvSponsors = rootView.findViewById(R.id.rv_sponsors);
        rvSponsors.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSponsors.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new SponsorsRvAdapter(this);
        rvSponsors.setAdapter(adapter);

        getLoaderManager().initLoader(SPONSORS_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<ArrayList<Sponsor>> onCreateLoader(int id, Bundle args)
    {
        return new SponsorsLoader(getContext(), pbSponsors);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Sponsor>> loader, ArrayList<Sponsor> data)
    {
        if(data != null)
            adapter.setItems(data);

        pbSponsors.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Sponsor>> loader)
    {
        if(adapter != null)
            adapter.clear();
    }

    @Override
    public void onItemClick(View v, int itemPosition)
    {
        Sponsor sponsor = adapter.getItems().get(itemPosition);

        if(!TextUtils.isEmpty(sponsor.getUrl()))
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sponsor.getUrl()));
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(View v, int itemPosition)
    {
        return false;
    }

    private static class SponsorsLoader extends AsyncTaskLoader<ArrayList<Sponsor>>
    {
        private ArrayList<Sponsor> sponsors;
        private ProgressBar progressBar;

        public SponsorsLoader(Context context, ProgressBar progressBar)
        {
            super(context);
            this.progressBar = progressBar;
        }

        @Override
        protected void onStartLoading()
        {
            if(sponsors != null)
            {
                deliverResult(sponsors);
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }

        @Override
        public ArrayList<Sponsor> loadInBackground()
        {
            return RestApi.getInstance().getSponsors();
        }

        @Override
        public void deliverResult(ArrayList<Sponsor> data)
        {
            sponsors = data;
            super.deliverResult(data);
        }
    }
}
