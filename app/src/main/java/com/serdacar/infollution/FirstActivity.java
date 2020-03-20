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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import com.alespero.expandablecardview.ExpandableCardView;
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
import com.serdacar.infollution.model.Estacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FirstActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";
    FirebaseAuth fbAuth;

    TextView tv;
    ImageView ivLogo;
    String email;

    // GRAFICAS
    String so2;
    String co;
    String no;
    String no2;

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

            Toast.makeText(this, "Es necesario que active su GPS", Toast.LENGTH_LONG).show();
            return;
        } else {

            miLocalizacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = miLocalizacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


        miLatitud = loc.getLatitude();
        miLongitud = loc.getLongitude();

        Toast.makeText(this, miLatitud + " " + miLongitud, Toast.LENGTH_LONG).show();

        EstacionDataSource eds = new EstacionDataSource(this);

        ArrayList<Estacion> listaEstaciones = eds.leerEstacionLista();
        final Estacion estacionCerca = encontrarEstMasCerca(listaEstaciones, miLatitud, miLongitud);

        estacionCerca.getLatitud();
        estacionCerca.getLongitud();
        Toast.makeText(this, estacionCerca.getNombre(), Toast.LENGTH_LONG).show();

        /* * * * * * * * * * * * * * * * * * * * * */

        Retrofit retrofit = RetrofitClient.getClient(APIEstaciones.BASE_URL);
        APIEstaciones apiEstaciones = retrofit.create(APIEstaciones.class);
        Call<Datos> call  = apiEstaciones.obtenerDatos();

        /*call.enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if(response.isSuccessful()) {
                    Datos d = response.body();
                    List<DatoHorario> listaEstaciones = d.getDatoHorario();

                    String codigoEstacion;
                    int numeroEstacion;
                    String codigoRecuperado;

                    int horaActual = comprobarHora();

                    for(int i = 0; i < listaEstaciones.size(); i++) {
                        codigoEstacion = listaEstaciones.get(i).getEstacion();

                        if (codigoEstacion.substring(0, 1).equals("0")

                                //estacionSeleccionada
                                && estacionCerca.getCodigoCorto() == Integer.parseInt(listaEstaciones.get(i).getEstacion())) {
                            codigoRecuperado = codigoEstacion.substring(1, 3);
                            numeroEstacion = Integer.parseInt(codigoRecuperado);

                            // estacionSeleccionada
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
                                // estacionSeleccionada
                                && estacionCerca.getCodigoCorto() == Integer.parseInt(listaEstaciones.get(i).getEstacion())){
                            codigoRecuperado = codigoEstacion.substring(2, 3);
                            numeroEstacion = Integer.parseInt(codigoRecuperado);

                            // estacionSeleccionada
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
        });*/

        // TODO: Toast.makeText(this, "DA: " + da + ", LE: " + le + ", MC: " + mc + ", DN: " + dn, Toast.LENGTH_LONG).show();


       /* * * * * * * * * * * * * * * * ** * * * * * * * * * ** * * * * * * * * * **

        AnyChartView anyChartView = findViewById(R.id.acGrafica);

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(new String[] { "7", "7", "7", "7", "100", "100"}));
        circularGauge.fill("#fff").stroke(null).padding(0d, 0d, 0d, 0d).margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d); // 0d
        circularGauge.sweepAngle(270d); // 270d

        Circular xAxis = circularGauge.axis(0).radius(100d).width(1d).fill((Fill) null);
        xAxis.scale().minimum(0d).maximum(100d);
        xAxis.ticks("{ interval: 5 }").minorTicks("{ interval: 5 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(true);
        xAxis.minorTicks().enabled(true);





        circularGauge.label(0d).text("Dioxido de Azufre").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(0d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(100d + "%").offsetX(0d);

        Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(100d);
        bar0.width(17d);
        bar0.fill(new SolidFill("#64b5f6", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(5d);
        bar100.radius(100d);
        bar100.width(17d);
        bar100.fill(new SolidFill("#F5F4F4", 1d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);

        circularGauge.label(1d).text("Monóxido de Carbono").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(1d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(80d + "%").offsetX(0d);
        Bar bar1 = circularGauge.bar(1d);
        bar1.dataIndex(1d);
        bar1.radius(80d);
        bar1.width(17d);
        bar1.fill(new SolidFill("#1976d2", 1d));
        bar1.stroke(null);
        bar1.zIndex(5d);
        Bar bar101 = circularGauge.bar(101d);
        bar101.dataIndex(5d);
        bar101.radius(80d);
        bar101.width(17d);
        bar101.fill(new SolidFill("#F5F4F4", 1d));
        bar101.stroke("1 #e5e4e4");
        bar101.zIndex(4d);

        circularGauge.label(2d).text("Dioxido de Nitrogeno").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(2d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(60d + "%").offsetX(0d);
        Bar bar2 = circularGauge.bar(2d);
        bar2.dataIndex(2d);
        bar2.radius(60d);
        bar2.width(17d);
        bar2.fill(new SolidFill("#ef6c00", 1d));
        bar2.stroke(null);
        bar2.zIndex(5d);
        Bar bar102 = circularGauge.bar(102d);
        bar102.dataIndex(5d);
        bar102.radius(60d);
        bar102.width(17d);
        bar102.fill(new SolidFill("#F5F4F4", 1d));
        bar102.stroke("1 #e5e4e4");
        bar102.zIndex(4d);

        circularGauge.label(3d).text("Dióxido de Carbono").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(3d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(40d + "%").offsetX(0d);
        Bar bar3 = circularGauge.bar(3d);
        bar3.dataIndex(3d);
        bar3.radius(40d);
        bar3.width(17d);
        bar3.fill(new SolidFill("#ffd54f", 1d));
        bar3.stroke(null);
        bar3.zIndex(5d);
        Bar bar103 = circularGauge.bar(103d);
        bar103.dataIndex(5d);
        bar103.radius(40d);
        bar103.width(17d);
        bar103.fill(new SolidFill("#F5F4F4", 1d));
        bar103.stroke("1 #e5e4e4");
        bar103.zIndex(4d);


        circularGauge.margin(50d, 50d, 50d, 50d);
        circularGauge.title().text("MEDIDOR DE CONTAMINACIÓN' +\n" + "    '<br/><span style=\"color:#929292; font-size: 12px;\">(ESTACIONES DE LA COMUNIDAD DE MADRID").useHtml(true);
        circularGauge.title().enabled(true);
        circularGauge.title().hAlign(String.valueOf(HAlign.CENTER));
        circularGauge.title().padding(0d, 0d, 0d, 0d).margin(0d, 0d, 20d, 0d);

        anyChartView.setChart(circularGauge);

        * * * * * * * * * * * * * * * ** * * * * * * * * * ** * * * * * * * * * **/




       // EJEMPLO OFICIAL DE LA PAGINA
        AnyChartView anyChartView = findViewById(R.id.acGrafica);

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(FirstActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        // TODO: Parsear a double
        /*int dioxidoAzufre = Integer.parseInt(so2);
        int monoxidoNitrogeno = Integer.parseInt(co);
        int monoxidoCarbono = Integer.parseInt(no);
        int dioxidoNitrogeno = Integer.parseInt(no2);*/

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Dioxido de Azufre (SO2)", 0.2));
        data.add(new ValueDataEntry("Monoxido de Nitrogeno (CO)", 16.2));
        data.add(new ValueDataEntry("Monoxido de Carbono (NO)", 10.0));
        data.add(new ValueDataEntry("Dioxido de Nitrogeno (NO2", 23.2));

        pie.data(data);

        // pie.palette("#DE3655");
        // pie.hatchFill(Html.);
        // pie.palette(String.valueOf(getColor(R.color.colorAzulOscuro)));
        // pie.hatchFillPalette(getColor(R.color.colorAzulOscuro), getColor(R.color.colorVerde))

        pie.animation(true);

        pie.title("Estacion más cerca " + estacionCerca.getNombre())/*.padding(50d, 50d, 0d, 50d)*/;

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title().text("Leyenda").padding(50d, 0d, 0d, 50d);
        pie.legend().position("center-bottom").itemsLayout(LegendLayout.HORIZONTAL).align(Align.CENTER);

        anyChartView.setChart(pie);










        /*
        pie1 = AnyChart.pie();
        // pie2 = CircularGauge.instantiate()

        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Dioxido de Azufre", Integer.parseInt(da)));
        data.add(new ValueDataEntry("Monoxido de Carbono", Integer.parseInt(mc)));
        data.add(new ValueDataEntry("Dioxido de Nitrogeno", Integer.parseInt(le)));
        data.add(new ValueDataEntry("Dioxido de Nitrogeno", Integer.parseInt(dn)));

        pie1.data(data);
        graficaTarta = (AnyChartView) findViewById(R.id.acGrafica);
        graficaTarta.setChart(pie1);
        */


/*        Retrofit retrofit = RetrofitClient.getClient(APIEstaciones.BASE_URL);
        APIEstaciones apiEstaciones = retrofit.create(APIEstaciones.class);
        Call<Datos> call  = apiEstaciones.obtenerDatos();

        String da;
        String mc;
        String no;

        call.enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if(response.isSuccessful()) {
                    Datos d = response.body();
                    List<DatoHorario> listaEstaciones = d.getDatoHorario();
                    List<DataEntry> data = new ArrayList<>();

                    for(int i = 0; i < listaEstaciones.size(); i++) {

                        listaEstaciones.get(i).getEstacion();
                        listaEstaciones.get(i).getH02();
                        listaEstaciones.get(i).getH03();
                    }

                    pie1.data(data);
                    graficaTarta.setChart(pie1);


                } else {
                    Log.e("ERROR ON RESPONSE", "ERROR: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                Log.e("ERROR ON FAILURE", "ERROR: " + t.getMessage());
            }
        });*/

        /*graficaTarta = (AnyChartView) findViewById(R.id.acGrafica);
        graficaTarta.setChart(pie1);*/



    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * ** * */

    // lat = x \\
    // long = y   \\
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * ** * */

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
