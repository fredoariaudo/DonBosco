package com.donbosco.android.porlosjovenes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;

public class InitiativesFragment extends Fragment
{
    private ProgressBar pbInitiatives;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_initiatives, container, false);

        RecyclerView rvInitiatives = rootView.findViewById(R.id.rv_initiatives);

        pbInitiatives = rootView.findViewById(R.id.pb_initiatives);

        return rootView;
    }
}
