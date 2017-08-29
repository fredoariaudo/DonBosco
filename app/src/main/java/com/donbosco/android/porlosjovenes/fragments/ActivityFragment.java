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
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.activities.RunActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.RunConfig;

public class ActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<RunConfig>
{
    private static final int RUN_CONFIG_LOADER_ID = 1;

    private ProgressBar pbActivity;
    private FloatingActionButton fabActivityBegin;

    private RunConfig runConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);

        pbActivity = rootView.findViewById(R.id.pb_activity);

        fabActivityBegin = rootView.findViewById(R.id.fab_activity_begin);
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
        return new RunConfigLoader(getContext(), pbActivity);
    }

    @Override
    public void onLoadFinished(Loader<RunConfig> loader, RunConfig data)
    {
        pbActivity.setVisibility(View.GONE);

        if(data != null)
        {
            fabActivityBegin.show();
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
        private ProgressBar progressBar;

        public RunConfigLoader(Context context, ProgressBar progressBar)
        {
            super(context);
            this.progressBar = progressBar;
        }

        @Override
        protected void onStartLoading()
        {
            progressBar.setVisibility(View.VISIBLE);
            forceLoad();
        }

        @Override
        public RunConfig loadInBackground()
        {
            return RestApi.getInstance().getRunConfig();
        }
    }
}
