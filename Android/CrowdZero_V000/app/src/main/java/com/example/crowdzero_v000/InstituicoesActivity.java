package com.example.crowdzero_v000;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.crowdzero_v000.fragmentos.CardInstituicoesFragment;

public class InstituicoesActivity extends NavDrawerActivity {

    int nCards=0;
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


        adicionarCard();
        adicionarCard();
        adicionarCard();
        adicionarCard();
    }


    public void adicionarCard(){
        nCards++;
        CardInstituicoesFragment cardInicial = CardInstituicoesFragment.newInstance("Instituicao"+nCards,"bem bonita"+nCards);
        //cardInicial.getView().findViewById(R.id.DetalhesFragmentoBotao).setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        adicionarCard();
        //    }
        //});

        getSupportFragmentManager().beginTransaction()
                .add(findViewById(R.id.linearLayoutFragmentsInstituicoes).getId(),cardInicial,"instituicao"+nCards).
                commit();

    }
}
