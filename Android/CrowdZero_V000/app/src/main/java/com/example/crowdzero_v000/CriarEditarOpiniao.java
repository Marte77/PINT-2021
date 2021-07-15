package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CriarEditarOpiniao extends NavDrawerActivity {

    String dataOpiniao, descricao="";
    int classificacao = 0;
    int idLocal;
    MaterialButton botaoSubmeter;
    AutoCompleteTextView inputClassificacao;
    TextInputEditText inputDescricao;
    FuncoesSharedPreferences f;
    Integer[] opcoesClassificao = {1,2,3,4,5};
    boolean opiniaoJaExiste = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_editar_opiniao);
        this.mudarNomeToolBar("Criar/Editar Opinião");
        idLocal = getIntent().getExtras().getInt("idlocal");

        f =  new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
        inputDescricao = findViewById(R.id.descricaoInputOpiniao);
        inputClassificacao = findViewById(R.id.autoCompleteClassificacaoOpiniao);

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.lista_niveis_densidade,opcoesClassificao);
        inputClassificacao.setAdapter(arrayAdapter);

        botaoSubmeter = findViewById(R.id.botaoSubmeterOpiniao);
        botaoSubmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submeterOpiniao();
            }
        });

        obterOpiniaoSeExiste();
    }

    void submeterOpiniao(){
        if(inputDescricao.getText().toString().isEmpty() || inputClassificacao.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Preencha todos os campos!",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FuncoesApi.FuncoesComentarios.criarEditarNovoComentario(opiniaoJaExiste,
                    getApplicationContext(),
                    inputDescricao.getText().toString(),
                    Integer.parseInt(inputClassificacao.getText().toString()),
                    idLocal,
                    f.getIDPessoa(),
                    new FuncoesApi.volleycallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            Log.i("pedido", jsonObject.toString());
                            Toast.makeText(getApplicationContext(), "Opinião submetida com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(JSONObject jsonObjectErr) throws JSONException {
                            Log.i("pedido", jsonObjectErr.toString());
                            Toast.makeText(getApplicationContext(), "Erro a submeter opinião", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void obterOpiniaoSeExiste(){
        FuncoesApi.FuncoesComentarios.getComentario(getApplicationContext(), idLocal, f.getIDPessoa(), new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                Log.i("pedido",jsonObject.toString());
                if(jsonObject.has("Comentario")){
                    JSONObject comentario = jsonObject.getJSONObject("Comentario");
                    opiniaoJaExiste = true;
                    descricao = comentario.getString("Descricao");
                    classificacao = comentario.getInt("Classificacao");
                    inputDescricao.setText(descricao);
                    for(int i = 0; i<opcoesClassificao.length;i++) {
                        if(opcoesClassificao[i] == classificacao) {
                            Log.i("testar", String.valueOf(opcoesClassificao[i]));
                            inputClassificacao.setText(String.valueOf(classificacao),false);
                        }
                    }
                }
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
            }
        });
    }
}