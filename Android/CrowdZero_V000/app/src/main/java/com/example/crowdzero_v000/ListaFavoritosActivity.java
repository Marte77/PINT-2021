package com.example.crowdzero_v000;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.example.crowdzero_v000.fragmentos.CardInstituicoesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListaFavoritosActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_favoritos);

        /*
         * adicionar padding em cima da SV, caso contrário ficaria a sobrepor a topbar
         * */
        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutListaFavoritos));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());

        /*adicionarCard();
        adicionarCard();*/

        obterLocaisFavoritados();
    }

    void obterLocaisFavoritados(){
        final Context c = getApplicationContext();
        FuncoesSharedPreferences f = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        FuncoesApi.FuncoesLocais.getListaLocaisFavotitados(c, f.getIDPessoa(), new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                Log.i("pedido","get lista locais favoritos: "+ jsonObject.toString());
                if(!jsonObject.getBoolean("sucesso")) {

                    return;
                }
                JSONArray arrayLocais = jsonObject.getJSONArray("lista");

                for(int i =0;i<arrayLocais.length();i++){
                    int idLocal = arrayLocais.getJSONObject(i).getInt("LocalIDLocal");
                    FuncoesApi.FuncoesLocais.getLocalPorId(c, idLocal, new FuncoesApi.volleycallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            Log.i("pedido","pedido get local favorito por id: "+jsonObject.toString());
                            if(jsonObject.getInt("status")==500)
                                return;
                            JSONObject local = jsonObject.getJSONObject("Local");
                            adicionarCard(local.getString("Nome"),local.getString("Descricao"),local.getInt("ID_Local"),local.getString("URL_Imagem"));
                        }

                        @Override
                        public void onError(JSONObject jsonObjectErr) throws JSONException {
                            Log.i("pedido","Erro get local id favorito: " + jsonObjectErr.toString());
                        }
                    });
                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido","Erro a obter lista de favoritos: "+jsonObjectErr.toString());
            }
        });
    }

    public void adicionarCard(String nomeLocal, String descricao, int idLocal,String urlImagem){
        Fragment cardInicial = CardInstituicoesFragment.newInstance(nomeLocal,descricao,
                idLocal,urlImagem);
        getSupportFragmentManager().beginTransaction()
                .add(findViewById(R.id.linearLayoutFragmentsInstituicoesFavs).getId(),cardInicial,"instituicao"+idLocal).
                commit();
    }
}