package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClassificacaoActivity extends NavDrawerActivity {
    ImageView fotoDePerfil;
    TextView txtPontuacaoUtilizador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificacao);

        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = (findViewById(R.id.linearLayoutExteriorClassificacao));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());
        fotoDePerfil = findViewById(R.id.fotoPerfilUtilizadorClassificacao);
        txtPontuacaoUtilizador = findViewById(R.id.PontuacaoUtilizadorClassificao);
        txtPontuacaoUtilizador.setTypeface(null, Typeface.BOLD);
        txtPontuacaoUtilizador.setTextSize(25f);
        txtPontuacaoUtilizador.setGravity(Gravity.CENTER);

        // TODO: 25/06/2021 terminar design, adicionar circulo branco a volta das pfps

        obterPontuacaoEfotoDePerfil();

        colocarTop3Pessoas();
    }

    private void colocarTop3Pessoas() {
        TextView txtviewNome3lugar, txtviewNome2lugar, txtviewNome1lugar;
        ImageView imgview3lugar, imgview2lugar, imgview1lugar;
        txtviewNome1lugar = findViewById(R.id.textViewNomePessoaPrimeiroLugar);
        txtviewNome2lugar = findViewById(R.id.textViewNomePessoaSegundoLugar);
        txtviewNome3lugar = findViewById(R.id.textViewNomePessoaTerceiroLugar);
        imgview1lugar = findViewById(R.id.fotoPerfilPessoaPrimeiroLugar);
        imgview2lugar = findViewById(R.id.fotoPerfilPessoaSegundoLugar);
        imgview3lugar = findViewById(R.id.fotoPerfilPessoaTerceiroLugar);
        TextView txtViewPontos3lugar, txtViewPontos2lugar, txtViewPontos1lugar;
        txtViewPontos3lugar = findViewById(R.id.textViewPontuacaoPessoaPrimeiroLugar);
        txtViewPontos2lugar = findViewById(R.id.textViewPontuacaoPessoaSegundoLugar);
        txtViewPontos1lugar = findViewById(R.id.textViewPontuacaoPessoaTerceiroLugar);

        final TextView[] arraytxts = new TextView[3];
        arraytxts[0] = txtviewNome1lugar;
        arraytxts[1] = txtviewNome2lugar;
        arraytxts[2] = txtviewNome3lugar;
        final TextView[] arrayptstxts = new TextView[3];
        arrayptstxts[0] = txtViewPontos3lugar;
        arrayptstxts[1] = txtViewPontos2lugar;
        arrayptstxts[2] = txtViewPontos1lugar;
        final ImageView[] arrayimgs = new ImageView[3];
        arrayimgs[0] = imgview1lugar;
        arrayimgs[1] = imgview2lugar;
        arrayimgs[2] = imgview3lugar;

        final Context appcontext = getApplicationContext();
        FuncoesApi.FuncoesPessoas.getTopXPessoasComMaisPontos(appcontext, 3, new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                JSONArray top3 = jsonObject.getJSONArray("Top3");
                //tem sempre a primeira pessoa no index 0
                String nome, urlImagem;
                int pontos;
                for(int i = 0; i<top3.length() || i == 3;i++){
                    nome = top3.getJSONObject(i).getJSONObject("Pessoa").getString("PNome")
                            +" "+top3.getJSONObject(i).getJSONObject("Pessoa").getString("UNome");
                    if(top3.getJSONObject(i).has("Pontos")){
                        //é util inst
                        pontos = top3.getJSONObject(i).getInt("Pontos");
                    }else pontos = top3.getJSONObject(i).getInt("Pontos_Outro_Util");
                    arraytxts[i].setText(Html.fromHtml("<b>"+nome+"</b>"));
                    arrayptstxts[i].setText(Html.fromHtml("<b>Pontos: " + pontos+"</b>"));
                    urlImagem = top3.getJSONObject(i).getJSONObject("Pessoa").getString("Foto_De_Perfil");
                    final int finalI = i;
                    FuncoesApi.downloadImagem(appcontext, urlImagem, new FuncoesApi.volleyimagecallback() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            arrayimgs[finalI].setImageBitmap(bitmap);
                        }
                    });
                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {

            }
        });
    }


    void obterPontuacaoEfotoDePerfil(){
        final FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        try{
            FuncoesApi.FuncoesPessoas.getInformacoesPessoa(getApplicationContext(), f.getIDPessoa(), new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    String pontos;
                    if (f.getTipoPessoa().equals(FuncoesSharedPreferences.outrosUtil)) {
                        int pts = jsonObject.getInt("Pontos_Outro_Util");
                        if (pts == 1)
                            pontos = pts + " Ponto";
                        else pontos = pts + " Pontos";
                    } else {
                        int pts = jsonObject.getInt("Pontos");
                        if (pts == 1)
                            pontos = pts + " Ponto";
                        else pontos = pts + " Pontos";
                    }
                    txtPontuacaoUtilizador.setText(pontos);
                    if (!jsonObject.getJSONObject("Pessoa").get("Foto_De_Perfil").toString().equals("null")) {
                        obterFotoDePerfil(jsonObject.getJSONObject("Pessoa").getString("Foto_De_Perfil"));
                    }
                }

                @Override
                public void onError(JSONObject jsonObjectErr) {
                    Log.i("pedido", "erro classifacao: " + jsonObjectErr);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        obterPontuacao();
    }
    void obterPontuacao(){
        final TextView reports,ranking,likes;
        reports = findViewById(R.id.textViewReportsClassificao);
        ranking = findViewById(R.id.textViewRankingClassificao);
        likes = findViewById(R.id.textViewLikesClassificao);
        FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        try {
            FuncoesApi.FuncoesReports.getNumeroReportsUtilizador(getApplicationContext(), f.getIDPessoa(), new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    Log.i("pedido",jsonObject.toString());
                    int nreports, rank, like;
                    nreports =jsonObject.getInt("Numero_Reports");
                    rank = jsonObject.getJSONObject("Pessoa").getInt("Ranking");
                    like = jsonObject.getInt("NLikes");
                    reports.setText(String.valueOf(nreports));
                    ranking.setText(String.valueOf(rank));
                    likes.setText(String.valueOf(like));
                }

                @Override
                public void onError(JSONObject jsonObjectErr){
                    Log.i("pedido",jsonObjectErr.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void obterFotoDePerfil(String urlFoto){
        FuncoesApi.downloadImagem(getApplicationContext(), urlFoto, new FuncoesApi.volleyimagecallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                fotoDePerfil.setImageBitmap(bitmap);
            }
        });

    }
}