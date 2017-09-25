package com.donbosco.android.porlosjovenes.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.components.NavUpActivity;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.model.GenericResponse;
import com.donbosco.android.porlosjovenes.model.User;
import com.donbosco.android.porlosjovenes.util.ConversionUtils;

import java.util.HashMap;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class EventDetailActivity extends NavUpActivity
{
    private MaterialProgressBar pbEventDetailSignInOut;
    private FloatingActionButton fabEventDetailSignInOut;

    private SignInEventTask signInEventTask;
    private SignOutEventTask signOutEventTask;

    private Event event;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        event = (Event) getIntent().getSerializableExtra(ExtraKeys.EVENT);
        user = UserSerializer.getInstance().load(this);

        ImageView ivEventDetailImage = findViewById(R.id.iv_event_detail_image);
        Glide.with(this).load("http://clubdecorredores.com/carreras/2016/c508/e705/flyer.jpg").into(ivEventDetailImage);

        fabEventDetailSignInOut = findViewById(R.id.fab_event_detail_sign_in_out);
        fabEventDetailSignInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                signInOut();
            }
        });

        //Set remove icon if the user is already signed
        if(user.getActiveEvents().contains(event.getId()))
            fabEventDetailSignInOut.setImageResource(R.drawable.ic_close_black_24dp);

        pbEventDetailSignInOut = findViewById(R.id.pb_event_detail_signing_in_out);

        TextView tvEventDetailDate = findViewById(R.id.tv_event_detail_date);
        TextView tvEventDetailLocation = findViewById(R.id.tv_event_detail_location);
        TextView tvEventDetailDescription = findViewById(R.id.tv_event_detail_description);

        tvEventDetailDate.setText(ConversionUtils.net2JavaDate(event.getStartDate()));
        tvEventDetailLocation.setText(event.getLocation());
        tvEventDetailDescription.setText(event.getDescription());
    }

    private void signInOut()
    {
        if(user.getActiveEvents().contains(event.getId()))
            signOut();
        else
            signIn();
    }

    private void signIn()
    {
        if(signInEventTask == null || !signInEventTask.getStatus().equals(AsyncTask.Status.RUNNING))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
            builder.setMessage(R.string.want_sing_in_event);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    signInEventTask = new SignInEventTask();
                    signInEventTask.execute();
                }

            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                }
            });
            builder.show();
        }
    }

    private void signOut()
    {
        if(signOutEventTask == null || !signOutEventTask.getStatus().equals(AsyncTask.Status.RUNNING))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
            builder.setMessage(R.string.want_sing_out_event);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    signOutEventTask = new SignOutEventTask();
                    signOutEventTask.execute();
                }

            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                }
            });
            builder.show();
        }
    }

    private class SignInEventTask extends AsyncTask<Void, Integer, GenericResponse>
    {
        @Override
        protected void onPreExecute()
        {
            pbEventDetailSignInOut.setVisibility(View.VISIBLE);
        }

        @Override
        protected GenericResponse doInBackground(Void... voids)
        {
            HashMap<String, String> userData = new HashMap<>();
            userData.put(RestApiConstants.PARAM_EMAIL, user.getEmail());
            userData.put(RestApiConstants.PARAM_USER, user.getUserName());
            userData.put(RestApiConstants.PARAM_PASSWORD, user.getPassword());
            return RestApi.getInstance().signInEvent(userData, event.getId());
        }

        @Override
        protected void onPostExecute(GenericResponse genericResponse)
        {
            pbEventDetailSignInOut.setVisibility(View.INVISIBLE);

            if(genericResponse != null && genericResponse.getCode() == 0)
            {
                user.getActiveEvents().add(event.getId());
                UserSerializer.getInstance().save(EventDetailActivity.this, user);
                fabEventDetailSignInOut.setImageResource(R.drawable.ic_close_black_24dp);
            }
            else
            {
                Snackbar.make(findViewById(android.R.id.content), R.string.error_signing_in_event, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private class SignOutEventTask extends AsyncTask<Void, Integer, GenericResponse>
    {
        @Override
        protected void onPreExecute()
        {
            pbEventDetailSignInOut.setVisibility(View.VISIBLE);
        }

        @Override
        protected GenericResponse doInBackground(Void... voids)
        {
            HashMap<String, String> userData = new HashMap<>();
            userData.put(RestApiConstants.PARAM_EMAIL, user.getEmail());
            return RestApi.getInstance().signOutEvent(userData, event.getId());
        }

        @Override
        protected void onPostExecute(GenericResponse genericResponse)
        {
            pbEventDetailSignInOut.setVisibility(View.INVISIBLE);

            if(genericResponse != null && genericResponse.getCode() == 0)
            {
                user.getActiveEvents().remove(event.getId());
                UserSerializer.getInstance().save(EventDetailActivity.this, user);
                fabEventDetailSignInOut.setImageResource(R.drawable.ic_check_black_24dp);
            }
            else
            {
                Snackbar.make(findViewById(android.R.id.content), R.string.error_signing_out_event, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
