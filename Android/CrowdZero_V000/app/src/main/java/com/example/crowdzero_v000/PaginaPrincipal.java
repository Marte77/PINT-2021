package com.example.crowdzero_v000;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdzero_v000.fragmentos.CardReportFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

//<!--Note: The layout_width and layout_height attributes should be set to wrap_content, match_parent, or a custom dimension depending on the navigation drawer type and parent ViewGroup.-->

public class PaginaPrincipal extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        CardReportFragment cardInicial = CardReportFragment.newInstance("Martinho\nàs 09:47 de 20/05/21","Muito populado - Muitas pessoas na fila da cantina assustei-me");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout,cardInicial).commit();



        /*
        * if(utilizador pertence a empresa){
        *   adicionarPieChartUtilizadorEmpresa()}
        * else adicionarPieChartOutrosUtilizadores()*/
        adicionarPieChartOutrosUtilizadores();
        //adicionarPieChartUtilizadorEmpresa();
    }




    private void adicionarPieChartOutrosUtilizadores(){
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

    private void setupGrafico(PieChart graficoReports) {
        graficoReports.setDrawHoleEnabled(false);
        graficoReports.setUsePercentValues(true);
        graficoReports.getLegend().setEnabled(false);
        graficoReports.getDescription().setEnabled(false);
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
    }

}