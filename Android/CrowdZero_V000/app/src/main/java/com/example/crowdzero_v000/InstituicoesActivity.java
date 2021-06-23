package com.example.crowdzero_v000;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.fragmentos.CardInstituicoesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstituicoesActivity extends NavDrawerActivity {

    int nCards=0;
    FuncoesApi.volleycallback VCB = new FuncoesApi.volleycallback() {
        @Override
        public void onSuccess(JSONObject jsonObject) throws JSONException {
            //Log.i("pedido", jsonObject.toString());
            if(jsonObject.getInt("status") == 500){
                Toast.makeText(getApplicationContext(),"Erro a obter os locais",Toast.LENGTH_LONG).show();
                return;
            }

            JSONArray jsonArray = jsonObject.getJSONArray("Locais");
            int nLocais = jsonArray.length();
            //Log.i("pedido", String.valueOf(jsonObject.getJSONArray("Locais").length()));
            for(int i = 0;i<nLocais;i++){
                JSONObject j= (JSONObject) jsonArray.get(i);
                String nome = j.getString("Nome");
                int idLocal = j.getInt("ID_Local");
                String descricao = j.getString("Descricao");
                String urlimagem = j.getString("URL_Imagem");

                adicionarCard(nome,idLocal,descricao, urlimagem);
            }
        }

        @Override
        public void onError(JSONObject jsonObjectErr) throws JSONException {
            Log.i("pedido",jsonObjectErr.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicoes);

        /*
        * adicionar padding em cima da SV, caso contrário ficaria a sobrepor a topbar
        * */
        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutListaInstituicoes));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());


        FuncoesApi.FuncoesLocais.getTodosLocais(getApplicationContext(),VCB);

    }


    public void adicionarCard(String nome, int idLocal,String descricao,String urlImagem){
        nCards++;
        CardInstituicoesFragment cardInicial = CardInstituicoesFragment.newInstance(
                nome,
                descricao,
                idLocal,
                urlImagem);
        getSupportFragmentManager().beginTransaction()
                .add(findViewById(R.id.linearLayoutFragmentsInstituicoes).getId(),cardInicial,"instituicao"+nCards).
                commit();

    }
}
