package com.example.crowdzero_v000;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstituicaoInformacoesActivity extends NavDrawerActivity {

    private String nome="", descricao="";
    TextView textViewDescricao, textViewInformacoesEcontacto;
    boolean isUtilizadorEmpresa = false, btnFavEnabled= false;
    private int idlocal = 0;
    LatLng coordsInstituicao = null;
    ImageView imagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao_informacoes);

        idlocal = getIntent().getExtras().getInt("idlocal");

        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = (findViewById(R.id.linearLayoutInstituicoesInformacoes));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());
        textViewInformacoesEcontacto = findViewById(R.id.textViewInformacoesEcontacto);
        textViewInformacoesEcontacto.setTypeface(null, Typeface.BOLD);
        textViewInformacoesEcontacto.setTextSize(20f);
        textViewInformacoesEcontacto.setGravity(Gravity.CENTER_VERTICAL );


        pegarCoordsInstituicao();
        imagem = findViewById(R.id.imagemInstituicaoInfo);
        verficarSeLocalEstaFavoritado();


        //verificarSeUtilizadorEmpresa();
        if(!isUtilizadorEmpresa){
            LinearLayout ll = findViewById(R.id.linearLayoutBotaoInteriorInstiuicaoInfo);
            LinearLayout llDeFora = findViewById(R.id.linearLayoutBotoesInformacoesInstituicao);
            llDeFora.setPadding((int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,74,getResources().getDisplayMetrics()))
                    ,0,0,0);
            llDeFora.removeView(ll);
        }
        colocarListenersNosBotoes();
        criarGraficoNReportsUltimosDias(7);
        //criarGraficoComDados(null,null);
    }

    //String[] DIAS = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    ArrayList<String> DIAS;
    private void criarGraficoNReportsUltimosDias(int nDias) {
        try{
            FuncoesApi.FuncoesLocais.getNReportsUltimosXDias(getApplicationContext(), nDias, idlocal, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    Log.i("pedido", jsonObject.toString());
                    JSONArray diasRep = jsonObject.getJSONArray("NReportsArray");
                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<String> dias = new ArrayList<>();
                    for (int i = 0; i < diasRep.length(); i++) {
                        JSONObject a = diasRep.getJSONObject(i);
                        JSONObject info = a.getJSONObject("info");
                        dias.add(a.getString("dia"));
                        int numeroDia =0;
                        //for(int x = 0;x<DIAS.length;x++){
                        //    if(DIAS[x].equals(a.getString("dia"))){
                        //        numeroDia = x;
                        //        break;
                        //    }
                        //}
                        //este 6-i é para obter o numero oposto ao i no intervalo [0,6]
                        barEntries.add(new BarEntry((float)6-i, (float) info.getDouble("media")));

                    }

                    criarGraficoComDados(barEntries, dias);
                }

                @Override
                public void onError(JSONObject jsonObjectErr) throws JSONException {

                }
            });
        } catch(Exception e){
            Log.i("pedido",e.getMessage());
            e.printStackTrace();
        }
    }


    public static class MyBarDataSet extends BarDataSet{
        public MyBarDataSet(List<BarEntry> yVals, String label) {
            super(yVals, label);
        }

        @Override
        public int getColor(int index) {
            float valor = getEntryForIndex(index).getY();
            //set.setColors(Color.RED, Color.YELLOW,Color.GREEN);
            if(valor>=2.5) return mColors.get(0);
            if(valor>=1.5) return mColors.get(1);
            else return mColors.get(2);
        }
    }

    void criarGraficoComDados(ArrayList<BarEntry> barEntries, final ArrayList<String> dias){
        MaterialCardView mcv = findViewById(R.id.cardBarChartInstituicaoReports);
        BarChart barChart = findViewById(R.id.barchartInstituicaoReports);
        barChart.setMinimumHeight(100);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setTextSize(15f);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);

        barChart.getAxisRight().setYOffset(25f);
        barChart.getAxisRight().setEnabled(true);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setAxisMaximum(0f);
        barChart.getAxisRight().setAxisMaximum(3f);
        barChart.getAxisRight().setGranularity(1f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setGranularity(0.1f);
        barChart.getAxisLeft().setEnabled(false);
        barChart.animateY(600);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dias.get((int) value);
            }
        });
        xAxis.setTextSize(12f);

        MyBarDataSet set = new MyBarDataSet(barEntries, "Média Reports na última semana");
        set.setColors(Color.RED, Color.YELLOW,Color.GREEN);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        set.setDrawValues(false);
        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.invalidate();

    }

    void verficarSeLocalEstaFavoritado(){
        final ImageButton btnFav = findViewById(R.id.imgBtnAdicionarInstituicaoFavoritos);
        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        FuncoesApi.FuncoesLocais.verificarSeLocalEstaNaLista(getApplicationContext(), f.getIDPessoa(), idlocal, new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                if(jsonObject.getBoolean("existe")) {
                    btnFavEnabled = true;
                    btnFav.setImageResource(R.drawable.ic_favorite_filled_black_24dp);
                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {

            }
        });
    }



    void colocarListenersNosBotoes(){
        final ImageButton btnMapa, btnReport, btnOpiniao, btnFav;
        btnFav = findViewById(R.id.imgBtnAdicionarInstituicaoFavoritos);
        btnMapa = findViewById(R.id.botaoMapaInfoInstituicao);
        btnReport = findViewById(R.id.botaoReportsInfoInstituicao);
        btnOpiniao = findViewById(R.id.botaoOpinioesInfoInstituicao);
        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        //region botao favoritos
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFavEnabled){
                    FuncoesApi.FuncoesLocais.removerLocalLista(getApplicationContext(), f.getIDPessoa(), idlocal, new FuncoesApi.volleycallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            if(jsonObject.getBoolean("sucesso")) {
                                btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                                btnFavEnabled = !btnFavEnabled;
                            }
                            else Toast.makeText(getApplicationContext(),"Local nao se encontra na lista de favoritos", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(JSONObject jsonObjectErr) throws JSONException {
                            Toast.makeText(getApplicationContext(),"Erro a remover local da lista de favoritos", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    try{
                        FuncoesApi.FuncoesLocais.favoritarLocal(getApplicationContext(), f.getIDPessoa(), idlocal, new FuncoesApi.volleycallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                btnFav.setImageResource(R.drawable.ic_favorite_filled_black_24dp);
                                btnFavEnabled=!btnFavEnabled;
                            }
                            @Override
                            public void onError(JSONObject jsonObjectErr) throws JSONException {
                                Toast.makeText(getApplicationContext(),"Erro a adicionar local a lista de favoritos",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }

            }
        });
        //endregion
        //region botao mapa e report
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comecarActivityMapa();
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaReportsInstituicaoActivity.class);
                i.putExtra("opcaoEscolhida","Home");
                i.putExtra("opcaoEscolhidaItemID",-1);
                i.putExtra("nome",nome);
                i.putExtra("descricao",descricao);
                i.putExtra("idlocal",idlocal);
                startActivity(i);
            }
        });
        //endregion
        //region botao opiniao
        btnOpiniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),OpinioesActivity.class);
                i.putExtra("opcaoEscolhida","Home");
                i.putExtra("opcaoEscolhidaItemID",-1);
                i.putExtra("nome",nome);
                i.putExtra("descricao",descricao);
                i.putExtra("idlocal",idlocal);
                startActivity(i);
            }
        });
        //endregion
        //region reports interiores
        if(isUtilizadorEmpresa){
            ImageButton btnInterior;
            btnInterior = findViewById(R.id.botaoInteriorInfoInstituicao);
            btnInterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Ao ver a lista de reports pode escolher entre tipos de reports e pode tambem fazer outros tipos de reports",Toast.LENGTH_LONG).show();
                }
            });
        }
        //endregion
    }
    void verificarSeUtilizadorEmpresa(){
        if((new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE))).getTipoPessoa().equals(FuncoesSharedPreferences.utilInst))
            isUtilizadorEmpresa =true;

    }

    void pegarCoordsInstituicao(){
        FuncoesApi.FuncoesLocais.getLocalPorId(getApplicationContext(), idlocal, new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                descricao = jsonObject.getJSONObject("Local").getString("Descricao");
                nome = jsonObject.getJSONObject("Local").getString("Nome");
                coordsInstituicao = new LatLng(jsonObject.getJSONObject("Local").getDouble("Latitude")
                        ,jsonObject.getJSONObject("Local").getDouble("Longitude"));
                colocarInfosEcra();
                downloadImagem( jsonObject.getJSONObject("Local").getString("URL_Imagem"));
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
            }
        });

    }
    void colocarInfosEcra(){
        textViewDescricao = findViewById(R.id.textViewDescricaoInstituicao);
        textViewDescricao.setText(descricao);
        this.mudarNomeToolBar(nome);
    }
    void downloadImagem(String urlimagem){
        FuncoesApi.downloadImagem(getApplicationContext(), urlimagem, new FuncoesApi.volleyimagecallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                imagem.setImageBitmap(bitmap);
            }
        });
    }

    void comecarActivityMapa(){
        Intent i = new Intent(getApplicationContext(), MapaActivity.class);
        Bundle b = new Bundle();
        if(coordsInstituicao == null) {
            b.putDouble("lat", 40.6577125);
            b.putDouble("lng",-7.9141467);
        }else{
            b.putDouble("lat",coordsInstituicao.latitude);
            b.putDouble("lng",coordsInstituicao.longitude);
        }
        i.putExtra("coords",b);
        i.putExtra("opcaoEscolhida","mapa"); // colocar "mapa" na tb e na barra lateral
        i.putExtra("opcaoEscolhidaItemID",nv.getMenu().getItem(5).getItemId());
        startActivity(i);
    }
}