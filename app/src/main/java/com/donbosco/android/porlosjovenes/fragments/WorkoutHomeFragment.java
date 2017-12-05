package com.donbosco.android.porlosjovenes.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.activities.RunActivity;
import com.donbosco.android.porlosjovenes.adapters.WorkoutTypePagerAdapter;
import com.donbosco.android.porlosjovenes.application.AppInfo;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;

public class WorkoutHomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<WorkoutConfig>
{
    private static final int WORKOUT_CONFIG_LOADER_ID = 1;

    private View rootView;
    private ViewPager vpWorkoutHomeType;
    private WorkoutTypePagerAdapter workoutTypePagerAdapter;
    private ProgressBar pbWorkoutHome;
    private FloatingActionButton fabWorkoutHomeBegin;

    private WorkoutConfig workoutConfig;
    private Snackbar snackbarError;
    private Snackbar snackbarNoConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_workout_home, container, false);

        vpWorkoutHomeType = rootView.findViewById(R.id.vp_workout_home_type);
        workoutTypePagerAdapter = new WorkoutTypePagerAdapter(getContext());
        vpWorkoutHomeType.setAdapter(workoutTypePagerAdapter);

        pbWorkoutHome = rootView.findViewById(R.id.pb_workout_home);

        fabWorkoutHomeBegin = rootView.findViewById(R.id.fab_workout_home_begin);
        fabWorkoutHomeBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startWorkout();
            }
        });


        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        //Init the loader whenever the fragment is visible
        fabWorkoutHomeBegin.setVisibility(View.INVISIBLE);
        getLoaderManager().initLoader(WORKOUT_CONFIG_LOADER_ID, null, this);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        //Destroy the loader to force reload workout config next time fragment is visible
        getLoaderManager().destroyLoader(WORKOUT_CONFIG_LOADER_ID);
    }

    @Override
    public Loader<WorkoutConfig> onCreateLoader(int id, Bundle args)
    {
        return new WorkoutConfigLoader(getContext(), pbWorkoutHome);
    }

    @Override
    public void onLoadFinished(Loader<WorkoutConfig> loader, WorkoutConfig data)
    {
        pbWorkoutHome.setVisibility(View.GONE);


        if(data != null)
        {
            if(data.getCode() == 0)
            {
                fabWorkoutHomeBegin.show();
                workoutConfig = data;
            }
            else
            {
                if(data.getMessage() != null)
                {
                    Snackbar.make(rootView, data.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.retry, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fabWorkoutHomeBegin.setVisibility(View.INVISIBLE);
                                    getLoaderManager().restartLoader(WORKOUT_CONFIG_LOADER_ID, null, WorkoutHomeFragment.this);
                                }
                            }).show();
                }
                else
                {
                    Snackbar.make(rootView, R.string.error_initial_config, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.retry, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fabWorkoutHomeBegin.setVisibility(View.INVISIBLE);
                                    getLoaderManager().restartLoader(WORKOUT_CONFIG_LOADER_ID, null, WorkoutHomeFragment.this);
                                }
                            }).show();

                }
            }
        }
        else
        {
            if (AppInfo.connected)
            {
                Snackbar.make(rootView, R.string.error_initial_config, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fabWorkoutHomeBegin.setVisibility(View.INVISIBLE);
                                getLoaderManager().restartLoader(WORKOUT_CONFIG_LOADER_ID, null, WorkoutHomeFragment.this);
                            }
                        }).show();
            }
            else
            {
                Snackbar.make(rootView, R.string.error_connection, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fabWorkoutHomeBegin.setVisibility(View.INVISIBLE);
                                getLoaderManager().restartLoader(WORKOUT_CONFIG_LOADER_ID, null, WorkoutHomeFragment.this);
                            }
                        }).show();
            }


        }
    }

    @Override
    public void onLoaderReset(Loader<WorkoutConfig> loader)
    {
    }

    private void startWorkout()
    {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            int selectedWorkout = workoutTypePagerAdapter.getWorkoutTypeAt(vpWorkoutHomeType.getCurrentItem());
            workoutConfig.setWorkoutType(selectedWorkout);
            Intent intent = new Intent(getContext(), RunActivity.class);
            intent.putExtra(ExtraKeys.WORKOUT_CONFIG, workoutConfig);
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.location_disabled_message);
            builder.setPositiveButton(R.string.enable, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }

    private static class WorkoutConfigLoader extends AsyncTaskLoader<WorkoutConfig>
    {
        private ProgressBar progressBar;

        public WorkoutConfigLoader(Context context, ProgressBar progressBar)
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
        public WorkoutConfig loadInBackground()
        {
            User user = UserSerializer.getInstance().load(getContext());
            return RestApi.getInstance().getWorkoutConfig(user.getEmail());
        }
    }
}
