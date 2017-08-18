package com.donbosco.android.porlosjovenes.data.api;

import com.donbosco.android.porlosjovenes.model.RunConfig;
import com.donbosco.android.porlosjovenes.model.Sponsor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.HTTP;

public interface ApiService
{
    @HTTP(method = "GET", path = "Sponsor")
    Call<ArrayList<Sponsor>> getSponsors();

    @HTTP(method = "GET", path = "Configuracion/GetConfiguracionIni")
    Call<RunConfig> getRunConfig();
}
