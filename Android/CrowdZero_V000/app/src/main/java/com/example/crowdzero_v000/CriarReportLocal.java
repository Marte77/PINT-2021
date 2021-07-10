package com.example.crowdzero_v000;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CriarReportLocal extends NavDrawerActivity {
    String nome, descricao;
    int idlocal;


    AutoCompleteTextView inputNivelDensidade, inputLocalIndoor;
    TextInputLayout menuLayoutNivelDensidade,menuLayoutLocalIndoor;
    TextInputEditText inputDescricaoReport;
    CheckBox isReportIndoor;
    ArrayList<String> niveisDensidadeArray = new ArrayList<>();
    ArrayList<String> locaisIndoorArray = new ArrayList<>();
    ArrayList<Pair<Integer, String>> listaParesIDNomeLocalIndoor = new ArrayList<>();
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


        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        inputNivelDensidade = findViewById(R.id.autoCompleteNivelReport);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_niveis_densidade, niveisDensidadeArray);
        inputNivelDensidade.setAdapter(arrayAdapter);
        menuLayoutNivelDensidade = findViewById(R.id.menuNivelDensidade);
        inputDescricaoReport = findViewById(R.id.inputDescricaoNovoReport);
        isReportIndoor = findViewById(R.id.checkBoxIsReportIndoor);
        menuLayoutLocalIndoor = findViewById(R.id.menuLocaisIndoor);
        inputLocalIndoor = findViewById(R.id.autoCompleteLocaisIndoor);
        inputLocalIndoor.setEnabled(false);
        inputLocalIndoor.setAlpha(0.4f);

        isReportIndoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inputLocalIndoor.setEnabled(isChecked);
                if (isChecked) {
                    menuLayoutLocalIndoor.setAlpha(1f);
                } else menuLayoutLocalIndoor.setAlpha(0.4f);
            }
        });

        //os inputs comecam desativados pois demora um bocado a obter a localizacao da pessoa relativamente ao local
        desativarOuAtivarInputs(false);
        int i = 0;
        //region verificar coordenadas para fazer report e obter locais indoor
        verificarCoordsParaFazerReport();
        if(f.getTipoPessoa().equals("Outros_Util")){
            isReportIndoor.setAlpha(0);
            isReportIndoor.setEnabled(false);
            inputLocalIndoor.setEnabled(false);
            inputLocalIndoor.setAlpha(0);
            menuLayoutLocalIndoor.setAlpha(0);
            menuLayoutLocalIndoor.setEnabled(false);
        }else {
            if (f.getVerificacao()) {
                //obter lista de locais
                obterLocaisIndoor();
            } else {
                isReportIndoor.setEnabled(false);
                isReportIndoor.setAlpha(0.4f);
            }
        }

        //endregion


        niveisDensidadeArray.add("Pouco Populado");
        niveisDensidadeArray.add("Muito Populado");
        niveisDensidadeArray.add("Extremamente Populado");


        //region botao report listener
        MaterialButton button = findViewById(R.id.submeterNovoReport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputNivelDensidade.getText().toString().isEmpty() || inputDescricaoReport.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Tem de preencher os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tipoPessoa = f.getTipoPessoa();
                if (tipoPessoa.equals(FuncoesSharedPreferences.outrosUtil)) {
                    try {
                        FuncoesApi.FuncoesReports.criarNovoReportOutdoorOutrosUtil(getApplicationContext(),
                                inputDescricaoReport.getText().toString(),
                                niveisDensidadeArray.indexOf(inputNivelDensidade.getText().toString()) + 1,
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
                    if(isReportIndoor.isChecked() && f.getVerificacao()){
                        try {
                            int localIndoorID = -1;
                            for(int i = 0; i< listaParesIDNomeLocalIndoor.size();i++ ){
                                Pair<Integer,String> p = listaParesIDNomeLocalIndoor.get(i);
                                if(p.second.equals(inputLocalIndoor.getText().toString())){
                                    localIndoorID = p.first;
                                }
                            }
                            if(localIndoorID == -1){
                                Toast.makeText(getApplicationContext(),"Erro a criar report", Toast.LENGTH_LONG).show();
                                finish();
                                return;
                            }
                            FuncoesApi.FuncoesReports.criarNovoReportIndoor(getApplicationContext()
                                    , inputDescricaoReport.getText().toString(), niveisDensidadeArray.indexOf(inputNivelDensidade.getText().toString()) + 1
                                    , localIndoorID, f.getIDUtilizador(), new FuncoesApi.volleycallback() {
                                        @Override
                                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                                            Log.i("pedido",jsonObject.toString());
                                            Toast.makeText(getApplicationContext(),"Report submetido com sucesso",Toast.LENGTH_LONG).show();
                                            finish();
                                        }

                                        @Override
                                        public void onError(JSONObject jsonObjectErr) throws JSONException {
                                            Log.i("pedido",jsonObjectErr.toString());
                                            Toast.makeText(getApplicationContext(),"Erro a submeter report",Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            FuncoesApi.FuncoesReports.criarNovoReportOutdoorUtilInst(getApplicationContext(),
                                    inputDescricaoReport.getText().toString(),
                                    niveisDensidadeArray.indexOf(inputNivelDensidade.getText().toString()) + 1,
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
            }
        });
        //endregion
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
                Log.i("pedido",jsonObjectErr.toString());
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
        /*inputDescricaoReport.setEnabled(ativa);
        inputNivelDensidade.setEnabled(ativa);
        inputLocalIndoor.setEnabled(ativa);
        menuLayoutLocalIndoor.setEnabled(ativa);
        if(!ativa) {

            menuLayoutNivelDensidade.setAlpha(0.4f);
            findViewById(R.id.descricaoNovoReport).setAlpha(0.4f);
        }else{
            menuLayoutNivelDensidade.setAlpha(1.0f);
            findViewById(R.id.descricaoNovoReport).setAlpha(1.0f);
        }
        if(!isReportIndoor.isChecked()){
            inputLocalIndoor.setEnabled(false);
        }*/
        TextInputLayout ndensidtextinplay = findViewById(R.id.menuNivelDensidade)
                ,desctexinplay = findViewById(R.id.descricaoNovoReport)
                ,loctextinplay = findViewById(R.id.menuLocaisIndoor);
        AutoCompleteTextView localindoorautocomp = findViewById(R.id.autoCompleteLocaisIndoor),
                ndensidadeautocomp = findViewById(R.id.autoCompleteNivelReport);
        TextInputEditText descricaoautocomp = findViewById(R.id.inputDescricaoNovoReport);
        CheckBox checkbox = findViewById(R.id.checkBoxIsReportIndoor);
        if(!ativa){
            ndensidtextinplay.setAlpha(0.4f);
            ndensidtextinplay.setEnabled(false);
            desctexinplay.setAlpha(0.4f);
            desctexinplay.setEnabled(false);
            loctextinplay.setAlpha(0.4f);
            loctextinplay.setEnabled(false);
            localindoorautocomp.setEnabled(false);
            localindoorautocomp.setAlpha(0.4f);
            ndensidadeautocomp.setEnabled(false);
            ndensidadeautocomp.setAlpha(0.4f);
            descricaoautocomp.setEnabled(false);
            descricaoautocomp.setAlpha(0.4f);
            checkbox.setEnabled(false);
            checkbox.setAlpha(0.4f);
        }else{
            ndensidtextinplay.setAlpha(1f);
            ndensidtextinplay.setEnabled(true);
            desctexinplay.setAlpha(1f);
            desctexinplay.setEnabled(true);
            loctextinplay.setAlpha(1f);
            loctextinplay.setEnabled(true);
            localindoorautocomp.setEnabled(true);
            localindoorautocomp.setAlpha(1f);
            ndensidadeautocomp.setEnabled(true);
            ndensidadeautocomp.setAlpha(1f);
            descricaoautocomp.setEnabled(true);
            descricaoautocomp.setAlpha(1f);
            checkbox.setEnabled(true);
            checkbox.setAlpha(1f);
        }
        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        if(!f.getVerificacao()){
            isReportIndoor.setEnabled(false);
            isReportIndoor.setAlpha(0.4f);
            localindoorautocomp.setEnabled(false);
            localindoorautocomp.setAlpha(0.4f);
        }

    }

    private void obterLocaisIndoor(){

        FuncoesApi.FuncoesLocais.getListaLocaisIndoor(getApplicationContext(), idlocal, new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                Log.i("pedido", "aaa "+jsonObject.toString());
                JSONArray locais = jsonObject.getJSONArray("LocaisIndoor");
                for(int i = 0;i < locais.length();i++){
                    String nomelocalindoor = locais.getJSONObject(i).getString("Nome");
                    int idlocalindoor = locais.getJSONObject(i).getInt("ID_Local_Indoor");
                    listaParesIDNomeLocalIndoor.add(new Pair<>(idlocalindoor, nomelocalindoor));
                    locaisIndoorArray.add(nomelocalindoor);
                }
                ArrayAdapter<String> arrayAdapterLocaisIndoor = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.lista_niveis_densidade,locaisIndoorArray);
                inputLocalIndoor.setAdapter(arrayAdapterLocaisIndoor);
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
            }
        });

    }
}