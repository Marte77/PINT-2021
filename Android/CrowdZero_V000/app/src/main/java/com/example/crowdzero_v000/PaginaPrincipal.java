package com.example.crowdzero_v000;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.example.crowdzero_v000.fragmentos.CardReportFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//<!--Note: The layout_width and layout_height attributes should be set to wrap_content, match_parent, or a custom dimension depending on the navigation drawer type and parent ViewGroup.-->

public class PaginaPrincipal extends NavDrawerActivity {

    int idpessoa = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        final FuncoesSharedPreferences f =(new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE)));
        idpessoa = f.getIDPessoa();

        FuncoesApi.FuncoesPessoas.verificarSeJaFoiVerificado(getApplicationContext(),
                f.getIDUtilizador(),
                new FuncoesApi.volleycallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                        if(f.getTipoPessoa().equals("Util_Instituicao"))
                            if(jsonObject.getJSONObject("Estado").getBoolean("Verificado"))
                                f.setVerificacao(true);
                    }

                    @Override
                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                        Log.i("pedido","Erro ver se ja foi verificaod: " + jsonObjectErr);
                    }
                });
        obterDadosParaPieChart();
        obterReportMaisRelevante();
        /*
        * if(utilizador pertence a empresa){
        *   adicionarPieChartUtilizadorEmpresa()}
        * else adicionarPieChartOutrosUtilizadores()*/
        //adicionarPieChartOutrosUtilizadores();
        //adicionarPieChartUtilizadorEmpresa();
    }

    private void setupGrafico(PieChart graficoReports) {
        graficoReports.setDrawHoleEnabled(false);
        graficoReports.setUsePercentValues(true);
        graficoReports.getLegend().setEnabled(false);
        graficoReports.getDescription().setEnabled(false);
    }

    void obterDadosParaPieChart(){
        try{
            FuncoesApi.FuncoesReports.getPercentagemReportsLocais(getApplicationContext(), 3, "dd", 2, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    Log.i("pedido",jsonObject.toString());
                    JSONArray arrayResultado = jsonObject.getJSONArray("Resultado");
                    ArrayList<PieEntry> pieEntries =new ArrayList<>();
                    if(jsonObject.getInt("NumeroReportsTotal") == 0) {
                        pieEntries.add(new PieEntry(1f,"Sem dados"));
                        adicionarDadosAoPieChart(pieEntries);
                        return;
                    }
                    for(int i =0;i<arrayResultado.length();i++){
                        JSONObject dado = arrayResultado.getJSONObject(i);
                        String nome = dado.getString("NomeLocal");
                        if((dado.get("PercentagemLocal")).getClass().getName().equals("java.lang.Double")){
                            double percentagem = (double) dado.get("PercentagemLocal");
                            if(percentagem != 0) {
                                PieEntry pieEntry = new PieEntry((float) (percentagem / 100), nome);
                                pieEntries.add(pieEntry);
                            }
                        }else if((dado.get("PercentagemLocal")).getClass().getName().equals("java.lang.Integer")){
                            int percentagem = (int) dado.get("PercentagemLocal");
                            if(percentagem != 0) {
                                PieEntry pieEntry = new PieEntry(((float) percentagem / 100), nome);
                                pieEntries.add(pieEntry);
                            }
                        }
                    }
                    adicionarDadosAoPieChart(pieEntries);
                }

                @Override
                public void onError(JSONObject jsonObjectErr){
                    Log.i("pedido",jsonObjectErr.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.i("pedido","Exception dados piechar: "+e);
        }
    }

    void adicionarDadosAoPieChart(ArrayList<PieEntry> pieEntries){
        LinearLayout LLGraficoReports = findViewById(R.id.linearLayoutCardGraficoReport);
        TextView textView = findViewById(R.id.textViewCardReportPaginaPrincipal);
        textView.setText(Html.fromHtml("<h2><b>"+textView.getText()+"</b></h2>"));
        textView.setGravity(Gravity.CENTER);
        PieChart graficoReports = findViewById(R.id.GraficoReportCardPagPrincipal);
        setupGrafico(graficoReports);
        //cores para os dados
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: FuncoesApi.CORES_GRAFICOS) {//ColorTemplate.LIBERTY_COLORS
            colors.add(color);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Concentração em Viseu");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(graficoReports));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        graficoReports.setEntryLabelColor(Color.BLACK);
        graficoReports.setData(pieData);
        graficoReports.invalidate();
        graficoReports.animateY(1400, Easing.EaseInOutQuad);
    }

    void obterReportMaisRelevante(){
        try {
            FuncoesApi.FuncoesReports.getReportMaisRelevante(getApplicationContext(), "dd", 1, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    Log.i("pedido",jsonObject.toString());
                    JSONObject reportrelevante = jsonObject.getJSONArray("ReportRelevante").getJSONObject(0);
                    JSONObject reportAdjacente = jsonObject.getJSONObject("ReportAdjacente");
                    JSONObject Pessoa = jsonObject.getJSONObject("Pessoa");
                    JSONObject Local = jsonObject.getJSONObject("Local");
                    String data = reportrelevante.getString("Data");
                    data = "Às " + data.substring(11,16) + " de " +data.substring(0,10);
                    String populacao = "";int ndensidade = reportrelevante.getInt("Nivel_Densidade");
                    switch (ndensidade){
                        case 1:populacao = "Pouco Populado"; break;
                        case 2:populacao = "Muito Populado";break;
                        case 3:populacao = "Extremamente Populado";break;
                        case 0:populacao = "Sem População";break;
                        default:populacao = "Erro";break;
                    }

                    criarCardReportMaisLikes(reportrelevante, reportAdjacente, Pessoa, Local, data, populacao);
                }

                @Override
                public void onError(JSONObject jsonObjectErr) {
                    Log.i("pedido",jsonObjectErr.toString());
                    CardReportFragment cardInicial = CardReportFragment.newInstance("Martinho","às 09:47 de 20/05/21","Muitas pessoas na fila da cantina assustei-me"
                            ,-1,"ERROOOO",getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE).getInt("IDUtil",0),
                            0,0);
                    adicionarCard(cardInicial,"Camâra de Viseu");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.i("pedido","Erro catch report relevante pagina principal: " + e);
        }
    }

    private void criarCardReportMaisLikes(final JSONObject reportrelevante, final JSONObject reportAdjacente, final JSONObject pessoa, final JSONObject local, final String data, final String populacao) throws JSONException {
        CardReportFragment cardInicial =CardReportFragment.newInstance(
                pessoa.getString("PNome")+ ' ' + pessoa.getString("UNome"),
                data,
                reportrelevante.getString("Descricao"),
                reportAdjacente.getInt("ReportIDReport"),
                populacao,idpessoa, reportrelevante.getInt("N_Likes"),
                reportrelevante.getInt("N_Dislikes"));
        String nomeLocal = local.getString("Nome");

        adicionarCard(cardInicial, nomeLocal);
    }

    private void adicionarCard(final CardReportFragment cardInicial, final String nomeLocal) {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout, cardInicial).commit();
        TextView textView = findViewById(R.id.textViewPaginaPrincipalReport);
        textView.setText(textView.getText()+"\n " + nomeLocal);
    }

    /*private void adicionarPieChartOutrosUtilizadores(){
        //MaterialCardView MCVPieCharts = findViewById(R.id.cardPieChartsPaginaPrincipal);
        LinearLayout LLGraficoReports = findViewById(R.id.linearLayoutCardGraficoReport);
        TextView textView = findViewById(R.id.textViewCardReportPaginaPrincipal);
        textView.setText(Html.fromHtml("<h2><b>"+textView.getText()+"</b></h2>"));
        textView.setGravity(Gravity.CENTER);
        //textView.setText("Concentração em Viseu");
        //LLGraficoReports.addView(textView);

        PieChart graficoReports = findViewById(R.id.GraficoReportCardPagPrincipal);
        //LLGraficoReports.addView(graficoReports);

        setupGrafico(graficoReports);

        //encher grafico com dados imaginarios
        ArrayList<PieEntry> pieEntries =new ArrayList<>();
        pieEntries.add(new PieEntry(0.25f,"Palácio do Gelo"));
        pieEntries.add(new PieEntry(0.15f,"ESTGV"));
        pieEntries.add(new PieEntry(0.15f,"Camara de Viseu"));
        pieEntries.add(new PieEntry(0.25f,"Fórum Viseu"));
        pieEntries.add(new PieEntry(0.10f,"BizDirect"));

        //cores para os dados
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.JOYFUL_COLORS) {
            colors.add(color);
        }

        //criar um set de dados e atribuir cores a cada entry
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Concentração em Viseu");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(graficoReports));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        graficoReports.setData(pieData);
        graficoReports.invalidate();
        graficoReports.animateY(1400, Easing.EaseInOutQuad);
    }



    private void adicionarPieChartUtilizadorEmpresa(){
        MaterialCardView MCVPieCharts = findViewById(R.id.cardPieChartsPaginaPrincipal);
        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        ll.setOrientation(LinearLayout.HORIZONTAL);

        MCVPieCharts.addView(ll);
        PieChart graficoReportsPrimeiroPiso = new PieChart(getApplicationContext());
        PieChart graficoReportsSegundoPiso = new PieChart(getApplicationContext());

        //TODO: METER OS GRAFICOS COM A MESMA ALTURA E METADE DA LARGURA DO LAYOUT
        ll.addView(graficoReportsPrimeiroPiso);
        ll.addView(graficoReportsSegundoPiso);

        setupGrafico(graficoReportsPrimeiroPiso);
        setupGrafico(graficoReportsSegundoPiso);
        AdicionarDadosPrimeiroPisoGrafico(graficoReportsPrimeiroPiso);
        AdicionarDadosSegundoPisoPisoGrafico(graficoReportsSegundoPiso);

    }

    private void AdicionarDadosPrimeiroPisoGrafico(PieChart graficoReportsPrimeiroPiso) {
        //encher grafico com dados imaginarios
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(0.25f, "Escritório 1"));
        pieEntries.add(new PieEntry(0.15f, "Escritório Administracao"));
        pieEntries.add(new PieEntry(0.15f, "WC Funcionarios"));
        pieEntries.add(new PieEntry(0.25f, "Zona Impressora"));
        pieEntries.add(new PieEntry(0.10f, "SnackBar"));

        //cores para os dados
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.JOYFUL_COLORS) {
            colors.add(color);
        }

        //criar um set de dados e atribuir cores a cada entry
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Concentração em Viseu");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(graficoReportsPrimeiroPiso));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        graficoReportsPrimeiroPiso.setData(pieData);
        graficoReportsPrimeiroPiso.invalidate();
        graficoReportsPrimeiroPiso.animateY(1400, Easing.EaseInOutQuad);
    }

    private void AdicionarDadosSegundoPisoPisoGrafico(PieChart graficoReportsPrimeiroPiso) {
        //encher grafico com dados imaginarios
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(0.25f, "Cantina"));
        pieEntries.add(new PieEntry(0.15f, "WC Feminino"));
        pieEntries.add(new PieEntry(0.15f, "WC Masculino"));
        pieEntries.add(new PieEntry(0.25f, "Sala de Reuniões"));
        pieEntries.add(new PieEntry(0.10f, "Anfiteatro"));

        //cores para os dados
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.JOYFUL_COLORS) {
            colors.add(color);
        }

        //criar um set de dados e atribuir cores a cada entry
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Concentração em Viseu");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(graficoReportsPrimeiroPiso));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        graficoReportsPrimeiroPiso.setData(pieData);
        graficoReportsPrimeiroPiso.invalidate();
        graficoReportsPrimeiroPiso.animateY(1400, Easing.EaseInOutQuad);
    }*/

}