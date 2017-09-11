package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.donbosco.android.porlosjovenes.activities.EventDetailActivity;
import com.donbosco.android.porlosjovenes.adapters.EventsRvAdapter;
import com.donbosco.android.porlosjovenes.adapters.RvAdapterListener;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.model.Event;

import java.util.ArrayList;

public class EventsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Event>>, RvAdapterListener
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

        adapter = new EventsRvAdapter(this);
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
        if(data != null)
            adapter.setItems(data);

        pbEvents.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Event>> loader)
    {
        adapter.clear();
    }

    @Override
    public void onItemClick(View v, int itemPosition)
    {
        Event event = adapter.getItems().get(itemPosition);
        Intent intent = new Intent(getContext(), EventDetailActivity.class);
        intent.putExtra(ExtraKeys.EVENT, event);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View v, int itemPosition)
    {
        return false;
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
                Thread.sleep(1000);
            }
            catch(Exception e)
            {

            }

            ArrayList<Event> events = new ArrayList<>();

            for(int i=0; i<10; i++)
            {
                Event event = new Event();
                event.setTitle("Buenos Aires corre por los jóvenes");
                event.setDate("8 de Septiembre");
                event.setHour("10:30 hs");
                event.setLocation("Buenos Aires");
                event.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
                event.setImage("http://clubdecorredores.com/carreras/2016/c508/logo.jpg");
                events.add(event);
            }

            return events;
        }

        @Override
        public void deliverResult(ArrayList<Event> data)
        {
            this.events = data;
            super.deliverResult(data);
        }
    }
}
