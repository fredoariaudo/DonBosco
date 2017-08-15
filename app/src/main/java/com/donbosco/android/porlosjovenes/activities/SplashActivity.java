package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.model.User;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkUser();
    }

    private void checkUser()
    {
        User user = UserSerializer.getInstance().load(this);
        Intent intent;

        if(user == null)
            intent = new Intent(this, LoginActivity.class);
        else
            intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();
    }
}
