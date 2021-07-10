package com.example.crowdzero_v000;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.example.crowdzero_v000.fragmentos.CardReportFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ListaReportsInstituicaoActivity extends NavDrawerActivity {
    String nome,descricao;
    int idlocal;

    /**
     * chaves do hashmap:
     * "nome"
     * "data"
     * "descricao"
     * "idreport"
     * "nlikes"
     * "ndislikes"
     * "niveldensidade"
     * */
    ArrayList<Pair<HashMap<String,Object>,String>> arrayListCardReportFragment = new ArrayList<>();

    ScrollView scrollViewListaReports;
    FloatingActionButton botaoNovoReport;

    int tempo = 12;
    String tipoTempo ="hh";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbar,menu);
        return true;
    }

    void listarReportsIndoor(){
        adicionarCardsDoArray("Indoor");
    }
    void listarReportsOutdoor(){
        adicionarCardsDoArray("Outdoor");
    }
    void listarTodosReports(){
        adicionarCardsDoArray("gaming");
    }

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

        FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        //colocar botao das opcoes na barra de navegacoes
        if(f.getVerificacao() && f.getTipoPessoa().equals("Util_Instituicao"))
            tb.getMenu().getItem(0).setVisible(true);

        scrollViewListaReports =findViewById(R.id.ScrollViewListaReportsInstituicoes);

        botaoNovoReport = findViewById(R.id.criarReportBotao);
        botaoNovoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoNovoReport.cancelPendingInputEvents();//prevenir duplos cliques que duplicam o report
                criarNovoReport();
            }
        });

        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("testar", String.valueOf(arrayListCardReportFragment.size()));
                if(item.toString().equals(getString(R.string.reportes_indoor))){
                    listarReportsIndoor();
                }else if(item.toString().equals(getString(R.string.reportes_outdoor))){
                    listarReportsOutdoor();
                }else if(item.toString().equals(getString(R.string.todos_os_reportes))){
                    listarTodosReports();
                }
                return true;
            }
        });

        try {
            getListaReports(tempo,tipoTempo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void criarNovoReport(){
        Intent i = new Intent(getApplicationContext(),CriarReportLocal.class);
        i.putExtra("idlocal",idlocal);
        i.putExtra("nome",nome);
        i.putExtra("descricao",descricao);
        startActivity(i);
    }


    boolean estevenoonpause = false;
    @Override
    protected void onPause() {
        super.onPause();
        estevenoonpause = true; //apenas para identificar que veio de outra activity,
                                // caso contrario iriam existir cardsduplicados
    }

    @Override
    protected void onPostResume() { //refresh reports depois de submeter um report
        super.onPostResume();

        if(estevenoonpause){
            /*LinearLayout ll = findViewById(R.id.linearLayoutScrollViewListaReportsInstituicoes);
            ll.removeAllViews();
            try {
                getListaReports(tempo,tipoTempo);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            adicionarCardsDoArray("Outdoor");
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
                    //adicionarCard("Outdoor",pessoa.getString("PNome") +" "+ pessoa.getString("UNome")
                    //        ,"Às "+
                    //                dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6))
                    //                + " de " +dataReport.substring(0,dataReport.indexOf('T')),
                    //        report.getString("Descricao")
                    //        ,report.getInt("ID_Report"),report.getInt("Nivel_Densidade"), report.getInt("N_Likes"),report.getInt("N_Dislikes"));
                    HashMap<String, Object> reportInfos = new HashMap<>();
                    reportInfos.put("nome",pessoa.getString("PNome") +" "+ pessoa.getString("UNome"));
                    reportInfos.put("data","Às "+ dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6)) + " de " +dataReport.substring(0,dataReport.indexOf('T')));
                    reportInfos.put("descricao",report.getString("Descricao"));
                    reportInfos.put("idreport",report.getInt("ID_Report"));
                    reportInfos.put("nlikes",report.getInt("N_Likes"));
                    reportInfos.put("ndislikes",report.getInt("N_Dislikes"));
                    reportInfos.put("niveldensidade",report.getInt("Nivel_Densidade"));
                    arrayListCardReportFragment.add(new Pair<>(reportInfos,"Outdoor"));
                }

                for(int i = 0;i< reportsInst.length(); i++){
                    JSONObject reportOutro = reportsInst.getJSONObject(i);
                    JSONObject report = reportOutro.getJSONObject("Report");
                    JSONObject pessoa = reportOutro.getJSONObject("Utils_Instituicao").getJSONObject("Pessoa");
                    String dataReport = report.getString("Data");

                    //adicionarCard("Outdoor",pessoa.getString("PNome") +" "+ pessoa.getString("UNome")
                    //        ,"Às "+
                    //                dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6))
                    //                + " de " +dataReport.substring(0,dataReport.indexOf('T')),
                    //        report.getString("Descricao")
                    //        ,report.getInt("ID_Report"),report.getInt("Nivel_Densidade"), report.getInt("N_Likes"),report.getInt("N_Dislikes"));
                    HashMap<String, Object> reportInfos = new HashMap<>();
                    reportInfos.put("nome",pessoa.getString("PNome") +" "+ pessoa.getString("UNome"));
                    reportInfos.put("data","Às "+ dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6)) + " de " +dataReport.substring(0,dataReport.indexOf('T')));
                    reportInfos.put("descricao",report.getString("Descricao"));
                    reportInfos.put("idreport",report.getInt("ID_Report"));
                    reportInfos.put("nlikes",report.getInt("N_Likes"));
                    reportInfos.put("ndislikes",report.getInt("N_Dislikes"));
                    reportInfos.put("niveldensidade",report.getInt("Nivel_Densidade"));
                    arrayListCardReportFragment.add(new Pair<>(reportInfos,"Outdoor"));

                }
                adicionarCardsDoArray("Outdoor");
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
                Toast.makeText(getApplicationContext(),"Erro a obter os reports em " + nome, Toast.LENGTH_LONG).show();
            }
        });
        FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        if(f.getVerificacao() && f.getTipoPessoa().equals("Util_Instituicao")){
            FuncoesApi.FuncoesReports.getListaReportsIndoor(getApplicationContext(), idlocal, tipoTempo, tempo, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    JSONArray listaReports = jsonObject.getJSONArray("ReportsIndoor");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    for(int i =0; i<listaReports.length();i++){
                        JSONObject report, reportindoor, utilinst, pessoa, localindoor;
                        reportindoor = listaReports.getJSONObject(i);
                        localindoor = reportindoor.getJSONObject("Local_Indoor");
                        report = reportindoor.getJSONObject("Report");
                        utilinst = reportindoor.getJSONObject("Utils_Instituicao");
                        pessoa = utilinst.getJSONObject("Pessoa");
                        String dataReport = report.getString("Data");
                        //criarCard("Indoor",pessoa.getString("PNome") +pessoa.getString("UNome")
                        //        ,"Às "+
                        //                dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6))
                        //                + " de " +dataReport.substring(0,dataReport.indexOf('T')),report.getString("Descricao") +";"
                        //                +localindoor.getString("Nome") + " Piso: " + localindoor.getInt("Piso")
                        //        ,report.getInt("ID_Report"),report.getInt("Nivel_Densidade")
                        //        ,report.getInt("N_Likes"),report.getInt("N_Dislikes"));
                        HashMap<String, Object> reportInfos = new HashMap<>();
                        reportInfos.put("nome",pessoa.getString("PNome") +" "+ pessoa.getString("UNome"));
                        reportInfos.put("data","Às "+ dataReport.substring(dataReport.indexOf('T')+1,(dataReport.indexOf('T')+6)) + " de " +dataReport.substring(0,dataReport.indexOf('T')));
                        reportInfos.put("descricao",report.getString("Descricao") +";" +localindoor.getString("Nome") + " Piso: " + localindoor.getInt("Piso"));
                        reportInfos.put("idreport",report.getInt("ID_Report"));
                        reportInfos.put("nlikes",report.getInt("N_Likes"));
                        reportInfos.put("ndislikes",report.getInt("N_Dislikes"));
                        reportInfos.put("niveldensidade",report.getInt("Nivel_Densidade"));
                        arrayListCardReportFragment.add(new Pair<>(reportInfos,"Indoor"));
                    }
                }
                @Override
                public void onError(JSONObject jsonObjectErr) throws JSONException {
                    Log.i("pedido",jsonObjectErr.toString());
                }
            });
        }
    }


    void adicionarCardSegundoHashMap(HashMap<String,Object> map){
        /**
         * chaves do hashmap:
         * "nome"
         * "data"
         * "descricao"
         * "idreport"
         * "nlikes"
         * "ndislikes"
         * "niveldensidade"
         * */
        String populacao ="";
        int ndensidade, nLikes, nDislikes, idReport;
        try {
            ndensidade = (int) map.get("niveldensidade");
            idReport = (int) map.get("idreport");
            nLikes = (int) map.get("nlikes");
            nDislikes = (int) map.get("ndislikes");
        }catch (NullPointerException e){
            e.printStackTrace();
            return;
        }
        String nomePessoa = (String) map.get("nome")
                , data = (String) map.get("data")
                , descricaoReport = (String)map.get("descricao");
        switch (ndensidade){
            case 1:populacao = "Pouco Populado"; break;
            case 2:populacao = "Muito Populado";break;
            case 3:populacao = "Extremamente Populado";break;
            case 0:populacao = "Sem População";break;
            default:populacao = "Erro";break;
        }


        CardReportFragment card = CardReportFragment.newInstance(nomePessoa
                , data
                , descricaoReport
                , idReport
                ,populacao,
                (new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE))).getIDPessoa(),
                nLikes, nDislikes);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayoutScrollViewListaReportsInstituicoes,card).commit();
    }
    /**
     * @param tipoReports = "Outdoor" || "Indoor"
     * - serve para indicar se é indoor ou outdoor ou ambos para a selecao
     */
    void adicionarCardsDoArray(String tipoReports){
        LinearLayout ll = findViewById(R.id.linearLayoutScrollViewListaReportsInstituicoes);
        ll.removeAllViews();
        if(tipoReports.equals("Outdoor")){
            for(Pair<HashMap<String,Object>,String> par : arrayListCardReportFragment){
                if(par.second.equals("Outdoor")){
                    adicionarCardSegundoHashMap(par.first);
                }
            }
        }else if(tipoReports.equals("Indoor")){
            for(Pair<HashMap<String,Object>,String> par : arrayListCardReportFragment){
                if(par.second.equals("Indoor")){
                    adicionarCardSegundoHashMap(par.first);
                }
            }
        }else{//ambos
            for(Pair<HashMap<String,Object>,String> par : arrayListCardReportFragment){
                adicionarCardSegundoHashMap(par.first);
            }
        }
    }

}