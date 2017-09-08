package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;

public class RecoverPasswordActivity extends AppCompatActivity
{
    private LinearLayoutCompat llRecoverPasswordData;
    private EditText etRecoverPasswordEmail;
    private ProgressBar pbRecoverPassword;

    private RecoverPasswordTask recoverPasswordTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        llRecoverPasswordData = findViewById(R.id.ll_recover_password_data);
        etRecoverPasswordEmail = findViewById(R.id.et_recover_password_email);
        pbRecoverPassword = findViewById(R.id.pb_recover_password);

        Button btnRecoverPassword = findViewById(R.id.btn_recover_password);
        btnRecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                recoverPassword();
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(recoverPasswordTask != null)
            recoverPasswordTask.cancel(true);
    }

    private void recoverPassword()
    {
        if(TextUtils.isEmpty(etRecoverPasswordEmail.getText()))
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.must_type_email, Snackbar.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(etRecoverPasswordEmail.getText()).matches())
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.wrong_email_format, Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            recoverPasswordTask = new RecoverPasswordTask();
            recoverPasswordTask.execute();
        }
    }

    private void startChangePassword()
    {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra(ExtraKeys.COME_FROM_RECOVER, true);
        startActivity(intent);
        finish();
    }

    private class RecoverPasswordTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            llRecoverPasswordData.setVisibility(View.INVISIBLE);
            pbRecoverPassword.setVisibility(View.VISIBLE);
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
            pbRecoverPassword.setVisibility(View.GONE);
            startChangePassword();
        }
    }
}
