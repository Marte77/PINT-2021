package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.crowdzero_v000.fragmentos.CardReportFragment;

import java.util.ArrayList;

public class ListaReportsInstituicaoActivity extends NavDrawerActivity {
    String nome,descricao;
    ArrayList<CardReportFragment> arrayListCardReportFragment = new ArrayList<>();
    ScrollView scrollViewListaReports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reports_instituicao);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");
        mudarNomeToolBar(nome);

        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutListaReportsInstituicao));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());

        scrollViewListaReports =findViewById(R.id.ScrollViewListaReportsInstituicoes);

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
    }

    void adicionarCard(String nomePessoa,String data, String descricaoReport, int idReport, String populacao){

        CardReportFragment card = CardReportFragment.newInstance(nomePessoa
                ,data
                ,descricaoReport + " idreport "+idReport
                ,idReport
                ,populacao);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayoutScrollViewListaReportsInstituicoes,card).commit();
        Log.e("testar", String.valueOf(idReport));
        //getSupportFragmentManager().beginTransaction()
        //        .add(scrollViewListaReports.getId(),card, "report " + idReport)
        //        .commit();
        arrayListCardReportFragment.add(card);
    };

}