package com.donbosco.android.porlosjovenes.activities;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.donbosco.android.porlosjovenes.R;

public class SignUpActivity extends AppCompatActivity
{
    private LinearLayoutCompat llSignUpData;
    private EditText etSignUpEmail;
    private EditText etSignUpUser;
    private EditText etSignUpPassword;
    private ProgressBar pbSignUp;

    private SignUpTask signUpTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        llSignUpData = findViewById(R.id.ll_sign_up_data);
        etSignUpEmail = findViewById(R.id.et_sign_up_email);
        etSignUpUser = findViewById(R.id.et_sign_up_user);
        etSignUpPassword = findViewById(R.id.et_sign_up_password);
        pbSignUp = findViewById(R.id.pb_sign_up);

        Button btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                signUp();
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(signUpTask != null)
            signUpTask.cancel(true);
    }

    private void signUp()
    {
        if(!TextUtils.isEmpty(etSignUpEmail.getText()) && !TextUtils.isEmpty(etSignUpUser.getText()) && !TextUtils.isEmpty(etSignUpPassword.getText()))
        {
            signUpTask = new SignUpTask();
            signUpTask.execute();
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.must_fill_all_fields, Snackbar.LENGTH_SHORT).show();
        }
    }

    private class SignUpTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            llSignUpData.setVisibility(View.INVISIBLE);
            pbSignUp.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                Thread.sleep(3000);
            }
            catch(Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            llSignUpData.setVisibility(View.VISIBLE);
            pbSignUp.setVisibility(View.INVISIBLE);
        }
    }
}
