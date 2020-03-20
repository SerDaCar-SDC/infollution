package com.serdacar.infollution.retrofit.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface APIEstaciones {
    String BASE_URL = "http://www.mambiente.madrid.es/opendata/";

    @Headers({
            "Accept: application/xml"
    })

    @GET("horario.xml")
    Call<Datos>obtenerDatos();
}
