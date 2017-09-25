package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.adapters.InitiativesRvAdapter;
import com.donbosco.android.porlosjovenes.application.AppInfo;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Initiative;

import java.util.ArrayList;

public class InitiativesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Initiative>>
{
    private static final int INITIATIVES_LOADER_ID = 1;

    private View rootView;
    private ProgressBar pbInitiatives;
    private InitiativesRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_initiatives, container, false);

        ImageView ivDbImageHeaderBackground = rootView.findViewById(R.id.iv_db_image_header_background);
        TextView tvDbImageHeaderTitle = rootView.findViewById(R.id.tv_db_image_header_title);
        Glide.with(getContext()).load("https://porlosjovenes.com/site/wp-content/uploads/2017/08/lunapark_extra2.jpg").into(ivDbImageHeaderBackground);
        tvDbImageHeaderTitle.setText(R.string.header_title_initiatives);

        RecyclerView rvInitiatives = rootView.findViewById(R.id.rv_initiatives);
        rvInitiatives.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new InitiativesRvAdapter();
        rvInitiatives.setAdapter(adapter);

        pbInitiatives = rootView.findViewById(R.id.pb_initiatives);

        getLoaderManager().initLoader(INITIATIVES_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<ArrayList<Initiative>> onCreateLoader(int id, Bundle args)
    {
        return new InitiativesLoader(getContext(), pbInitiatives);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Initiative>> loader, ArrayList<Initiative> data)
    {
        if(data != null)
            adapter.setItems(data);

        pbInitiatives.setVisibility(View.GONE);

        if(!AppInfo.connected)
            Snackbar.make(rootView, R.string.api_generic_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Initiative>> loader)
    {
    }

    private static class InitiativesLoader extends AsyncTaskLoader<ArrayList<Initiative>>
    {
        private ProgressBar progressBar;
        private ArrayList<Initiative> initiatives;

        public InitiativesLoader(Context context, ProgressBar progressBar)
        {
            super(context);
            this.progressBar = progressBar;
        }

        @Override
        protected void onStartLoading()
        {
            if(initiatives != null)
            {
                deliverResult(initiatives);
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }

        @Override
        public ArrayList<Initiative> loadInBackground()
        {
            return RestApi.getInstance().getInititatives();
        }

        @Override
        public void deliverResult(ArrayList<Initiative> data)
        {
            this.initiatives = data;
            super.deliverResult(data);
        }
    }
}
