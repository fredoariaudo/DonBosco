package com.donbosco.android.porlosjovenes.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.IntentActions;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.WorkoutConfigurationSerializer;
import com.donbosco.android.porlosjovenes.fragments.HistoryFragment;
import com.donbosco.android.porlosjovenes.fragments.InitiativesFragment;
import com.donbosco.android.porlosjovenes.fragments.WorkoutHomeFragment;
import com.donbosco.android.porlosjovenes.fragments.EventsFragment;
import com.donbosco.android.porlosjovenes.fragments.SponsorsFragment;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.services.OfflineWorkoutConfigurationService;
import com.donbosco.android.porlosjovenes.services.WorkoutService;

import java.io.Serializable;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private NavigationView navigationView;
    private BroadcastReceiver offlineWorkoutConfigurationServiceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        TextView tvNavUserName = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_user_name);
        TextView tvNavEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_email);

        User user = UserSerializer.getInstance().load(this);
        if(user != null)
        {
            tvNavUserName.setText(user.getUserName());

            if(!user.isGuest())
                tvNavEmail.setText(user.getEmail());
        }

        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null)
            selectNavItem(R.id.nav_activity);

        startService(new Intent(this,WorkoutService.class));
        Intent offlineWorkoutConfigurationServiceIntent = new Intent(this,OfflineWorkoutConfigurationService.class);
        offlineWorkoutConfigurationServiceIntent.putExtra(ExtraKeys.EMAIL,user.getEmail());
        startService(offlineWorkoutConfigurationServiceIntent);


        offlineWorkoutConfigurationServiceReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                WorkoutConfig workoutConfig = (WorkoutConfig) intent.getSerializableExtra(ExtraKeys.WORKOUT_CONFIG);
                WorkoutConfigurationSerializer.getInstance().save(HomeActivity.this,workoutConfig);
            }
        };
        registerReceiver(offlineWorkoutConfigurationServiceReceiver, new IntentFilter(IntentActions.ACTION_OFFLINE_CONFIGUARION_WORKOUT));
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.nav_activity:
                fragment = Fragment.instantiate(this, WorkoutHomeFragment.class.getName());
                break;

            case R.id.nav_sponsors:
                fragment = Fragment.instantiate(this, SponsorsFragment.class.getName());
                break;

            case R.id.nav_events:
                fragment = Fragment.instantiate(this, EventsFragment.class.getName());
                break;

            case R.id.nav_initiatives:
                fragment = Fragment.instantiate(this, InitiativesFragment.class.getName());
                break;

            case R.id.nav_profile:
                openProfile();
                break;

            case R.id.nav_history:
                fragment = Fragment.instantiate(this, HistoryFragment.class.getName());
                break;


            case R.id.donate_more:
                fragment = Fragment.instantiate(this, InitiativesFragment.class.getName());
                break;
        }

        if(fragment != null)
        {
            fragmentManager.beginTransaction().replace(R.id.content_home, fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectNavItem(int menuItemId)
    {
        navigationView.getMenu().findItem(menuItemId).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().findItem(menuItemId));
    }

    private void openProfile()
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void donateMore()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RestApiConstants.DONATE_MORE_URL));
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

}
