package com.donbosco.android.porlosjovenes.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.ExtraKeys;

public class ChangePasswordActivity extends AppCompatActivity
{
    private LinearLayoutCompat llChangePasswordData;
    private EditText etChangePasswordCurrent;
    private EditText etChangePasswordNew;
    private EditText etChangePasswordConfirmNew;
    private ProgressBar pbChangePassword;

    private ChangePasswordTask changePasswordTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        llChangePasswordData = findViewById(R.id.ll_change_password_data);
        etChangePasswordCurrent = findViewById(R.id.et_change_password_current);
        etChangePasswordNew = findViewById(R.id.et_change_password_new);
        etChangePasswordConfirmNew = findViewById(R.id.et_change_password_confirm_new);
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
        String currentPassword = etChangePasswordCurrent.getText().toString();
        String newPassword = etChangePasswordNew.getText().toString();
        String confirmNewPassword = etChangePasswordConfirmNew.getText().toString();

        if(TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword))
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.must_fill_all_fields, Snackbar.LENGTH_SHORT).show();
        }
        else if(!newPassword.equals(confirmNewPassword))
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.passwords_do_not_match, Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            changePasswordTask = new ChangePasswordTask();
            changePasswordTask.execute();
        }
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
                Thread.sleep(1500);
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

            int message = getIntent().getBooleanExtra(ExtraKeys.COME_FROM_RECOVER, false) ? R.string.password_changed_successfully_login : R.string.password_changed_successfully;

            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this, R.style.Theme_AppCompat_Light_Dialog);
            builder.setMessage(message);
            builder.setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
}
