package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
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
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.fragments.InitiativesFragment;
import com.donbosco.android.porlosjovenes.fragments.WorkoutHomeFragment;
import com.donbosco.android.porlosjovenes.fragments.EventsFragment;
import com.donbosco.android.porlosjovenes.fragments.SponsorsFragment;
import com.donbosco.android.porlosjovenes.model.User;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private NavigationView navigationView;

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
}
