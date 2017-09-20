package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.constants.RestApiConstants;
import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.model.GenericResponse;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.model.WorkoutResultResponse;
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

    public GenericResponse signUp(HashMap<String, String> userData)
    {
        GenericResponse genericResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<GenericResponse> signUpCall = apiService.signUp(userData);
            genericResponse = signUpCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return genericResponse;
    }

    public GenericResponse recoverPassword(HashMap<String, String> userData)
    {
        GenericResponse genericResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<GenericResponse> recoverPasswordCall = apiService.recoverPassword(userData);
            genericResponse = recoverPasswordCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return genericResponse;
    }

    public GenericResponse changePassword(HashMap<String, String> userData, String newPassword, String confirmNewPassword)
    {
        GenericResponse genericResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<GenericResponse> changePasswordCall = apiService.changePassword(userData, newPassword, confirmNewPassword);
            genericResponse = changePasswordCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return genericResponse;
    }

    public WorkoutConfig getWorkoutConfig()
    {
        WorkoutConfig workoutConfig = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<WorkoutConfig> workoutConfigCall = apiService.getWorkoutConfig();
            workoutConfig = workoutConfigCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return workoutConfig;
    }

    public WorkoutResultResponse sendWorkoutResult(HashMap<String, String> workoutData, Callback<WorkoutResultResponse> callback)
    {
        WorkoutResultResponse workoutResultResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<WorkoutResultResponse> sendWorkoutResultCall = apiService.sendWorkoutResult(workoutData);
            sendWorkoutResultCall.enqueue(callback);
        }
        catch(Exception e)
        {

        }

        return workoutResultResponse;
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

    public ArrayList<Event> getEvents()
    {
        ArrayList<Event> events = new ArrayList<>();

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<ArrayList<Event>> eventsCall = apiService.getEvents();
            events = eventsCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return events;
    }

    private GenericResponse signInEvent(HashMap<String, String> user, long eventId)
    {
        GenericResponse genericResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<GenericResponse> signInEventCall = apiService.signInEvent(user, eventId);
            genericResponse = signInEventCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return genericResponse;
    }

    private GenericResponse signOutEvent(HashMap<String, String> user, long eventId)
    {
        GenericResponse genericResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<GenericResponse> signOutEventCall = apiService.signOutEvent(user, eventId);
            genericResponse = signOutEventCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return genericResponse;
    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl(RestApiConstants.API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
