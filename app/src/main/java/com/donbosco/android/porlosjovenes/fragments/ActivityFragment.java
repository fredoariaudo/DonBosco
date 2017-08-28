package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.activities.RunActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.RunConfig;

public class ActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<RunConfig>
{
    private static final int RUN_CONFIG_LOADER_ID = 1;

    private FloatingActionButton fabActivityBegin;

    private RunConfig runConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);

        fabActivityBegin = rootView.findViewById(R.id.fab_activity_begin);
        fabActivityBegin.setEnabled(false);
        fabActivityBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startRun();
            }
        });

        getLoaderManager().initLoader(RUN_CONFIG_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<RunConfig> onCreateLoader(int id, Bundle args)
    {
        return new RunConfigLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<RunConfig> loader, RunConfig data)
    {
        if(data != null)
        {
            fabActivityBegin.setEnabled(true);
            runConfig = data;
        }
    }

    @Override
    public void onLoaderReset(Loader<RunConfig> loader)
    {
    }

    private void startRun()
    {
        Intent intent = new Intent(getContext(), RunActivity.class);
        intent.putExtra(ExtraKeys.RUN_CONFIG, runConfig);
        startActivity(intent);
    }

    private static class RunConfigLoader extends AsyncTaskLoader<RunConfig>
    {
        public RunConfigLoader(Context context)
        {
            super(context);
        }

        @Override
        protected void onStartLoading()
        {
            forceLoad();
        }

        @Override
        public RunConfig loadInBackground()
        {
            return RestApi.getInstance().getRunConfig();
        }

        @Override
        public void deliverResult(RunConfig data)
        {
            super.deliverResult(data);
        }
    }
}
