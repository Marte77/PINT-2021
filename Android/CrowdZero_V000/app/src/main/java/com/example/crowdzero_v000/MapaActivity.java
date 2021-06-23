package com.example.crowdzero_v000;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MapaActivity extends NavDrawerActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private float zoom = 15.0f;
    protected LocationManager locationManager = null;
    private GoogleMap mapa = null;
    private ArrayList<MarkerOptions> markerOptionsArray = new ArrayList<>();
    private ArrayList<Marker> markerArray = new ArrayList<>();
    private ArrayList<CircleOptions> circleOptionsArray = new ArrayList<>();
    //private ArrayList<Bitmap> bitmapImagensInstsArrayList = new ArrayList<>();
    private boolean isLocationEnabled = false, isUserMarkerSet = false;
    private Marker userMarker = null;
    private MarkerOptions userMarkerOptions = null;
    private LatLng latLngInfoInstituicao= null;
    private TreeMap<String,Integer> parIdInst_NomeInst = new TreeMap<>();
    private boolean gpsLigado =false,
            veioDoInfoInstituicao = false;//este serve para verificar se veio
                                            // do info instituicao, se sim, mete a camara nessa isntituicao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) || (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)) {
            int LOCATION_PERMISSION_COARSE_REQUEST = 100;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_COARSE_REQUEST);
        } else {
            isLocationEnabled = true;
        }

        if(savedInstanceState== null){
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                if(extras.get("coords") !=null) {
                    veioDoInfoInstituicao=true;
                    Bundle e = (Bundle) extras.get("coords");
                    latLngInfoInstituicao = new LatLng(e.getDouble("lat"),e.getDouble("lng"));
                }
            }
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //colocar altura do linearlayout certa para nao sobre por a toolbar
        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutMapa));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        if (isLocationEnabled) {
            atualizarLocalizacaoEMarcador();
        }


        try{
            gpsLigado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception ignored){}
        if(!gpsLigado){
            //seria fixe fazer isto mas para pedir as permissoes, so qe nao estou a ver como fazer
            AlertDialog a = new AlertDialog.Builder(MapaActivity.this).
                    setMessage("Tem de ligar a localização para fazer reports")
                    .setPositiveButton("Ligar localização", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MapaActivity.this.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),123);
                        }
                    })
                    .setNegativeButton("Cancelar",null).create();
            a.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {//requestcode da activity que abre as settings da localizacao
            //fazer refresh da activity
            finish();
            startActivity(getIntent());
        }
    }

    private void atualizarLocalizacaoEMarcador() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatLng coordsUtil = new LatLng(location.getLatitude(), location.getLongitude());
                String locUtilS = String.valueOf(coordsUtil.latitude).substring(0, 5) + ", " + String.valueOf(coordsUtil.longitude).substring(0, 5);
                userMarkerOptions = marcadorComIcone(coordsUtil, "Localização Utilizador", locUtilS, true);
                if (mapa != null && !isUserMarkerSet) {
                    userMarker = mapa.addMarker(userMarkerOptions);
                    if(!veioDoInfoInstituicao)
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(coordsUtil,zoom));
                    else{
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngInfoInstituicao,zoom));
                    }
                    isUserMarkerSet = true;
                } else if (mapa != null) {
                    userMarker.setPosition(coordsUtil);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int x =0 ;x<permissions.length;x++){
            if(permissions[x].equals(Manifest.permission.ACCESS_COARSE_LOCATION) || permissions[x].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                if(grantResults[x] == PERMISSION_GRANTED){
                    isLocationEnabled = true;
                }
            }
        }
        if(isLocationEnabled){
            atualizarLocalizacaoEMarcador();
        }else{
            AlertDialog a = new AlertDialog.Builder(MapaActivity.this)
                    .setMessage("Tem dar permissões para aceder a localização")
                    .setPositiveButton("", null)
                    .setNegativeButton("",null).create();
            a.show();
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        inicializarGoogleMapa(googleMap);
        listarMarcadores();
    }

    private void listarMarcadores() {
        FuncoesApi.FuncoesLocais.getTodosLocais(getApplicationContext(), new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                if(jsonObject.getInt("status") == 500)
                    return;
                JSONArray jsonArray= jsonObject.getJSONArray("Locais");
                for(int i = 0; i<jsonArray.length();i++){
                    final int idLocal = jsonArray.getJSONObject(i).getInt("ID_Local");
                    final String nome = jsonArray.getJSONObject(i).getString("Nome");
                    final String descricao = jsonArray.getJSONObject(i).getString("Descricao");
                    final double lon = jsonArray.getJSONObject(i).getDouble("Longitude");
                    final double lat = jsonArray.getJSONObject(i).getDouble("Latitude");

                    FuncoesApi.FuncoesReports.getDensidadeMediaPorLocal(getApplicationContext(), idLocal, "dd", 1, new FuncoesApi.volleycallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            int numeroReports = jsonObject.getInt("numeroReports");

                            if(numeroReports == 0)
                                adicionarMarcador(new LatLng(lat,lon),nome,descricao,numeroReports,"Sem populacao",idLocal);
                            else {
                                int densidadeMedia = jsonObject.getInt("media");
                                String populacao = null;
                                if(densidadeMedia == 3)
                                    populacao = "Muito";
                                else if(densidadeMedia == 2)
                                    populacao = "Medio";
                                else if(densidadeMedia == 1)
                                    populacao = "Pouco";
                                adicionarMarcador(new LatLng(lat, lon), nome, descricao, numeroReports, populacao, idLocal);
                            }
                        }

                        @Override
                        public void onError(JSONObject jsonObjectErr) throws JSONException {
                            Log.i("pedido","Erro Mapa Locais" + jsonObjectErr);
                            adicionarMarcador(new LatLng(lat, lon), nome, descricao, 0, "Sem populacao",idLocal);
                        }
                    });

                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
            }
        });
    }

    private void inicializarGoogleMapa(GoogleMap googleMap) {
        this.mapa = googleMap;
        UiSettings uiSettingsMapa = googleMap.getUiSettings();
        uiSettingsMapa.setCompassEnabled(true);

        //Fazer click longo para abrir o menu de infos do local
        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double thresholdLatitude = Math.pow(5.705,-4);
                double thresholdLongitude = Math.pow(5.705,-4);
                for(Marker m : markerArray){
                    if(Math.abs(m.getPosition().latitude - latLng.latitude) < thresholdLatitude && Math.abs(m.getPosition().longitude - latLng.longitude) < thresholdLongitude){
                        int idLocal = -1;
                        try{
                            idLocal = parIdInst_NomeInst.get(m.getTitle());
                        }catch (NullPointerException e){
                            e.printStackTrace();
                            return;
                        }
                        int posMarker = markerArray.indexOf(m);
                        FragmentModalBottomSheet fmBS = new FragmentModalBottomSheet(m.getTitle(),idLocal, getApplicationContext()/*, bitmapImagensInstsArrayList.get(posMarker) */);

                        fmBS.show(getSupportFragmentManager(),m.getTitle());
                        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(50);
                    }
                }
            }
        });

        //tipo do mapa e zoom default
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.6574533,-7.9131884),zoom));

        try {
            googleMap.setMapStyle( //adicionar estilo ao mapa para tirar os marcadores
                    MapStyleOptions.loadRawResourceStyle(
                            getApplicationContext(), R.raw.map_style));

        }catch(Resources.NotFoundException e){
            e.printStackTrace();
        }
    }



    void adicionarMarcador(LatLng latLng, String nomeLocal, String descricaoLocal, int numeroReports, String populacao, int idLocal){


        parIdInst_NomeInst.put(nomeLocal,idLocal);

        MarkerOptions marcadorNovo = marcadorComIcone(latLng,nomeLocal,"Número de reports: " + numeroReports);
        Marker marker= mapa.addMarker(marcadorNovo);
        markerArray.add(marker);
        markerOptionsArray.add(marcadorNovo);
        CircleOptions circuloMarcador = new CircleOptions().center(latLng);
        circleOptionsArray.add(circuloMarcador);
        circuloMarcador.strokeWidth(2.0f);
        String corTransparencia50 ="#7F"; //adicionar os 6 digitos respetivos a cor dependendo do numero de reports

        numeroReports = numeroReports * 30; //amplificar o circulo

        if(populacao == null)
            populacao = "Sem populacao";
        circuloMarcador.radius(numeroReports*0.5f);
        switch (populacao) {
            case "Muito":
                marcadorNovo.snippet("Extremamente populado" + marcadorNovo.getSnippet());
                corTransparencia50 += "e81313"; // vermelho

                circuloMarcador.fillColor(Color.parseColor(corTransparencia50));
                mapa.addCircle(circuloMarcador);
                break;
            case "Medio":
                marcadorNovo.snippet("Muito populado" + marcadorNovo.getSnippet());
                corTransparencia50 += "e4e813"; //amarelo

                circuloMarcador.fillColor(Color.parseColor(corTransparencia50));
                mapa.addCircle(circuloMarcador);
                break;
            case "Pouco":
                marcadorNovo.snippet("Pouco populado" + marcadorNovo.getSnippet());
                corTransparencia50 += "13e84b"; //verde

                circuloMarcador.fillColor(Color.parseColor(corTransparencia50));
                mapa.addCircle(circuloMarcador);
                break;
            case "Sem populacao":
                marcadorNovo.snippet("Sem população" + marcadorNovo.getSnippet());
                break;
        }

    }

    MarkerOptions marcadorComIcone(LatLng latLng, String nomeLocal,String descricaoLocal){
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).snippet(descricaoLocal).title(nomeLocal);
        Bitmap bitmap = getBitmapFromVectorDrawable(getApplicationContext(),R.drawable.ic_room_black_24dp);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(bitmapDescriptor);
        return markerOptions;
    }
    MarkerOptions marcadorComIcone(LatLng latLng, String nomeLocal,String descricaoLocal, boolean isUtilizador){
        //apenas mete o marcador a vermelho
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).snippet(descricaoLocal).title(nomeLocal);
        Bitmap bitmap = getBitmapFromVectorDrawable(getApplicationContext(),R.drawable.ic_room_black_24dp);
        if(isUtilizador){
            int [] allPixels = new int[bitmap.getHeight()*bitmap.getWidth()];
            bitmap.getPixels(allPixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
            for(int i = 0;i< allPixels.length;i++){
                if(allPixels[i] == Color.BLACK){
                    allPixels[i] = Color.RED;
                }
            }
            bitmap.setPixels(allPixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        }

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(bitmapDescriptor);
        return markerOptions;
    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        //retirada deste post https://stackoverflow.com/a/38244327/10676498
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),zoom));
        return false;
    }
}
