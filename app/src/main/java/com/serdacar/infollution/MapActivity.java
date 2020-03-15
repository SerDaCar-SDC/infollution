package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private GoogleMap mMap;
    //private FusedLocationProviderClient flClient;
    private Location miLoc;
    private LocationManager locManager;

    ImageView ivMapa;
    EditText etEmail;
    // LAYOUT
    private ImageButton btnSatelite;
    private ImageButton btnTerrain;
    private ImageButton btnNormal;
    private ImageButton btnHybrid;

    EstacionDataSource persistencia;
    TextView tvNombreEstacion;
    TextView tvDireccionEstacion;
    TextView tvLatitudEstacion;
    TextView tvLongitudEstacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().hide();

        ivMapa = findViewById(R.id.ivMenuMapa);
        ivMapa.setImageResource(R.drawable.ic_pin_rojo);
        ivMapa.setEnabled(false);

        tvNombreEstacion = findViewById(R.id.tvNombreEstacion);
        tvDireccionEstacion = findViewById(R.id.tvDireccionEstacion);
        tvLatitudEstacion = findViewById(R.id.tvLatitudEstacion);
        tvLongitudEstacion = findViewById(R.id.tvLongitudEstacion);

        //etEmail = findViewById(R.id.etEmailLogin);
        //String emailRegister = getIntent().getStringExtra(RegisterActivity.CLAVE_EMAIL);
        //etEmail.setText(emailRegister);

        // LAYOUT
        btnSatelite = findViewById(R.id.btnTipoMapaSatelite);
        btnTerrain = findViewById(R.id.btnTipoMapaTerrain);
        btnNormal = findViewById(R.id.btnTipoMapaNormal);
        btnHybrid = findViewById(R.id.btnTipoMapaHybrid);

        //flClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {
            Log.i("LOC", "con permisos");
            /*flClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.i("LOC", "onSuccess de location");
                    if (location != null) {
                        miLoc = location;
                    }
                }
            });*/

            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            miLoc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

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

        //-------Métodos para consultar la baes de datos--------//
        persistencia = new EstacionDataSource(this);

        leerEstacion();
    }

    private void leerEstacion() {
        Estacion est = persistencia.leerEstacion(4);
        tvNombreEstacion.setText(est.getNombre());
        tvDireccionEstacion.setText("Dirección: " + est.getDireccion());
        tvLatitudEstacion.setText("Latitud: " + String.valueOf(est.getLatitud()));
        tvLongitudEstacion.setText("Longitud: " + String.valueOf(est.getLongitud()));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ubicacion = new LatLng(miLoc.getLatitude(), miLoc.getAltitude());

        /*if (miLoc == null) {
            uem = new LatLng(40.5351, -3.6165);
        } else {
            uem = new LatLng(miLoc.getLatitude(), miLoc.getLongitude());
        }*/
        // COORDENADAS

        LatLng llMadrid = new LatLng(40.4165001, -3.7025599);
        LatLng llNorte = new LatLng(40.6590900, -3.7676200);
        LatLng llNoroeste = new LatLng(40.6350600, -4.0048600);
        LatLng llSuroeste = new LatLng(40.4500600, -3.9834400);
        LatLng llSur = new LatLng(40.3223381, -3.86496);
        LatLng llSureste = new LatLng(40.3007600, -3.4372200);
        LatLng llNordeste = new LatLng(40.4820500, -3.3599600);
        LatLng uem = new LatLng(40.5351, -3.6165);

        // MARCADORES EN COORDENADAS
        // Marker markerMadrid = mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marcador en tu ubicación"));
        mMap.addMarker(new MarkerOptions().position(llMadrid).title("Marcador en Madrid capital").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(llNorte).title("Marcador en Colmenar Viejo").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(llNoroeste).title("Marcador en Collado Villalba").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(llSuroeste).title("Marcador en Villanueva de la Cañada").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(llSur).title("Marcador en Móstoles").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(llSureste).title("Marcador en Arganda del Rey").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(llNordeste).title("Marcador en Alcalá de Henares").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        mMap.addMarker(new MarkerOptions().position(uem).title("Marcador en Universidad Europea").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3)));
        // POSICIÓN DE CÁMARA
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uem, 20));

        // TIPO DE VISUALIZACIÓN DE MAPA
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // pulsado(false, true, false, false);

        // VISUALIZACIÓN DE BOTONES (colores)
        /*
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));
        btnHybrid.setTextColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        //btnNormal.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnSatelite.setTextColor(getResources().getColor(R.color.colorAzulOscuro)); */

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        // EVENTO PARA EL MAPA
        /*
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("New POSITION")
                        .snippet("Latitud: " + latLng.latitude + " Longitud: " + latLng.longitude)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo3))
                );
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
        */

        //mMap.getUiSettings().setZoomControlsEnabled(true);
        //mMap.getUiSettings().setCompassEnabled(true);
        // mMap.setMapType();

        //EVENTO PARA LOS MARCADORES
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(MapActivity.this, "Has hecho clic en marker: " + marker.getTitle(), Toast.LENGTH_SHORT).show();

                /*
                Intent i = new Intent(MapActivity.this, MapActivity.class);
                startActivity(i);
                finish();
                */

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

    public void accesoFirst(View v) {
        startActivity(new Intent(this, FirstActivity.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
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
