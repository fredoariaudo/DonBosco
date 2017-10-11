package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.User;

public class LoginActivity extends AppCompatActivity
{
    private final static int SIGN_UP_REQUEST = 1;

    private LinearLayoutCompat llLoginData;
    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private ProgressBar pbLogin;

    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        llLoginData = findViewById(R.id.ll_login_data);
        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
        pbLogin = findViewById(R.id.pb_login);

        TextView tvLoginSignUp = findViewById(R.id.tv_login_sign_up);
        tvLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startSignUp();
            }
        });

        TextView tvLoginForgotPassword = findViewById(R.id.tv_login_forgot_password);
        tvLoginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startRecoverPassword();
            }
        });

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                login();
            }
        });

        Button btnLoginGuest = findViewById(R.id.btn_login_guest);
        btnLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                continueAsGuest();
            }
        });
        
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(loginTask != null)
            loginTask.cancel(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == SIGN_UP_REQUEST && resultCode == RESULT_OK)
        {
            finish();
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startSignUp()
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, SIGN_UP_REQUEST);
    }

    private void startRecoverPassword()
    {
        Intent intent = new Intent(this, RecoverPasswordActivity.class);
        startActivity(intent);
    }

    private void login()
    {
        if(!TextUtils.isEmpty(etLoginEmail.getText()) && !TextUtils.isEmpty(etLoginPassword.getText()))
        {
            loginTask = new LoginTask(etLoginEmail.getText().toString(), etLoginPassword.getText().toString());
            loginTask.execute();
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.must_type_email_password, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void continueAsGuest()
    {
        User user = new User();
        user.setUserName(RestApiConstants.GUEST_USER_DEFAULT_USER_NAME);
        user.setEmail(RestApiConstants.GUEST_USER_DEFAULT_EMAIL);
        user.setGuest(true);
        UserSerializer.getInstance().save(this, user);
        startHomeActivity();
    }

    private void startHomeActivity()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private class LoginTask extends AsyncTask<Void, Integer, User>
    {
        private String email;
        private String password;

        public LoginTask(String email, String password)
        {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute()
        {
            llLoginData.setVisibility(View.INVISIBLE);
            pbLogin.setVisibility(View.VISIBLE);
        }

        @Override
        protected User doInBackground(Void... voids)
        {
            User user = RestApi.getInstance().login(email, password);

            if(user != null)
            {
                user.setPassword(password);
                UserSerializer.getInstance().save(LoginActivity.this, user);
            }

            return user;
        }

        @Override
        protected void onPostExecute(User user)
        {
            pbLogin.setVisibility(View.GONE);

            if(user != null)
            {
                startHomeActivity();
            }
            else
            {
                llLoginData.setVisibility(View.VISIBLE);
                Snackbar.make(findViewById(android.R.id.content), R.string.wrong_email_or_password, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
