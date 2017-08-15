package com.donbosco.android.porlosjovenes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.activities.RunActivity;

public class ActivityFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);

        Button btnActivityBegin = rootView.findViewById(R.id.btn_activity_begin);
        btnActivityBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startRun();
            }
        });

        return rootView;
    }

    private void startRun()
    {
        Intent intent = new Intent(getContext(), RunActivity.class);
        startActivity(intent);
    }
}
