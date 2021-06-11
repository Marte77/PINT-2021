package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        //adicionarCard("boas","tudo bem",1);
        //adicionarCard("boas1","tudo bem1",2);
        //adicionarCard("boas2","tudo bem2",3);
        //adicionarCard("boas3","tudo bem3",4);
    }

    void adicionarCard(String nomePessoaEData,String descricaoReport,int idReport){
        //todo: ver pq e que isto nao coloca os cards na scroll view
        CardReportFragment card = CardReportFragment.newInstance(nomePessoaEData,descricaoReport + " idreport "+idReport,idReport);
        getSupportFragmentManager().beginTransaction()
                .add(scrollViewListaReports.getId(),card, "report " + idReport)
                .commit();
        arrayListCardReportFragment.add(card);
    };

}