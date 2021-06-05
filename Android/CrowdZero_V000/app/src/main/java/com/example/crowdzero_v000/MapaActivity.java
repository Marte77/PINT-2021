package com.example.crowdzero_v000;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.crowdzero_v000.fragmentos.FragmentModalBottomSheet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;


import java.util.ArrayList;

public class MapaActivity extends NavDrawerActivity implements OnMapReadyCallback {

    private int LOCATION_PERMISSION_COARSE_REQUEST = 100;
    private int LOCATION_PERMISSION_FINE_REQUEST = 101;
    private GoogleMap mapa;
    private UiSettings uiSettingsMapa;
    private ArrayList<MarkerOptions> markerOptionsArray = new ArrayList<>();
    private ArrayList<Marker> markerArray = new ArrayList<>();
    private ArrayList<CircleOptions> circleOptionsArray = new ArrayList<>();


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
        inicializarGoogleMapa(googleMap);

        //TODO:obter lista de instituicoes
        /*
        //camara de viseu
        mapa.addMarker(marcadorComIcone(new LatLng(40.658103,-7.9150271),"Camara de Viseu","Camara de Viseu"));
        //mapa.addMarker(new MarkerOptions().position(new LatLng(40.658103,-7.9150271)).title("Camara de Viseu").snippet("Camara de Viseu"));
        //Palácio do Gelo
        mapa.addMarker(marcadorComIcone(new LatLng(40.6436356,-7.9133976),"Palácio do Gelo","Palácio do Gelo"));
        mapa.addCircle(new CircleOptions().center(new LatLng(40.6436356,-7.9133976))
                                                .radius(200.0f)
                                                .fillColor(Color.parseColor("#7Ffa0000"))
                                                .visible(true));*/

        adicionarMarcador(new LatLng(40.6577125,-7.9141467),"Camara de Viseu","Camara de Viseu",101,"Muito");
        adicionarMarcador(new LatLng(40.6435252,-7.9112907),"Palacio do Gelo","Palacio do Gelo",62,"Medio");
        adicionarMarcador(new LatLng(40.6559245,-7.9159877),"Parque da Cidade","Parque da Cidade",30,"Pouco");
        adicionarMarcador(new LatLng(40.6510224,-7.9495194),"Café da ti joana","Café da ti joana",0,"Sem populacao");
        adicionarMarcador(new LatLng(40.6629263,-7.9110049),"Gaming Swag","Gaming Swag",150,"Pouco");
    }

    private void inicializarGoogleMapa(GoogleMap googleMap) {
        this.mapa = googleMap;
        this.uiSettingsMapa = googleMap.getUiSettings();
        uiSettingsMapa.setCompassEnabled(true);

        /*TODO: Se nao funcionar para meter o long click fazer este
        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentModalBottomSheet fmBS = new FragmentModalBottomSheet(marker.getTitle());
                fmBS.show(getSupportFragmentManager(),marker.getTitle());

                return false;
            }
        });*/

        //Fazer click longo para abrir o menu de infos do local
        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double tresholdLatitude = Math.pow(5.705,-4);
                double tresholdLongitude = Math.pow(5.705,-4);
                for(Marker m : markerArray){

                    if(Math.abs(m.getPosition().latitude - latLng.latitude) < tresholdLatitude && Math.abs(m.getPosition().longitude - latLng.longitude) < tresholdLongitude){
                        FragmentModalBottomSheet fmBS = new FragmentModalBottomSheet(m.getTitle());
                        fmBS.show(getSupportFragmentManager(),m.getTitle());
                    }
                }
            }
        });

        //tipo do mapa e zoom default
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.6574533,-7.9131884),15.0f));
        try {
            boolean success = googleMap.setMapStyle( //adicionar estilo ao mapa para tirar os marcadores
                    MapStyleOptions.loadRawResourceStyle(
                            getApplicationContext(), R.raw.map_style));

        }catch(Resources.NotFoundException ignored){

        }
    }

    void adicionarMarcador(LatLng latLng, String nomeLocal, String descricaoLocal, int numeroReports, String populacao){
        MarkerOptions marcadorNovo = marcadorComIcone(latLng,nomeLocal,"Número de reports: " + numeroReports);
        Marker marker= mapa.addMarker(marcadorNovo);
        markerArray.add(marker);
        markerOptionsArray.add(marcadorNovo);
        CircleOptions circuloMarcador = new CircleOptions().center(latLng);
        circleOptionsArray.add(circuloMarcador);
        circuloMarcador.strokeWidth(2.0f);
        String corTransparencia50 ="#7F"; //adicionar os 6 digitos respetivos a cor dependendo do numero de reports


        circuloMarcador.radius(numeroReports*0.5f);
        if(populacao.equals("Muito")){
            marcadorNovo.snippet("Extremamente populado"+marcadorNovo.getSnippet());
            corTransparencia50 += "e81313"; // vermelho
            circuloMarcador.fillColor(Color.parseColor(corTransparencia50));
            mapa.addCircle(circuloMarcador);
        }else if(populacao.equals("Medio")){
            marcadorNovo.snippet("Muito populado"+marcadorNovo.getSnippet());
            corTransparencia50 += "e4e813"; //amarelo
            circuloMarcador.fillColor(Color.parseColor(corTransparencia50));
            mapa.addCircle(circuloMarcador);
        }else if(populacao.equals("Pouco")){
            marcadorNovo.snippet("Pouco populado"+marcadorNovo.getSnippet());
            corTransparencia50 += "13e84b"; //verde
            circuloMarcador.fillColor(Color.parseColor(corTransparencia50));
            mapa.addCircle(circuloMarcador);
        }else if(populacao.equals("Sem populacao")){
            marcadorNovo.snippet("Sem população"+marcadorNovo.getSnippet());

        }

    }

    MarkerOptions marcadorComIcone(LatLng latLng, String nomeLocal,String descricaoLocal){
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).snippet(descricaoLocal).title(nomeLocal);
        Bitmap bitmap = getBitmapFromVectorDrawable(getApplicationContext(),R.drawable.ic_room_black_24dp);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(bitmapDescriptor);

        return markerOptions;
    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        //retirada deste post https://stackoverflow.com/a/38244327/10676498
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }




}
