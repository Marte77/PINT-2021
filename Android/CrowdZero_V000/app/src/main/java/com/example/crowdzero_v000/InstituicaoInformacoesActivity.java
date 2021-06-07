package com.example.crowdzero_v000;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class InstituicaoInformacoesActivity extends NavDrawerActivity {

    private String nome="", descricao="";
    TextView textViewDescricao, textViewInformacoesEcontacto;
    boolean isUtilizadorEmpresa = false, btnFavEnabled= false;
    LatLng coordsInstituicao = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao_informacoes);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");


        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutInstituicoesInformacoes));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());
        textViewInformacoesEcontacto = findViewById(R.id.textViewInformacoesEcontacto);
        textViewInformacoesEcontacto.setTypeface(null, Typeface.BOLD);
        textViewInformacoesEcontacto.setTextSize(25f);
        textViewInformacoesEcontacto.setGravity(Gravity.CENTER_VERTICAL );

        textViewDescricao = findViewById(R.id.textViewDescricaoInstituicao);
        textViewDescricao.setText(descricao);
        this.mudarNomeToolBar(nome);
        //todo: pegar coords da instituicao
        pegarCoordsInstituicao();


        //TODO:verificar se é utilizador e verficar se é favorito para ligar o botao do fav
        verificarSeUtilizadorEmpresa();
        if(!isUtilizadorEmpresa){
            LinearLayout ll = findViewById(R.id.linearLayoutBotaoInteriorInstiuicaoInfo);
            LinearLayout llDeFora = findViewById(R.id.linearLayoutBotoesInformacoesInstituicao);
            llDeFora.setPadding((int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,74,getResources().getDisplayMetrics()))
                    ,0,0,0);
            llDeFora.removeView(ll);
        }
        colocarListenersNosBotoes();


    }
    void colocarListenersNosBotoes(){
        final ImageButton btnMapa, btnReport, btnOpiniao, btnFav;
        btnFav = findViewById(R.id.imgBtnAdicionarInstituicaoFavoritos);
        btnMapa = findViewById(R.id.botaoMapaInfoInstituicao);
        btnReport = findViewById(R.id.botaoReportsInfoInstituicao);
        btnOpiniao = findViewById(R.id.botaoOpinioesInfoInstituicao);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"adicionar inst aos favs",Toast.LENGTH_SHORT).show();
                if(btnFavEnabled){
                    btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                }else{
                    btnFav.setImageResource(R.drawable.ic_favorite_filled_black_24dp);
                }
                btnFavEnabled=!btnFavEnabled;
            }
        });
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comecarActivityMapa(false);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnOpiniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(isUtilizadorEmpresa){
            ImageButton btnInterior;
            btnInterior = findViewById(R.id.botaoInteriorInfoInstituicao);
            btnInterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comecarActivityMapa(true);
                }
            });
        }
    }
    void verificarSeUtilizadorEmpresa(){
        boolean eUtiliziador = true;
        if(eUtiliziador)
            isUtilizadorEmpresa =true;

    }

    void pegarCoordsInstituicao(){
        double lat = 40.6510224, lng = -7.9495194;
        //fazer pedido para pegar coords
        coordsInstituicao = new LatLng(40.6510224,-7.9495194);
    }


    void comecarActivityMapa(boolean isInterior){
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
        //if(isInterior)
            //todo:meter a abrir a activity mapa no interior
        startActivity(i);
    }
}