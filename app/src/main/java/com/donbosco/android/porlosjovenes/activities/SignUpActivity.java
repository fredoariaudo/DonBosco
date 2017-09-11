package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
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
import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.data.api.RestApi;
import com.donbosco.android.porlosjovenes.model.UserResponse;
import com.donbosco.android.porlosjovenes.model.User;

import java.util.HashMap;

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
            signUpTask = new SignUpTask(etSignUpEmail.getText().toString(), etSignUpUser.getText().toString(), etSignUpPassword.getText().toString());
            signUpTask.execute();
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.must_fill_all_fields, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void startHomeActivity()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    private class SignUpTask extends AsyncTask<Void, Integer, UserResponse>
    {
        private String email;
        private String userName;
        private String password;

        public SignUpTask(String email, String userName, String password)
        {
            this.email = email;
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected void onPreExecute()
        {
            llSignUpData.setVisibility(View.INVISIBLE);
            pbSignUp.setVisibility(View.VISIBLE);
        }

        @Override
        protected UserResponse doInBackground(Void... voids)
        {
            HashMap<String, String> userData = new HashMap<>();
            userData.put(RestApiConstants.PARAM_USER, userName);
            userData.put(RestApiConstants.PARAM_PASSWORD, password);
            userData.put(RestApiConstants.PARAM_EMAIL, email);

            UserResponse userResponse = RestApi.getInstance().signUp(userData);
            if(userResponse != null && userResponse.getCode() == 0)
            {
                User user = new User();
                user.setEmail(email);
                user.setUserName(userName);
                user.setPassword(password);
                UserSerializer.getInstance().save(SignUpActivity.this, user);
            }

            return userResponse;
        }

        @Override
        protected void onPostExecute(UserResponse userResponse)
        {
            pbSignUp.setVisibility(View.GONE);

            if(userResponse != null)
            {
                if(userResponse.getCode() == 0)
                {
                    startHomeActivity();
                }
                else
                {
                    llSignUpData.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), userResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
            else
            {
                llSignUpData.setVisibility(View.VISIBLE);
                Snackbar.make(findViewById(android.R.id.content), R.string.api_generic_error, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
