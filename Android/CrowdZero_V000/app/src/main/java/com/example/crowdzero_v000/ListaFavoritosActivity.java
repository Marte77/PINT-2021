package com.example.crowdzero_v000;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.crowdzero_v000.fragmentos.CardInstituicoesFragment;

public class ListaFavoritosActivity extends NavDrawerActivity {

    int nCards=0;
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

        adicionarCard();
        adicionarCard();


    }

    public void adicionarCard(){
        nCards++;
        Fragment cardInicial = CardInstituicoesFragment.newInstance("Instituicao"+nCards,"bem bonita"+nCards,1,"https://i.pinimg.com/originals/61/fe/4e/61fe4e49002d754ec69fcf50210e209c.jpg");
        //cardInicial.getView().findViewById(R.id.DetalhesFragmentoBotao).setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        adicionarCard();
        //    }
        //});

        getSupportFragmentManager().beginTransaction()

                .add(findViewById(R.id.linearLayoutFragmentsInstituicoesFavs).getId(),cardInicial,"instituicao"+nCards).
                commit();
    }
}