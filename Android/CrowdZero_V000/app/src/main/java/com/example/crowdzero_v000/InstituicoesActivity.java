package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class InstituicoesActivity extends NavDrawerActivity {

    int nCards=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicoes);

        int alturatb = this.tb.getLayoutParams().height;
        ScrollView SV = findViewById(R.id.ScrollViewListaInstituicoes);
        /*
        * adicionar padding em cima da SV, caso contrário ficaria a sobrepor a topbar
        * */
        SV.setPadding(SV.getLeft(),alturatb+1,SV.getRight(),SV.getBottom());

        adicionarCard();
        adicionarCard();
        adicionarCard();
        adicionarCard();
    }


    public void adicionarCard(){
        nCards++;
        Fragment cardInicial = CardInstituicoesFragment.newInstance("Instituicao"+nCards,"bem bonita"+nCards);
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