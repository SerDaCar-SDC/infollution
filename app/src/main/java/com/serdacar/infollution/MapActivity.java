package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private GoogleMap mMap;
    //private FusedLocationProviderClient flClient;
    private Location miLoc;
    private LocationManager locManager;

    ImageView ivMapa;
    EditText etEmail;
    // LAYOUT
    private Button btnSatelite;
    private Button btnTerrain;
    private Button btnNormal;
    private Button btnHybrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().hide();

        ivMapa = findViewById(R.id.ivMenuMapa);
        ivMapa.setEnabled(false);

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
        LatLng llSur = new LatLng(40.3223381 , -3.86496);
        LatLng llSureste = new LatLng(40.3007600, -3.4372200);
        LatLng llNordeste = new LatLng(40.4820500, -3.3599600);
        LatLng uem = new LatLng(40.5351, -3.6165);

        // MARCADORES EN COORDENADAS
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marcador en tu ubicación"));
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
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 20));
        // TIPO DE VISUALIZACIÓN DE MAPA 
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // VISUALIZACIÓN DE BOTONES (colores)
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));
        btnHybrid.setTextColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnNormal.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnSatelite.setTextColor(getResources().getColor(R.color.colorAzulOscuro));

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

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
        //mMap.getUiSettings().setZoomControlsEnabled(true);
        //mMap.getUiSettings().setCompassEnabled(true);
        // mMap.setMapType();
    }

    // TIPOS DE MAPAS CON BOTONES DE COLORES
    public void onClickTipoSatelite(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));
        btnSatelite.setTextColor(getResources().getColor(R.color.colorBlancoNuestro));

        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnNormal.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnHybrid.setTextColor(getResources().getColor(R.color.colorAzulOscuro));

    }

    public void onClickTipoTerrain(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));
        btnTerrain.setTextColor(getResources().getColor(R.color.colorBlancoNuestro));

        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnSatelite.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnNormal.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnHybrid.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
    }

    public void onClickTipoNormal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));
        btnNormal.setTextColor(getResources().getColor(R.color.colorBlancoNuestro));

        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnSatelite.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnHybrid.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
    }

    public void onClickTipoHybrid(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        btnHybrid.setBackgroundColor(getResources().getColor(R.color.colorAzulOscuro));
        btnHybrid.setTextColor(getResources().getColor(R.color.colorBlancoNuestro));

        btnTerrain.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnTerrain.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnNormal.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnNormal.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
        btnSatelite.setBackgroundColor(getResources().getColor(R.color.colorBlancoNuestro));
        btnSatelite.setTextColor(getResources().getColor(R.color.colorAzulOscuro));
    }

    public void accesoFirst(View v) {
        startActivity(new Intent(this, FirstActivity.class));
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
