package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.crowdzero_v000.fragmentos.CardOpiniaoFragment;

public class OpinioesActivity extends NavDrawerActivity {

    int idlocal;
    LinearLayout linearlayoutdascrollview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinioes);
        this.mudarNomeToolBar("Opiniões");
        idlocal = getIntent().getExtras().getInt("idlocal");
        linearlayoutdascrollview = findViewById(R.id.linearlayoutinsidecardsOpinioes);

        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout layoutdefora = findViewById(R.id.linearLayoutDeForaOpinioes);
        layoutdefora.setPadding(layoutdefora.getLeft(), alturatb, layoutdefora.getRight(), layoutdefora.getBottom());

        adicionarCard("joao",1,3,4,"data","descricao");
        adicionarCard("cao",2,3,1,"data","descricao");
        adicionarCard("salvacao",1,3,3,"data","descricao");
        adicionarCard("salvacao",1,3,3,"data","descricao");
        adicionarCard("salvacao",1,3,3,"data","descricao");
        adicionarCard("salvacao",1,3,3,"data","descricao");
        adicionarCard("salvacao",1,3,3,"data","descricao");
    }

    void adicionarCard(String nomePessoa,int idOpiniao, int idPessoaOpiniao, int classificacao, String data, String descricao){
        CardOpiniaoFragment c = CardOpiniaoFragment.newInstance(nomePessoa,idOpiniao,idPessoaOpiniao,classificacao,data,descricao);
        getSupportFragmentManager().beginTransaction().add(R.id.linearlayoutinsidecardsOpinioes,c).commit();
    }
}