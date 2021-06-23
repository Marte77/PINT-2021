package com.example.crowdzero_v000;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    int tempo = 12;
    String tipoTempo ="hh";
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
                botaoNovoReport.cancelPendingInputEvents();//prevenir duplos cliques que duplicam o report
                criarNovoReport();
            }
        });

        try {
            getListaReports(tempo,tipoTempo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void adicionarCard(String nomePessoa,String data, String descricaoReport, int idReport, int INTpopulacao){
        String populacao ="";
        // TODO: 20/06/2021 colocar imagem da pessoa
        switch (INTpopulacao){
            case 1:populacao = "Pouco Populado"; break;
            case 2:populacao = "Muito Populado";break;
            case 3:populacao = "Extremamente Populado";break;
            case 0:populacao = "Sem População";break;
            default:populacao = "Erro";break;
        }


        CardReportFragment card = CardReportFragment.newInstance(nomePessoa
                ,data
                ,descricaoReport
                ,idReport
                ,populacao,
                (new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE))).getIDPessoa());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayoutScrollViewListaReportsInstituicoes,card).commit();

        arrayListCardReportFragment.add(card);
    };

    void criarNovoReport(){
        Intent i = new Intent(getApplicationContext(),CriarReportLocal.class);
        i.putExtra("idlocal",idlocal);
        i.putExtra("nome",nome);
        i.putExtra("descricao",descricao);
        startActivity(i);
    }

    @Override
    protected void onPostResume() { //refresh reports depois de submeter um report
        super.onPostResume();
        LinearLayout ll = findViewById(R.id.linearLayoutScrollViewListaReportsInstituicoes);
        ll.removeAllViews();
        try {
            getListaReports(tempo,tipoTempo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tipoTempo: = hh - horas, mm - minutos, dd - dias
     * @param tempo: int da quantidade de tipoTempo
     */
    void getListaReports(int tempo, String tipoTempo) throws JSONException {
        FuncoesApi.FuncoesReports.getListaReportsOutdoor(getApplicationContext(), idlocal, tipoTempo,tempo, new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                JSONArray reportsOutros = jsonObject.getJSONObject("Reports").getJSONArray("ReportsOutrosUtil");
                JSONArray reportsInst = jsonObject.getJSONObject("Reports").getJSONArray("ReportsUtilInst");
                for(int i = 0;i< reportsOutros.length(); i++){

                    JSONObject reportOutro = reportsOutros.getJSONObject(i);
                    JSONObject report = reportOutro.getJSONObject("Report");
                    JSONObject pessoa = reportOutro.getJSONObject("Outros_Util").getJSONObject("Pessoa");//ele bloqueia aqui
                    String dataReport = report.getString("Data");
                    adicionarCard(pessoa.getString("PNome") +" "+ pessoa.getString("UNome")
                            ,"Às "+
                                    dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6))
                                    + " de " +dataReport.substring(0,dataReport.indexOf('T')),
                            report.getString("Descricao")
                            ,report.getInt("ID_Report"),report.getInt("Nivel_Densidade"));
                }

                for(int i = 0;i< reportsInst.length(); i++){
                    JSONObject reportOutro = reportsInst.getJSONObject(i);
                    JSONObject report = reportOutro.getJSONObject("Report");
                    JSONObject pessoa = reportOutro.getJSONObject("Utils_Instituicao").getJSONObject("Pessoa");
                    String dataReport = report.getString("Data");

                    adicionarCard(pessoa.getString("PNome") +" "+ pessoa.getString("UNome")
                            ,"Às "+
                                    dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6))
                                    + " de " +dataReport.substring(0,dataReport.indexOf('T')),
                            report.getString("Descricao")
                            ,report.getInt("ID_Report"),report.getInt("Nivel_Densidade"));
                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
                Toast.makeText(getApplicationContext(),"Erro a obter os reports em " + nome, Toast.LENGTH_LONG).show();
            }
        });
    }

}