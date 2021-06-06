package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InstituicaoInformacoesActivity extends NavDrawerActivity {

    private String nome="", descricao="";
    TextView textViewDescricao, textViewInformacoesEcontacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao_informacoes);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");


        int alturatb = this.tb.getLayoutParams().height;
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.linearLayoutInstituicoesInformacoes));
        linearLayout.setPadding(linearLayout.getLeft(), alturatb, linearLayout.getRight(), linearLayout.getBottom());
        textViewInformacoesEcontacto = findViewById(R.id.textViewInformacoesEcontacto);
        textViewInformacoesEcontacto.setTypeface(null, Typeface.BOLD);
        textViewInformacoesEcontacto.setTextSize(25f);
        textViewInformacoesEcontacto.setGravity(Gravity.CENTER_VERTICAL );

        textViewDescricao = findViewById(R.id.textViewDescricaoInstituicao);
        textViewDescricao.setText(descricao);
        this.mudarNomeToolBar(nome);
    }
}