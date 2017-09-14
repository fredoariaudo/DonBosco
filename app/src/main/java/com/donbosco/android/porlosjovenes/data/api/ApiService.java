package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.model.RunConfig;
import com.donbosco.android.porlosjovenes.model.RunResultResponse;
import com.donbosco.android.porlosjovenes.model.UserResponse;
import com.donbosco.android.porlosjovenes.model.Sponsor;
import com.donbosco.android.porlosjovenes.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiService
{
    @HTTP(method = "GET", path = "Usuario/Login")
    Call<User> login(@Query("email") String email, @Query("password") String password);

    @HTTP(method = "POST", path = "Usuario", hasBody = true)
    Call<UserResponse> signUp(@Body HashMap<String, String> user);

    @HTTP(method = "POST", path = "Usuario/RecuperarPassword", hasBody = true)
    Call<UserResponse> recoverPassword(@Body HashMap<String, String> user, @Query("emailAlternativo") String alternativeEmail);

    @HTTP(method = "POST", path = "Usuario/ActualizarPassword", hasBody = true)
    Call<UserResponse> changePassword(@Body HashMap<String, String> user, @Query("nuevaPass") String newPassword, @Query("confirmarPass") String confirmNewPassword);

    @HTTP(method = "GET", path = "Empresa")
    Call<ArrayList<Sponsor>> getSponsors();

    @HTTP(method = "GET", path = "Evento")
    Call<ArrayList<Event>> getEvents();

    @HTTP(method = "GET", path = "Configuracion/GetConfiguracionIni")
    Call<RunConfig> getRunConfig();

    @HTTP(method = "POST", path = "Actividad", hasBody = true)
    Call<RunResultResponse> sendRunResult(@Body HashMap<String, String> runData);
}
