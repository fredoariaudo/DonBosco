package com.donbosco.android.porlosjovenes.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;

public class ChangePasswordActivity extends AppCompatActivity
{
    private LinearLayoutCompat llChangePasswordData;
    private ProgressBar pbChangePassword;

    private ChangePasswordTask changePasswordTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        llChangePasswordData = findViewById(R.id.ll_change_password_data);
        pbChangePassword = findViewById(R.id.pb_change_password);

        Button btnChangePassword = findViewById(R.id.btn_change_password);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                changePassword();
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(changePasswordTask != null)
            changePasswordTask.cancel(true);
    }

    private void changePassword()
    {
        changePasswordTask = new ChangePasswordTask();
        changePasswordTask.execute();
    }

    private class ChangePasswordTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            llChangePasswordData.setVisibility(View.INVISIBLE);
            pbChangePassword.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch(Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            pbChangePassword.setVisibility(View.GONE);
        }
    }
}
