package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.fragmentos.CardOpiniaoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpinioesActivity extends NavDrawerActivity {

    int idlocal;
    String nomelocal, descricao;
    LinearLayout linearlayoutdascrollview = null;
    FloatingActionButton botaoEditarCriarOpiniao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinioes);
        this.mudarNomeToolBar("Opiniões");
        idlocal = getIntent().getExtras().getInt("idlocal");
        nomelocal = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");

        linearlayoutdascrollview = findViewById(R.id.linearlayoutinsidecardsOpinioes);

        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout layoutdefora = findViewById(R.id.linearLayoutDeForaOpinioes);
        layoutdefora.setPadding(layoutdefora.getLeft(), alturatb, layoutdefora.getRight(), layoutdefora.getBottom());

        obterComentariosTodos();

        botaoEditarCriarOpiniao = findViewById(R.id.criarOuEditarOpiniao);
        botaoEditarCriarOpiniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CriarEditarOpiniao.class);
                i.putExtra("opcaoEscolhida","Home");
                i.putExtra("opcaoEscolhidaItemID",-1);
                i.putExtra("nome",nomelocal);
                i.putExtra("descricao",descricao);
                i.putExtra("idlocal",idlocal);
                startActivity(i);
            }
        });
    }

    void obterComentariosTodos(){
        try{
            FuncoesApi.FuncoesComentarios.getTodosComentariosLocal(getApplicationContext(), idlocal, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    Log.i("pedido", jsonObject.toString());
                    JSONArray comentarios = jsonObject.getJSONArray("Comentarios");
                    JSONObject comentario, pessoa;
                    for (int i = 0; i < comentarios.length(); i++) {
                        comentario = comentarios.getJSONObject(i);
                        pessoa = comentario.getJSONObject("Pessoa");
                        adicionarCard(pessoa.getString("PNome") + " " + pessoa.getString("UNome"),
                                comentario.getInt("ID_Comentario"),
                                comentario.getInt("PessoaIDPessoa"),
                                comentario.getInt("Classificacao"),
                                comentario.getString("Data"),
                                comentario.getString("Descricao"));
                    }
                }

                @Override
                public void onError(JSONObject jsonObjectErr) {
                    Log.i("pedido", jsonObjectErr.toString());
                    Toast.makeText(getApplicationContext(), "Erro a obter as opiniões deste local", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.i("pedido", "Erro catch obter opinioes activity: " + e);
            e.printStackTrace();
        }
    }


    void adicionarCard(String nomePessoa,int idOpiniao, int idPessoaOpiniao, int classificacao, String data, String descricao){

        CardOpiniaoFragment c = CardOpiniaoFragment.newInstance(nomePessoa,idOpiniao,idPessoaOpiniao,classificacao,data,descricao);
        getSupportFragmentManager().beginTransaction().add(R.id.linearlayoutinsidecardsOpinioes,c).commit();
    }
}