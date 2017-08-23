package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.model.RunConfig;
import com.donbosco.android.porlosjovenes.model.Sponsor;
import com.donbosco.android.porlosjovenes.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiService
{
    @HTTP(method = "GET", path = "Usuario/Login")
    Call<User> login(@Query("email") String email, @Query("password") String password);

    @HTTP(method = "GET", path = "Sponsor")
    Call<ArrayList<Sponsor>> getSponsors();

    @HTTP(method = "GET", path = "Configuracion/GetConfiguracionIni")
    Call<RunConfig> getRunConfig();
}
