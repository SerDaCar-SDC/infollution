package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient flClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // METER GOOGLE MAPS
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sidney = new LatLng(-34, 151);

        // COORDENADAS
        LatLng llMadrid = new LatLng(40.4165001, -3.7025599);
        LatLng llNorte = new LatLng(40.6590900, -3.7676200);
        LatLng llNoroeste = new LatLng(40.6350600, -4.0048600);
        LatLng llSuroeste = new LatLng(40.4500600, -3.9834400);
        LatLng llSur = new LatLng(40.3223381 , -3.86496);
        LatLng llSureste = new LatLng(40.3007600, -3.4372200);
        LatLng llNordeste = new LatLng(40.4820500, -3.3599600);

        // MARCADORES EN COORDENADAS
        mMap.addMarker(new MarkerOptions().position(llMadrid).title("Marcador en Madrid capital"));
        mMap.addMarker(new MarkerOptions().position(llNorte).title("Marcador en Colmenar Viejo"));
        mMap.addMarker(new MarkerOptions().position(llNoroeste).title("Marcador en Collado Villalba"));
        mMap.addMarker(new MarkerOptions().position(llSuroeste).title("Marcador en Villanueva de la Cañada"));
        mMap.addMarker(new MarkerOptions().position(llSur).title("Marcador en Móstoles"));
        mMap.addMarker(new MarkerOptions().position(llSureste).title("Marcador en Arganda del Rey"));
        mMap.addMarker(new MarkerOptions().position(llNordeste).title("Marcador en Alcalá de Henares"));

        // POSICIÓN DE CÁMARA
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(llMadrid, 10));

        // TIPO DE VISUALIZACIÓN DE MAPA
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("New POSITION")
                        .snippet("Latitud: " + latLng.latitude + " Longitud: " + latLng.longitude)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                );
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


            }
        });
        // mMap.setMapType();

    }
}
