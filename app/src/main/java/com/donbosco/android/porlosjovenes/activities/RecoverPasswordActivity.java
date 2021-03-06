package com.donbosco.android.porlosjovenes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.GenericResponse;
import com.donbosco.android.porlosjovenes.model.User;

import java.util.HashMap;

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
            recoverPasswordTask = new RecoverPasswordTask(etRecoverPasswordEmail.getText().toString());
            recoverPasswordTask.execute();
        }
    }

    private void startChangePassword(User user)
    {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra(ExtraKeys.COME_FROM_RECOVER, true);
        intent.putExtra(ExtraKeys.USER, user);
        startActivity(intent);
        finish();
    }

    private class RecoverPasswordTask extends AsyncTask<Void, Integer, GenericResponse>
    {
        private String email;

        public RecoverPasswordTask(String email)
        {
            this.email = email;
        }

        @Override
        protected void onPreExecute()
        {
            llRecoverPasswordData.setVisibility(View.INVISIBLE);
            pbRecoverPassword.setVisibility(View.VISIBLE);
        }

        @Override
        protected GenericResponse doInBackground(Void... voids)
        {
            HashMap<String, String> userData = new HashMap<>();
            userData.put(RestApiConstants.PARAM_EMAIL, email);
            userData.put(RestApiConstants.PARAM_USER, "");
            userData.put(RestApiConstants.PARAM_PASSWORD, "");

            return RestApi.getInstance().recoverPassword(userData);
        }

        @Override
        protected void onPostExecute(GenericResponse genericResponse)
        {
            pbRecoverPassword.setVisibility(View.GONE);

            if(genericResponse != null)
            {
                if(genericResponse.getCode() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPasswordActivity.this, R.style.Theme_AppCompat_Light_Dialog);
                    builder.setMessage(R.string.new_password_sent);
                    builder.setPositiveButton(R.string.change_password, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            User user = new User();
                            user.setEmail(email);
                            startChangePassword(user);
                        }

                    });
                    builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            finish();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
                else
                {
                    llRecoverPasswordData.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), genericResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
            else
            {
                llRecoverPasswordData.setVisibility(View.VISIBLE);
                Snackbar.make(findViewById(android.R.id.content), R.string.api_generic_error, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
