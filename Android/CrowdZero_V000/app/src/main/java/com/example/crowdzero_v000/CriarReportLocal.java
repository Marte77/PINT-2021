package com.example.crowdzero_v000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        // TODO: 18/06/2021 verificar se o utilizador é da instituicao

        autoCompleteTextView = findViewById(R.id.autoCompleteNivelReport);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.lista_niveis_densidade,niveisDensidadeArray);
        autoCompleteTextView.setAdapter(arrayAdapter);
        textInputLayout = findViewById(R.id.menuNivelDensidade);

        textInputEditText = findViewById(R.id.inputDescricaoNovoReport);



        MaterialButton button = findViewById(R.id.submeterNovoReport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 18/06/2021 submeter report
                if(autoCompleteTextView.getText().toString().isEmpty() || textInputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Tem de preencher os campos!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(false == false)// TODO: 18/06/2021 verificar se é util instituicao
                {
                    try {
                        FuncoesApi.FuncoesReports.criarNovoReportOutdoorOutrosUtil(getApplicationContext(),
                                textInputEditText.getText().toString(),
                                niveisDensidadeArray.indexOf(autoCompleteTextView.getText().toString()) + 1,
                                idlocal,
                                1,
                                new FuncoesApi.volleycallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                                        Log.i("pedido",jsonObject.toString());
                                    }

                                    @Override
                                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                                        Log.i("pedido",jsonObjectErr.toString());
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        /*
        try {
            criarNovoReportOutdoorOutrosUtil(getApplicationContext(), descricaoReport, nivelDensidade, idlocal, 1,
                    new FuncoesApi.volleycallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            Log.i("testar",jsonObject.toString());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

    }



}