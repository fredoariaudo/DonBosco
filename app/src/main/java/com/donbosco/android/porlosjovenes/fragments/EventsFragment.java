package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.adapters.EventsRvAdapter;
import com.donbosco.android.porlosjovenes.model.Event;

import java.util.ArrayList;

public class EventsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Event>>
{
    private static final int EVENTS_LOADER_ID = 1;

    private ProgressBar pbEvents;
    private EventsRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView rvEvents = rootView.findViewById(R.id.rv_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        rvEvents.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new EventsRvAdapter();
        rvEvents.setAdapter(adapter);

        pbEvents = rootView.findViewById(R.id.pb_events);

        getLoaderManager().initLoader(EVENTS_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<ArrayList<Event>> onCreateLoader(int id, Bundle args)
    {
        return new EventsLoader(getContext(), pbEvents);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Event>> loader, ArrayList<Event> data)
    {
        pbEvents.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Event>> loader)
    {

    }

    private static class EventsLoader extends AsyncTaskLoader<ArrayList<Event>>
    {
        private ProgressBar progressBar;
        private ArrayList<Event> events;

        public EventsLoader(Context context, ProgressBar progressBar)
        {
            super(context);
            this.progressBar = progressBar;
        }

        @Override
        protected void onStartLoading()
        {
            if(events != null)
            {
                deliverResult(events);
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }

        @Override
        public ArrayList<Event> loadInBackground()
        {
            try
            {
                Thread.sleep(1500);
            }
            catch(Exception e)
            {

            }

            return new ArrayList<>();
        }

        @Override
        public void deliverResult(ArrayList<Event> data)
        {
            this.events = data;
            super.deliverResult(data);
        }
    }
}
