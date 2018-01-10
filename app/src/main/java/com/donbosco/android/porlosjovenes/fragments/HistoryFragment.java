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
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.adapters.EventsRvAdapter;
import com.donbosco.android.porlosjovenes.adapters.HistoryRvAdapter;
import com.donbosco.android.porlosjovenes.adapters.RvAdapterListener;
import com.donbosco.android.porlosjovenes.application.AppInfo;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.EventsResponse;
import com.donbosco.android.porlosjovenes.model.HistoryResponse;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.model.Workout;

import java.util.List;

public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<HistoryResponse>, RvAdapterListener
{
    private static final int HISTORY_LOADER_ID = 2;

    private View rootView;
    private ProgressBar pbEvents;
    private HistoryRvAdapter adapter;
    private Snackbar snackbarError;
    private Snackbar snackbarNoConnection;
    private Snackbar snackbarServerError;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);



        RecyclerView rvEvents = rootView.findViewById(R.id.rv_history);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        pbEvents = rootView.findViewById(R.id.pb_history);

        adapter = new HistoryRvAdapter<Workout>(getContext(),this);
        rvEvents.setAdapter(adapter);


        List<Workout> workouts = Workout.find(Workout.class, "");
        adapter.setPending(workouts);
        getLoaderManager().initLoader(HISTORY_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public void onDestroy() {
        if (snackbarError != null)
            snackbarError.dismiss();
        if (snackbarNoConnection != null)
            snackbarNoConnection.dismiss();
        if (snackbarServerError != null)
            snackbarServerError.dismiss();
        super.onDestroy();
    }

    @Override
    public Loader<HistoryResponse> onCreateLoader(int id, Bundle args)
    {
        return new HistoryLoader(getContext(), pbEvents);
    }

    @Override
    public void onLoadFinished(Loader<HistoryResponse> loader, HistoryResponse data)
    {
        if(data != null && data.size() > 0)
            adapter.setSent(data);

        pbEvents.setVisibility(View.GONE);

        if(!AppInfo.connected)
            Snackbar.make(rootView, R.string.api_generic_error, Snackbar.LENGTH_LONG).show();



        if(data != null)
        {
            adapter.setSent(data);
        }
        else
        {
            if (AppInfo.connected)
            {
                snackbarError = Snackbar.make(rootView, R.string.error_initial_config, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getLoaderManager().restartLoader(HISTORY_LOADER_ID, null, HistoryFragment.this);
                            }
                        });
                snackbarError.show();
            }
            else
            {
                snackbarNoConnection = Snackbar.make(rootView, R.string.error_connection, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getLoaderManager().restartLoader(HISTORY_LOADER_ID, null, HistoryFragment.this);
                            }
                        });
                snackbarNoConnection.show();
            }


        }

    }

    @Override
    public void onLoaderReset(Loader<HistoryResponse> loader)
    {

    }

    @Override
    public void onItemClick(View v, int itemPosition)
    {
    }

    @Override
    public boolean onItemLongClick(View v, int itemPosition)
    {
        return false;
    }

    private static class HistoryLoader extends AsyncTaskLoader<HistoryResponse>
    {
        private ProgressBar progressBar;
        private HistoryResponse historyResponse;

        public HistoryLoader(Context context, ProgressBar progressBar)
        {
            super(context);
            this.progressBar = progressBar;
        }

        @Override
        protected void onStartLoading()
        {
            if(historyResponse != null)
            {
                deliverResult(historyResponse);
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }

        @Override
        public HistoryResponse loadInBackground()
        {
            User user = UserSerializer.getInstance().load(getContext());
            HistoryResponse historyResponse = RestApi.getInstance().getHistory(user.getEmail());
            return historyResponse;
        }

        @Override
        public void deliverResult(HistoryResponse data)
        {
            this.historyResponse = data;
            super.deliverResult(data);
        }
    }
}
