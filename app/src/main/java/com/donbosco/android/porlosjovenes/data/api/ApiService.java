package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.model.Event;
import com.donbosco.android.porlosjovenes.model.GenericResponse;
import com.donbosco.android.porlosjovenes.model.WorkoutConfig;
import com.donbosco.android.porlosjovenes.model.WorkoutResultResponse;
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
    Call<GenericResponse> signUp(@Body HashMap<String, String> user);

    @HTTP(method = "POST", path = "Usuario/RecuperarPassword", hasBody = true)
    Call<GenericResponse> recoverPassword(@Body HashMap<String, String> user);

    @HTTP(method = "POST", path = "Usuario/ActualizarPassword", hasBody = true)
    Call<GenericResponse> changePassword(@Body HashMap<String, String> user, @Query("nuevaPass") String newPassword, @Query("confirmarPass") String confirmNewPassword);

    @HTTP(method = "GET", path = "Empresa")
    Call<ArrayList<Sponsor>> getSponsors();

    @HTTP(method = "GET", path = "Evento")
    Call<ArrayList<Event>> getEvents();

    @HTTP(method = "POST", path = "Usuario/AltaEvento")
    Call<GenericResponse> signInEvent(@Body HashMap<String, String> user, @Query("idEvento") long eventId);

    @HTTP(method = "POST", path = "Usuario/BajaEvento")
    Call<GenericResponse> signOutEvent(@Body HashMap<String, String> user, @Query("idEvento") long eventId);

    @HTTP(method = "GET", path = "Configuracion/GetConfiguracionIni")
    Call<WorkoutConfig> getWorkoutConfig();

    @HTTP(method = "POST", path = "Actividad", hasBody = true)
    Call<WorkoutResultResponse> sendWorkoutResult(@Body HashMap<String, String> workoutData);
}
