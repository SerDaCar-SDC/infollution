package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    ImageView ivMapa;
    //EditText etEmail;
    // LAYOUT
    private ImageButton btnSatelite;
    private ImageButton btnTerrain;
    private ImageButton btnNormal;
    private ImageButton btnHybrid;

    // CARDVIEW INFORMATION
    EstacionDataSource persistencia;
    //TextView tvNombreEstacion;
    TextView tvDioxidoAzufre;
    TextView tvMonoxidoCarbono;
    TextView tvLongitudEstacion;
    TextView tvDioxidoNitrogeno;

    ExpandableCardView swipe;
    ArrayList<Estacion> listaEstaciones;

    ImageView ivLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().hide();

        
        ivLugar = findViewById(R.id.ivLugar);
        ivMapa = findViewById(R.id.ivMenuMapa);
        ivMapa.setImageResource(R.drawable.ic_pin_rojo);
        ivMapa.setEnabled(false);

        //tvNombreEstacion = findViewById(R.id.tvNombreEstacion);
        tvDioxidoAzufre = findViewById(R.id.tvDioxidoAzufre);
        tvMonoxidoCarbono = findViewById(R.id.tvMonoxidoCarbono);
        tvLongitudEstacion = findViewById(R.id.tvMonoxidoNitrogeno);
        tvDioxidoNitrogeno = findViewById(R.id.tvDioxidoNitrogeno);


        // LAYOUT
        btnSatelite = findViewById(R.id.btnTipoMapaSatelite);
        btnTerrain = findViewById(R.id.btnTipoMapaTerrain);
        btnNormal = findViewById(R.id.btnTipoMapaNormal);
        btnHybrid = findViewById(R.id.btnTipoMapaHybrid);

        swipe = findViewById(R.id.swipecard);

        // METER GOOGLE MAPS
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        // METER SWYPE CARD
        ExpandableCardView card = findViewById(R.id.swipecard);
        card.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                Toast.makeText(MapActivity.this, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            }
        });

        persistencia = new EstacionDataSource(this);

        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
    }

    private void leerEstacion(int id) {
        Estacion est = persistencia.leerEstacion(id);
        //tvNombreEstacion.setText(est.getNombre());
        //ivLugar.setImageResource();
        //tvDioxidoAzufre.setText("Dirección: " + est.getDireccion());
        //tvMonoxidoCarbono.setText("Latitud: " + String.valueOf(est.getLatitud()));
        //tvLongitudEstacion.setText("Longitud: " + String.valueOf(est.getLongitud()));

        if (id == 4){
            ivLugar.setImageResource(R.drawable.plaza_espania);
        } else if (id == 8){
            ivLugar.setImageResource(R.drawable.escuelas_aguirre);
        } else if (id == 11){
            ivLugar.setImageResource(R.drawable.ramon_cajal);
        }else if (id == 16){
            ivLugar.setImageResource(R.drawable.arturo_soria);
        }else if (id == 17){
            ivLugar.setImageResource(R.drawable.villaverde);
        }else if (id == 18){
            ivLugar.setImageResource(R.drawable.farolillo);
        }else if (id == 24){
            ivLugar.setImageResource(R.drawable.casa_campo);
        }else if (id == 27){
            ivLugar.setImageResource(R.drawable.barajas_pueblo);
        }else if (id == 35){
            ivLugar.setImageResource(R.drawable.plaza_carmen);
        }else if (id == 36){
            ivLugar.setImageResource(R.drawable.moratalaz);
        }else if (id == 38){
            ivLugar.setImageResource(R.drawable.cuatro_caminos);
        }else if (id == 39){
            ivLugar.setImageResource(R.drawable.barrio_pilar);
        }else if (id == 40){
            ivLugar.setImageResource(R.drawable.vallecas);
        }else if (id == 47){
            ivLugar.setImageResource(R.drawable.mendez_alvaro);
        }else if (id == 48){
            ivLugar.setImageResource(R.drawable.castellana);
        }else if (id == 49){
            ivLugar.setImageResource(R.drawable.parque_retiro);
        }else if (id == 50){
            ivLugar.setImageResource(R.drawable.castilla);
        }else if (id == 54){
            ivLugar.setImageResource(R.drawable.ensanche_vallecas);
        }else if (id == 55){
            ivLugar.setImageResource(R.drawable.embajada);
        }else if (id == 56){
            ivLugar.setImageResource(R.drawable.plaza_eliptica);
        }else if (id == 57){
            ivLugar.setImageResource(R.drawable.sanchinarro);
        }else if (id == 58){
            ivLugar.setImageResource(R.drawable.pardo);
        }else if (id == 59){
            ivLugar.setImageResource(R.drawable.juancarlosi);
        }else if (id == 60){
            ivLugar.setImageResource(R.drawable.tres_olivos);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // LOCALIZACION EN TIEMPO REAL
        mMap.setMyLocationEnabled(true);

        // COORDENADAS
        LatLng madrid = new LatLng(40.416775, -3.703789);
        LatLng est4 = new LatLng(40.4238823, -3.7122567);
        LatLng est8 = new LatLng(40.4215533, -3.6823158);
        LatLng est11 = new LatLng(40.4514734, -3.6773491);
        LatLng est16 = new LatLng(40.4400457, -3.6392422);
        LatLng est17 = new LatLng(40.347147, -3.7133167);
        LatLng est18 = new LatLng(40.3947825, -3.7318356);
        LatLng est24 = new LatLng(40.4193577, -3.7473445);
        LatLng est27 = new LatLng(40.4769179, -3.5800258);
        LatLng est35 = new LatLng(40.4192091, -3.7031662);
        LatLng est36 = new LatLng(40.4079517, -3.6453104);
        LatLng est38 = new LatLng(40.4455439, -3.7071303);
        LatLng est39 = new LatLng(40.4782322, -3.7115364);
        LatLng est40 = new LatLng(40.3881478, -3.6515286);
        LatLng est47 = new LatLng(40.3980991, -3.6868138);
        LatLng est48 = new LatLng(40.4398904, -3.6903729);
        LatLng est49 = new LatLng(40.4144444, -3.6824999);
        LatLng est50 = new LatLng(40.4655841, -3.6887449);
        LatLng est54 = new LatLng(40.3730118, -3.6121394);
        LatLng est55 = new LatLng(40.4623628, -3.5805649);
        LatLng est56 = new LatLng(40.3850336, -3.7187679);
        LatLng est57 = new LatLng(40.4942012, -3.6605173);
        LatLng est58 = new LatLng(40.4942012, -3.6605173);
        LatLng est59 = new LatLng(40.4607255, -3.6163407);
        LatLng est60 = new LatLng(40.5005477, -3.6897308);

        // MARCADORES EN COORDENADAS
        // Marker markerMadrid = mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marcador en tu ubicación"));
        mMap.addMarker(new MarkerOptions().position(est4).title("Pza. de España").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est8).title("Escuelas Aguirre").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est11).title("Avda. Ramón y Cajal").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est16).title("Arturo Soria").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est17).title("Villaverde").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est18).title("Farolillo").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est24).title("Casa de Campo").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est27).title("Barajas Pueblo").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est35).title("Pza. del Carmen").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est36).title("Moratalaz").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est38).title("Cuatro Caminos").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est39).title("Barrio del Pilar").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est40).title("Vallecas").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est47).title("Mendez Alvaro").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est48).title("Castellana").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est49).title("Parque del Retiro").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est50).title("Plaza Castilla").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est54).title("Ensanche de Vallecas").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est55).title("Urb. Embajada").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est56).title("Pza. Elíptica").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est57).title("Sanchinarro").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est58).title("El Pardo").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est59).title("Juan Carlos I").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));
        mMap.addMarker(new MarkerOptions().position(est60).title("Tres Olivos").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo4)));

        // POSICIÓN DE CÁMARA
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 14));

        // TIPO DE VISUALIZACIÓN DE MAPA
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        //EVENTO PARA LOS MARCADORES
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                swipe.setTitle(marker.getTitle());
                String tituloMarcador = marker.getTitle();
                int id = persistencia.estacionPornombre(tituloMarcador);

                leerEstacion(id);

                Retrofit retrofit = RetrofitClient.getClient(APIEstaciones.BASE_URL);
                APIEstaciones apiEstaciones = retrofit.create(APIEstaciones.class);
                Call<Datos> call  = apiEstaciones.obtenerDatos();

                call.enqueue(new Callback<Datos>() {
                    @Override
                    public void onResponse(Call<Datos> call, Response<Datos> response) {
                        if(response.isSuccessful()) {
                            Datos d = response.body();
                            List<DatoHorario> listaEstaciones = d.getDatoHorario();

                            for(int i = 0; i < listaEstaciones.size(); i++) {
                                tvDioxidoAzufre.setText(listaEstaciones.get(i).getEstacion());
                                tvLongitudEstacion.setText(listaEstaciones.get(i).getH01());
                                tvMonoxidoCarbono.setText(listaEstaciones.get(i).getH02());
                                tvDioxidoNitrogeno.setText(listaEstaciones.get(i).getH03());
                            }


                        } else {
                            Log.e("ERROR ON RESPONSE", "ERROR: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Datos> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        Log.e("ERROR ON FAILURE", "ERROR: " + t.getMessage());
                    }
                });


                return false; // si ponemos true no se muestra el bocadillo
            }
        });
    }

    // TIPOS DE MAPAS CON BOTONES DE COLORES
    public void onClickTipoSatelite(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        pulsado(true, false, false, false);
    }

    public void onClickTipoTerrain(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        pulsado(false, false, false, true);
    }

    public void onClickTipoNormal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        pulsado(false, false, true, false);
    }

    public void onClickTipoHybrid(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        pulsado(false, true, false, false);
    }

    public void accederNoticias(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    public void accesoFirst(View v) {
        startActivity(new Intent(this, FirstActivity.class));
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }

    private void pulsado(boolean btnS, boolean btnH, boolean btnN, boolean btnT){

        if (btnS){
            btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));

            btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));

        } else if (btnH){
            btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));

            btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));

        } else if (btnN){
            btnNormal.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));

            btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));

        } else if (btnT){
            btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));

            btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
            btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        }

    }

}
