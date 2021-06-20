package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.crowdzero_v000.fragmentos.CardReportFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaReportsInstituicaoActivity extends NavDrawerActivity {
    String nome,descricao;
    int idlocal;
    ArrayList<CardReportFragment> arrayListCardReportFragment = new ArrayList<>();
    ScrollView scrollViewListaReports;
    FloatingActionButton botaoNovoReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reports_instituicao);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");
        idlocal = getIntent().getExtras().getInt("idlocal");
        mudarNomeToolBar(nome);

        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutListaReportsInstituicao));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());

        scrollViewListaReports =findViewById(R.id.ScrollViewListaReportsInstituicoes);

        botaoNovoReport = findViewById(R.id.criarReportBotao);
        botaoNovoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarNovoReport();
            }
        });

        try {
            getListaReports();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*adicionarCard("João Soeiro","às 09:47 de 20/05/21",
                "ola sou gay 1213",
                1,
                "Pouco populado");
        adicionarCard("Martinho","às 09:47 de 20/05/21",
                "muito 1213",
                2,
                "Muito populado");
        adicionarCard("Altívio","às 09:47 de 20/05/21",
                "muito 1213",
                3,
                "Muito populado");
        adicionarCard("Egas Bartolo","às 09:47 de 20/05/21",
                "muito 1213",
                3,
                "Muito populado");*/
    }

    void adicionarCard(String nomePessoa,String data, String descricaoReport, int idReport, String populacao){

        CardReportFragment card = CardReportFragment.newInstance(nomePessoa
                ,data
                ,descricaoReport + " idreport "+idReport
                ,idReport
                ,populacao);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayoutScrollViewListaReportsInstituicoes,card).commit();
        //getSupportFragmentManager().beginTransaction()
        //        .add(scrollViewListaReports.getId(),card, "report " + idReport)
        //        .commit();
        arrayListCardReportFragment.add(card);
    };

    void criarNovoReport(){
        Intent i = new Intent(getApplicationContext(),CriarReportLocal.class);
        i.putExtra("idlocal",idlocal);
        i.putExtra("nome",nome);
        i.putExtra("descricao",descricao);
        startActivity(i);
    }

    void getListaReports() throws JSONException {
        FuncoesApi.FuncoesReports.getListaReportsOutdoor(getApplicationContext(), idlocal, "hh", 6, new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                JSONArray reportsOutros = jsonObject.getJSONObject("Reports").getJSONArray("ReportsOutrosUtil");
                JSONArray reportsInst = jsonObject.getJSONObject("Reports").getJSONArray("ReportsUtilInst");
                for(int i = 0;i< reportsOutros.length(); i++){
                    JSONObject report = reportsOutros.getJSONObject(i);
                    adicionarCard(report.getString());
                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {

            }
        });
    }

}