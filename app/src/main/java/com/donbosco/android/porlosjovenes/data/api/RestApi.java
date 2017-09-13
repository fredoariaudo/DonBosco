package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.model.RunConfig;
import com.donbosco.android.porlosjovenes.model.RunResultResponse;
import com.donbosco.android.porlosjovenes.model.UserResponse;
import com.donbosco.android.porlosjovenes.model.Sponsor;
import com.donbosco.android.porlosjovenes.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi
{
    private static RestApi restApi;

    private RestApi()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApi getInstance()
    {
        if(restApi == null)
            restApi = new RestApi();

        return restApi;
    }

    public User login(String email, String password)
    {
        User user = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<User> loginCall = apiService.login(email, password);
            user = loginCall.execute().body();
        }
        catch(Exception e)
        {
        }

        return user;
    }

    public UserResponse signUp(HashMap<String, String> userData)
    {
        UserResponse userResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<UserResponse> signUpCall = apiService.signUp(userData);
            userResponse = signUpCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return userResponse;
    }

    public UserResponse recoverPassword(HashMap<String, String> userData, String alternativeEmail)
    {
        UserResponse userResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<UserResponse> recoverPasswordCall = apiService.recoverPassword(userData, alternativeEmail);
            userResponse = recoverPasswordCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return userResponse;
    }

    public UserResponse changePassword(HashMap<String, String> userData, String newPassword, String confirmNewPassword)
    {
        UserResponse userResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<UserResponse> changePasswordCall = apiService.changePassword(userData, newPassword, confirmNewPassword);
            userResponse = changePasswordCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return userResponse;
    }

    public RunConfig getRunConfig()
    {
        RunConfig runConfig = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<RunConfig> runConfigCall = apiService.getRunConfig();
            runConfig = runConfigCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return runConfig;
    }

    public RunResultResponse sendRunResult(HashMap<String, String> runData, Callback<RunResultResponse> callback)
    {
        RunResultResponse runResultResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<RunResultResponse> sendRunResultCall = apiService.sendRunResult(runData);
            sendRunResultCall.enqueue(callback);
        }
        catch(Exception e)
        {

        }

        return runResultResponse;
    }

    public ArrayList<Sponsor> getSponsors()
    {
        ArrayList<Sponsor> sponsors = new ArrayList<>();

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<ArrayList<Sponsor>> sponsorsCall = apiService.getSponsors();
            sponsors = sponsorsCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return sponsors;
    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl(RestApiConstants.API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
