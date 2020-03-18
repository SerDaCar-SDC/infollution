package com.serdacar.infollution.retrofit.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIEstaciones {
    String BASE_URL = "http://www.mambiente.madrid.es/opendata/";

    @Headers({
            "Accept: application/xml"
            //"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            //"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36"
    })

    /*@GET("horario.xml")
    Call<Datos>obtenerDatosPorCodigo(@Query("estacion") String estacion);*/

    @GET("horario.xml")
    Call<Datos>obtenerDatos();

}
