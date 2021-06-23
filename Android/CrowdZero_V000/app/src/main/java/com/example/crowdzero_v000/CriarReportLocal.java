package com.example.crowdzero_v000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.crowdzero_v000.FuncoesApi.FuncoesReports.criarNovoReportOutdoorOutrosUtil;

public class CriarReportLocal extends NavDrawerActivity {
    String nome, descricao;
    int idlocal;


    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    ArrayList<String> niveisDensidadeArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_report_instituicao);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");
        idlocal = getIntent().getExtras().getInt("idlocal");
        tb.setTitle(nome);

        niveisDensidadeArray.add("Pouco Populado");
        niveisDensidadeArray.add("Muito Populado");
        niveisDensidadeArray.add("Extremamente Populado");


        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        if(f.getVerificacao()){
            // TODO: 23/06/2021 fazer cenas relativas ao report indoor
        }


        autoCompleteTextView = findViewById(R.id.autoCompleteNivelReport);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.lista_niveis_densidade,niveisDensidadeArray);
        autoCompleteTextView.setAdapter(arrayAdapter);
        textInputLayout = findViewById(R.id.menuNivelDensidade);

        textInputEditText = findViewById(R.id.inputDescricaoNovoReport);

        f.logarSharedPrefs();
        Log.i("testar",""+f.getIDUtilizador());

        MaterialButton button = findViewById(R.id.submeterNovoReport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23/06/2021 fazer report indoor
                if(autoCompleteTextView.getText().toString().isEmpty() || textInputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Tem de preencher os campos!",Toast.LENGTH_SHORT).show();
                    return;
                }
                String tipoPessoa = f.getTipoPessoa();
                if(tipoPessoa.equals("Outros_Util")){
                    try {
                        FuncoesApi.FuncoesReports.criarNovoReportOutdoorOutrosUtil(getApplicationContext(),
                                textInputEditText.getText().toString(),
                                niveisDensidadeArray.indexOf(autoCompleteTextView.getText().toString()) + 1,
                                idlocal,
                                f.getIDUtilizador(),
                                new FuncoesApi.volleycallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                                        Log.i("pedido",jsonObject.toString());
                                        Toast.makeText(getApplicationContext(),"Report criado com sucesso",Toast.LENGTH_LONG).show();
                                        finish();
                                    }

                                    @Override
                                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                                        Log.i("pedido","Erro a criar report out oturo util: "+jsonObjectErr.toString());
                                        Toast.makeText(getApplicationContext(),"Erro a criar report!",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{ //report outdoor util instituicao
                    try {
                        FuncoesApi.FuncoesReports.criarNovoReportOutdoorUtilInst(getApplicationContext(),
                                textInputEditText.getText().toString(),
                                niveisDensidadeArray.indexOf(autoCompleteTextView.getText().toString()) + 1,
                                idlocal,
                                f.getIDUtilizador(),
                                new FuncoesApi.volleycallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                                        Log.i("pedido",jsonObject.toString());
                                        Toast.makeText(getApplicationContext(),"Report criado com sucesso",Toast.LENGTH_LONG).show();
                                        finish();
                                    }

                                    @Override
                                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                                        Log.i("pedido","Erro a criar report out utilinst: "+jsonObjectErr.toString());
                                        Toast.makeText(getApplicationContext(),"Erro a criar report!",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



}