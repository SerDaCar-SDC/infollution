package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.serdacar.infollution.database.EstacionDataSource;
import com.serdacar.infollution.model.Estacion;
import com.serdacar.infollution.retrofit.RetrofitClient;
import com.serdacar.infollution.retrofit.model.APIEstaciones;
import com.serdacar.infollution.retrofit.model.DatoHorario;
import com.serdacar.infollution.retrofit.model.Datos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FirstActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";
    FirebaseAuth fbAuth;

    TextView tv;
    ImageView ivLogo;
    String email;

    private LocationManager miLocalizacion;
    private Location loc;

    private double miLatitud;
    private double miLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.colorAzulOscuro)));

        ivLogo = findViewById(R.id.ivMenuLogo);
        ivLogo.setEnabled(false);

        email = getIntent().getStringExtra(RegisterActivity.CLAVE_EMAIL);

        fbAuth = FirebaseAuth.getInstance();

        /* * * *  ENCONTRAR UBICACION * * * * * * * * * * * * * * * * * */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Es necesario que active la localización GPS", Toast.LENGTH_LONG).show();
            return;
        } else {
            miLocalizacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = miLocalizacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


        miLatitud = loc.getLatitude();
        miLongitud = loc.getLongitude();

        EstacionDataSource persistencia = new EstacionDataSource(this);

        ArrayList<Estacion> listaEstaciones = persistencia.leerEstacionLista();
        final Estacion estacionCerca = encontrarEstMasCerca(listaEstaciones, miLatitud, miLongitud);

        estacionCerca.getLatitud();
        estacionCerca.getLongitud();

        Retrofit retrofit = RetrofitClient.getClient(APIEstaciones.BASE_URL);
        APIEstaciones apiEstaciones = retrofit.create(APIEstaciones.class);
        Call<Datos> call  = apiEstaciones.obtenerDatos();

        call.enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if(response.isSuccessful()) {
                    String so2 = "";
                    String co = "";
                    String no = "";
                    String no2 = "";

                    Datos d = response.body();
                    List<DatoHorario> listaEstaciones = d.getDatoHorario();

                    String codigoEstacion;
                    int numeroEstacion;
                    String codigoRecuperado;

                    int horaActual = comprobarHora();

                    for(int i = 0; i < listaEstaciones.size(); i++) {
                        codigoEstacion = listaEstaciones.get(i).getEstacion();

                        if (codigoEstacion.substring(0, 1).equals("0")
                                && estacionCerca.getCodigoCorto() == Integer.parseInt(listaEstaciones.get(i).getEstacion())) {
                            codigoRecuperado = codigoEstacion.substring(1, 3);
                            numeroEstacion = Integer.parseInt(codigoRecuperado);

                            if (estacionCerca.getCodigoCorto() == numeroEstacion) {
                                if (listaEstaciones.get(i).getMagnitud().equals("1")) {
                                    if (horaActual == 1) {
                                        so2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        so2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        so2 = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        so2 = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        so2 = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        so2 = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        so2 = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        so2 = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        so2 = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        so2 = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        so2 = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        so2 = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        so2 = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        so2 = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        so2 = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        so2 = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        so2 = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        so2 = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        so2 = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        so2 = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        so2 = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        so2 = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        so2 = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        so2 = (listaEstaciones.get(i).getH23());
                                    }

                                }
                                if (listaEstaciones.get(i).getMagnitud().equals("6")) {
                                    if (horaActual == 1) {
                                        co = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        co = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        co = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        co = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        co = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        co = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        co = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        co = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        co = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        co = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        co = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        co = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        co = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        co = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        co = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        co = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        co = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        co = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        co = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        co = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        co = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        co = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        co = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        co = (listaEstaciones.get(i).getH23());
                                    }

                                }
                                if (listaEstaciones.get(i).getMagnitud().equals("7")) {
                                    if (horaActual == 1) {
                                        no = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        no = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        no = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        no = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        no = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        no = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        no = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        no = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        no = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        no = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        no = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        no = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        no = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        no = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        no = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        no = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        no = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        no = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        no = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        no = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        no = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        no = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        no = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        no = (listaEstaciones.get(i).getH23());
                                    }

                                }
                                if (listaEstaciones.get(i).getMagnitud().equals("8")) {
                                    if (horaActual == 1) {
                                        no2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        no2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        no2 = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        no2 = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        no2 = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        no2 = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        no2 = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        no2 = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        no2 = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        no2 = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        no2 = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        no2 = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        no2 = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        no2 = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        no2 = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        no2 = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        no2 = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        no2 = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        no2 = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        no2 = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        no2 = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        no2 = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        no2 = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        no2 = (listaEstaciones.get(i).getH23());
                                    }
                                }
                                if (so2.equals("")) {
                                    so2 = "0";
                                }else if (co.equals("")) {
                                    co = "0";
                                }else if (no.equals("")) {
                                    no = "0";
                                }else if (no2.equals("")) {
                                    no2 = "0";
                                }
                            }
                        } else if (codigoEstacion.substring(0,2).equals("00")
                                && estacionCerca.getCodigoCorto() == Integer.parseInt(listaEstaciones.get(i).getEstacion())){
                            codigoRecuperado = codigoEstacion.substring(2, 3);
                            numeroEstacion = Integer.parseInt(codigoRecuperado);

                            if (estacionCerca.getCodigoCorto() == numeroEstacion) {
                                if (listaEstaciones.get(i).getMagnitud().equals("1")) {
                                    if (horaActual == 1) {
                                        so2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        so2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        so2 = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        so2 = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        so2 = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        so2 = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        so2 = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        so2 = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        so2 = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        so2 = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        so2 = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        so2 = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        so2 = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        so2 = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        so2 = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        so2 = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        so2 = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        so2 = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        so2 = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        so2 = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        so2 = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        so2 = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        so2 = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        so2 = (listaEstaciones.get(i).getH23());
                                    }

                                }
                                if (listaEstaciones.get(i).getMagnitud().equals("6")) {
                                    if (horaActual == 1) {
                                        co = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        co = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        co = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        co = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        co = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        co = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        co = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        co = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        co = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        co = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        co = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        co = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        co = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        co = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        co = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        co = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        co = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        co = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        co = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        co = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        co = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        co = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        co = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        co = (listaEstaciones.get(i).getH23());
                                    }

                                }
                                if (listaEstaciones.get(i).getMagnitud().equals("7")) {
                                    if (horaActual == 1) {
                                        no = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        no = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        no = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        no = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        no = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        no = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        no = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        no = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        no = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        no = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        no = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        no = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        no = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        no = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        no = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        no = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        no = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        no = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        no = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        no = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        no = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        no = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        no = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        no = (listaEstaciones.get(i).getH23());
                                    }
                                }
                                if (listaEstaciones.get(i).getMagnitud().equals("8")) {
                                    if (horaActual == 1) {
                                        no2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 2) {
                                        no2 = (listaEstaciones.get(i).getH01());
                                    } else if (horaActual == 3) {
                                        no2 = (listaEstaciones.get(i).getH02());
                                    } else if (horaActual == 4) {
                                        no2 = (listaEstaciones.get(i).getH03());
                                    } else if (horaActual == 5) {
                                        no2 = (listaEstaciones.get(i).getH04());
                                    } else if (horaActual == 6) {
                                        no2 = (listaEstaciones.get(i).getH05());
                                    } else if (horaActual == 7) {
                                        no2 = (listaEstaciones.get(i).getH06());
                                    } else if (horaActual == 8) {
                                        no2 = (listaEstaciones.get(i).getH07());
                                    } else if (horaActual == 9) {
                                        no2 = (listaEstaciones.get(i).getH08());
                                    } else if (horaActual == 10) {
                                        no2 = (listaEstaciones.get(i).getH09());
                                    } else if (horaActual == 11) {
                                        no2 = (listaEstaciones.get(i).getH10());
                                    } else if (horaActual == 12) {
                                        no2 = (listaEstaciones.get(i).getH11());
                                    } else if (horaActual == 13) {
                                        no2 = (listaEstaciones.get(i).getH12());
                                    } else if (horaActual == 14) {
                                        no2 = (listaEstaciones.get(i).getH13());
                                    } else if (horaActual == 15) {
                                        no2 = (listaEstaciones.get(i).getH14());
                                    } else if (horaActual == 16) {
                                        no2 = (listaEstaciones.get(i).getH15());
                                    } else if (horaActual == 17) {
                                        no2 = (listaEstaciones.get(i).getH16());
                                    } else if (horaActual == 18) {
                                        no2 = (listaEstaciones.get(i).getH17());
                                    } else if (horaActual == 19) {
                                        no2 = (listaEstaciones.get(i).getH18());
                                    } else if (horaActual == 20) {
                                        no2 = (listaEstaciones.get(i).getH19());
                                    } else if (horaActual == 21) {
                                        no2 = (listaEstaciones.get(i).getH20());
                                    } else if (horaActual == 22) {
                                        no2 = (listaEstaciones.get(i).getH21());
                                    } else if (horaActual == 23) {
                                        no2 = (listaEstaciones.get(i).getH22());
                                    } else if (horaActual == 0) {
                                        no2 = (listaEstaciones.get(i).getH23());
                                    }
                                }
                            }
                            if (so2.equals("")) {
                                so2 = "0";
                            }else if (co.equals("")) {
                                co = "0";
                            }else if (no.equals("")) {
                                no = "0";
                            }else if (no2.equals("")) {
                                no2 = "0";
                            }
                        }
                    }
                    double dioxidoAzufre = Double.parseDouble(so2);
                    double monoxidoNitrogeno = Double.parseDouble(co);
                    double monoxidoCarbono = Double.parseDouble(no);
                    double dioxidoNitrogeno = Double.parseDouble(no2);

                    AnyChartView anyChartView = findViewById(R.id.acGrafica);

                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Dióxido de Azufre (SO2)", dioxidoAzufre));
                    data.add(new ValueDataEntry("Monóxido de Nitrógeno (CO)", monoxidoNitrogeno));
                    data.add(new ValueDataEntry("Monóxido de Carbono (NO)", monoxidoCarbono));
                    data.add(new ValueDataEntry("Dióxido de Nitrógeno (NO2)", dioxidoNitrogeno));

                    Pie pie = AnyChart.pie();
                    pie.data(data);
                    pie.animation(true);
                    pie.title("Estación más cercana: " + estacionCerca.getNombre()).padding(20d, 0d, 0d, 0d);

                    pie.labels().position("outside");
                    pie.legend().title().text("").padding(70d, 50d, 0d, 50d);
                    pie.legend().position("center-bottom").itemsLayout(LegendLayout.HORIZONTAL).align(Align.CENTER);

                    anyChartView.setChart(pie);
                    pie.background().stroke();

                } else {
                    Log.e("ERROR ON RESPONSE", "ERROR: " + response.code());
                }
            }

            public int comprobarHora() {
                Date hora;
                Calendar gregorian = new GregorianCalendar();
                hora = gregorian.getTime();
                int horaActual = hora.getHours();

                return horaActual;
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                Log.e("ERROR ON FAILURE", "ERROR: " + t.getMessage());
            }
        });
    }

    public Estacion encontrarEstMasCerca(ArrayList<Estacion> lista, double posiX, double posiY) {
        Estacion resMenosdistante = null;

        double distancia;
        double distanciaMenor = 9999999.99;

        for (Estacion estacion : lista) {
            distancia = Math.sqrt(Math.pow(posiX - estacion.getLatitud(), 2) + Math.pow(posiY - estacion.getLongitud(), 2));
            if (distancia < distanciaMenor) {
                distanciaMenor = distancia;
                resMenosdistante = estacion;
            }
        }
        return resMenosdistante;
    }

    public void accederNoticias(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }

    public void accederScrolling(View view) {
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    public void desconectar() {
        fbAuth.signOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra(CLAVE_EMAIL, email);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void createSimpleDialog(View v) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Infollution")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                desconectar();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        dialog.show();
    }
}
