package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.donbosco.android.porlosjovenes.R;

public class RecoverPasswordActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        Button btnRecoverPassword = findViewById(R.id.btn_recover_password);
        btnRecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startChangePassword();
            }
        });
    }

    private void startChangePassword()
    {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
