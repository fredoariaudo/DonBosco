package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.model.Sponsor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi
{
    private final static String BASE_URL = "http://172.16.129.80/DonBoscoWebAPI/api/";

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
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
