package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.donbosco.android.porlosjovenes.activities.EventDetailActivity;
import com.donbosco.android.porlosjovenes.adapters.EventsRvAdapter;
import com.donbosco.android.porlosjovenes.adapters.RvAdapterListener;
import com.donbosco.android.porlosjovenes.application.AppInfo;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.model.EventsResponse;
import com.donbosco.android.porlosjovenes.model.User;

public class EventsFragment extends Fragment implements LoaderManager.LoaderCallbacks<EventsResponse>, RvAdapterListener
{
    private static final int EVENTS_LOADER_ID = 1;

    private View rootView;
    private ProgressBar pbEvents;
    private EventsRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView rvEvents = rootView.findViewById(R.id.rv_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new EventsRvAdapter(this);
        rvEvents.setAdapter(adapter);

        pbEvents = rootView.findViewById(R.id.pb_events);

        getLoaderManager().initLoader(EVENTS_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<EventsResponse> onCreateLoader(int id, Bundle args)
    {
        return new EventsLoader(getContext(), pbEvents);
    }

    @Override
    public void onLoadFinished(Loader<EventsResponse> loader, EventsResponse data)
    {
        if(data != null && data.getEvents() != null)
            adapter.setItems(data.getEvents());

        pbEvents.setVisibility(View.GONE);

        if(!AppInfo.connected)
            Snackbar.make(rootView, R.string.api_generic_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<EventsResponse> loader)
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

    private static class EventsLoader extends AsyncTaskLoader<EventsResponse>
    {
        private ProgressBar progressBar;
        private EventsResponse eventsResponse;

        public EventsLoader(Context context, ProgressBar progressBar)
        {
            super(context);
            this.progressBar = progressBar;
        }

        @Override
        protected void onStartLoading()
        {
            if(eventsResponse != null)
            {
                deliverResult(eventsResponse);
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }

        @Override
        public EventsResponse loadInBackground()
        {
            User user = UserSerializer.getInstance().load(getContext());
            EventsResponse eventsResponse = RestApi.getInstance().getEvents(user.getEmail());

            if(eventsResponse != null)
            {
                if(!user.getActiveEvents().contains(eventsResponse.getSignedEvent()))
                {
                    user.getActiveEvents().add(eventsResponse.getSignedEvent());
                    UserSerializer.getInstance().save(getContext(), user);
                }
            }

            return eventsResponse;
        }

        @Override
        public void deliverResult(EventsResponse data)
        {
            this.eventsResponse = data;
            super.deliverResult(data);
        }
    }
}
