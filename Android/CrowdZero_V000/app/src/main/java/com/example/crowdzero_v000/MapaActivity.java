package com.example.crowdzero_v000;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;

public class MapaActivity extends NavDrawerActivity implements OnMapReadyCallback {

    private int LOCATION_PERMISSION_COARSE_REQUEST = 100;
    private int LOCATION_PERMISSION_FINE_REQUEST = 101;
    private GoogleMap mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)||(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_COARSE_REQUEST);
        }

        //colocar altura do linearlayout certa para nao sobre por a toolbar
        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout)findViewById(R.id.linearLayoutMapa));
        linearLayout.setPadding(linearLayout.getLeft(),alturatb,linearLayout.getRight(),linearLayout.getBottom());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;
        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.65810312603541,-7.9150425642728806),14.0f));
        //TODO:obter lista de instituicoes
        //camara de viseu
        mapa.addMarker(new MarkerOptions().position(new LatLng(40.658103,-7.9150271)).snippet("Camara de Viseu"));
        //Palácio do Gelo
        mapa.addMarker(new MarkerOptions().position(new LatLng(40.6436356,-7.9133976)).snippet("Palácio do Gelo"));

        mapa.addCircle(new CircleOptions().center(new LatLng(40.6436356,-7.9133976))
                                                .radius(200.0f)
                                                .fillColor(Color.parseColor("#7Ffa0000"))
                                                .visible(true));
    }
}