package com.donbosco.android.porlosjovenes.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class PljFirebaseInstanceIdService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token)
    {
        //TODO: Send registration token to the server
    }
}
