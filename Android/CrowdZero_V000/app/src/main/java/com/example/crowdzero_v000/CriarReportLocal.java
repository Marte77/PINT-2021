package com.example.crowdzero_v000;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CriarReportLocal extends NavDrawerActivity {
    String nome, descricao;
    int idlocal;


    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    ArrayList<String> niveisDensidadeArray = new ArrayList<>();

    LatLng coordsLocal;
    boolean isLocationEnabled = false;
    final double raio = 200; // raio da circunferencia dentro da qual se pode dar report
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_report_instituicao);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");
        idlocal = getIntent().getExtras().getInt("idlocal");
        tb.setTitle(nome);

        autoCompleteTextView = findViewById(R.id.autoCompleteNivelReport);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_niveis_densidade, niveisDensidadeArray);
        autoCompleteTextView.setAdapter(arrayAdapter);
        textInputLayout = findViewById(R.id.menuNivelDensidade);
        textInputEditText = findViewById(R.id.inputDescricaoNovoReport);

        //os inputs comecam desativados pois demora um bocado a obter a localizacao da pessoa relativamente ao local
        desativarOuAtivarInputs(false);

        //region verificar coordenadas para fazer report
        verificarCoordsParaFazerReport();


        //endregion


        niveisDensidadeArray.add("Pouco Populado");
        niveisDensidadeArray.add("Muito Populado");
        niveisDensidadeArray.add("Extremamente Populado");


        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        if (f.getVerificacao()) {
            // TODO: 23/06/2021 fazer cenas relativas ao report indoor
        }


        MaterialButton button = findViewById(R.id.submeterNovoReport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23/06/2021 fazer report indoor
                if (autoCompleteTextView.getText().toString().isEmpty() || textInputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Tem de preencher os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tipoPessoa = f.getTipoPessoa();
                if (tipoPessoa.equals(FuncoesSharedPreferences.outrosUtil)) {
                    try {
                        FuncoesApi.FuncoesReports.criarNovoReportOutdoorOutrosUtil(getApplicationContext(),
                                textInputEditText.getText().toString(),
                                niveisDensidadeArray.indexOf(autoCompleteTextView.getText().toString()) + 1,
                                idlocal,
                                f.getIDUtilizador(),
                                new FuncoesApi.volleycallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                                        Log.i("pedido", jsonObject.toString());
                                        Toast.makeText(getApplicationContext(), "Report criado com sucesso", Toast.LENGTH_LONG).show();
                                        fecharActivity();
                                    }

                                    @Override
                                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                                        Log.i("pedido", "Erro a criar report out oturo util: " + jsonObjectErr.toString());
                                        Toast.makeText(getApplicationContext(), "Erro a criar report!", Toast.LENGTH_LONG).show();
                                        fecharActivity();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else { //report outdoor util instituicao
                    try {
                        FuncoesApi.FuncoesReports.criarNovoReportOutdoorUtilInst(getApplicationContext(),
                                textInputEditText.getText().toString(),
                                niveisDensidadeArray.indexOf(autoCompleteTextView.getText().toString()) + 1,
                                idlocal,
                                f.getIDUtilizador(),
                                new FuncoesApi.volleycallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                                        Log.i("pedido", jsonObject.toString());
                                        Toast.makeText(getApplicationContext(), "Report criado com sucesso", Toast.LENGTH_LONG).show();
                                        fecharActivity();
                                    }

                                    @Override
                                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                                        Log.i("pedido", "Erro a criar report out utilinst: " + jsonObjectErr.toString());
                                        Toast.makeText(getApplicationContext(), "Erro a criar report!", Toast.LENGTH_LONG).show();
                                        fecharActivity();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void fecharActivity() {
        getIntent().putExtra("dondeveio","criarreport");
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int x = 0; x < permissions.length; x++) {
            if (permissions[x].equals(Manifest.permission.ACCESS_COARSE_LOCATION) || permissions[x].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[x] == PERMISSION_GRANTED) {
                    isLocationEnabled = true;
                }
            }
        }
        if (isLocationEnabled) {
            verificarCoordsParaFazerReport();
        } else {
            desativarOuAtivarInputs(false);
        }
    }

    void verificarCoordsParaFazerReport() {

        if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) || (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)) {
            int LOCATION_PERMISSION_COARSE_REQUEST = 100;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_COARSE_REQUEST);
        } else {
            isLocationEnabled = true;
        }

        if (!isLocationEnabled) {//impedir utilizador de fazer reports
            desativarOuAtivarInputs(false);
            return;
        }

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        FuncoesApi.volleycallback VCB = new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                Log.i("testar",jsonObject.toString());
                coordsLocal = new LatLng(jsonObject.getJSONObject("Local").getDouble("Latitude"), jsonObject.getJSONObject("Local").getDouble("Longitude"));

                if (ActivityCompat.checkSelfPermission(CriarReportLocal.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(CriarReportLocal.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    desativarOuAtivarInputs(false);
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2.0f, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        verificarSeEstaNoDentroDoRaio(location);
                    }
                });
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("testar",jsonObjectErr.toString());
            }
        };
        FuncoesApi.FuncoesLocais.getLocalPorId(getApplicationContext(),idlocal,VCB);

    }

    boolean jaMandouToast = false;
    private void verificarSeEstaNoDentroDoRaio(final Location locationUtil) {
        LatLng coordsUtil = new LatLng(locationUtil.getLatitude(), locationUtil.getLongitude());
        float[] resultado = new float[1];
        Location.distanceBetween(coordsLocal.latitude,coordsLocal.longitude,coordsUtil.latitude,coordsUtil.longitude,resultado);
        float distancia = resultado[0];
        Log.i("testar",distancia+"");
        if(distancia<=raio){
            //utilizador está dentro do círculo
            //Toast.makeText(getApplicationContext(),"Está dentro do raio para fazer report neste local",Toast.LENGTH_LONG).show();
            desativarOuAtivarInputs(true);
        }else{
            if(!jaMandouToast) {
                Toast.makeText(getApplicationContext(), "Não pode fazer report pois está demasiado longe do local", Toast.LENGTH_LONG).show();
                jaMandouToast = !jaMandouToast;
            }desativarOuAtivarInputs(false);
        }
    }

    private void desativarOuAtivarInputs(boolean ativa) {
        textInputEditText.setEnabled(ativa);
        autoCompleteTextView.setEnabled(ativa);
        if(!ativa) {
            textInputLayout.setAlpha(0.4f);
            findViewById(R.id.descricaoNovoReport).setAlpha(0.4f);
        }else{
            textInputLayout.setAlpha(1.0f);
            findViewById(R.id.descricaoNovoReport).setAlpha(1.0f);
        }
    }
}