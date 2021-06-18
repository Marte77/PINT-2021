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

        adicionarCard("João Soeiro","às 09:47 de 20/05/21",
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
                "Muito populado");
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

        /*try {
            FuncoesApi.FuncoesReports.criarNovoReportOutdoorOutrosUtil(
                    getApplicationContext(),
                    "muita gente", 3, 3, 1
                    ,VCB
            );
        }catch(JSONException e){
            Log.i("pedido","ERRO NO LISTAREPORTSINST"+e);
        }*/
        Intent i = new Intent(getApplicationContext(),CriarReportLocal.class);
        i.putExtra("idlocal",idlocal);
        i.putExtra("nome",nome);
        i.putExtra("descricao",descricao);
        startActivity(i);
    }


    FuncoesApi.volleycallback VCB = new FuncoesApi.volleycallback() {
        @Override
        public void onSuccess(JSONObject response) throws JSONException {
            /*int statuscode;
            try {
                statuscode = jsonObject.getInt("status");
                Toast.makeText(getApplicationContext(), statuscode, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            Log.i("pedido",response.toString());
            if(response.getInt("status") == 500){
                Toast.makeText(getApplicationContext(),"ERRO A CRIAR",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"SUCESSO LETS GOO",Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onError(JSONObject jsonObjectErr) throws JSONException {
            Log.i("pedido",jsonObjectErr.toString());
        }
    };

}