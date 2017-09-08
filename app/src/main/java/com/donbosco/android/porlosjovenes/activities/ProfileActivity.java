package com.donbosco.android.porlosjovenes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.donbosco.android.porlosjovenes.R;
import com.donbosco.android.porlosjovenes.adapters.ProfileOptionRvAdapter;
import com.donbosco.android.porlosjovenes.adapters.RvAdapterListener;
import com.donbosco.android.porlosjovenes.components.NavUpActivity;
import com.donbosco.android.porlosjovenes.data.UserSerializer;
import com.donbosco.android.porlosjovenes.model.ProfileOption;
import com.donbosco.android.porlosjovenes.model.User;

public class ProfileActivity extends NavUpActivity implements RvAdapterListener
{
    private ProfileOptionRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tvProfileUserName = findViewById(R.id.tv_profile_user_name);
        TextView tvProfileEmail = findViewById(R.id.tv_profile_email);

        User user = UserSerializer.getInstance().load(this);
        if(user != null)
        {
            tvProfileUserName.setText(user.getUserName());
            tvProfileEmail.setText(user.getEmail());
        }

        RecyclerView rvProfileOptions = findViewById(R.id.rv_profile_options);
        rvProfileOptions.setLayoutManager(new LinearLayoutManager(this));
        rvProfileOptions.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new ProfileOptionRvAdapter(this);
        rvProfileOptions.setAdapter(adapter);

        //Initialize options adapter
        adapter.add(new ProfileOption(ProfileOption.CHANGE_PASSWORD_ID, getString(R.string.change_password)));
        adapter.add(new ProfileOption(ProfileOption.LOGOUT, getString(R.string.logout)));
    }

    @Override
    public void onItemClick(View v, int itemPosition)
    {
        ProfileOption profileOption = adapter.getItems().get(itemPosition);

        switch(profileOption.getId())
        {
            case ProfileOption.CHANGE_PASSWORD_ID:
                startChangePassword();
                break;

            case ProfileOption.LOGOUT:
                break;
        }
    }

    @Override
    public boolean onItemLongClick(View v, int itemPosition)
    {
        return false;
    }

    private void startChangePassword()
    {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
