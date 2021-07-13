package com.example.crowdzero_v000;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

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