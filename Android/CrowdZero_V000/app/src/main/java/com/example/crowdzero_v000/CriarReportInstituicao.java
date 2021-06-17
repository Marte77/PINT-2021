package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CriarReportInstituicao extends NavDrawerActivity {
    String nome, descricao;
    int idlocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_report_instituicao);
        nome = getIntent().getExtras().getString("nome");
        descricao = getIntent().getExtras().getString("descricao");
        idlocal = getIntent().getExtras().getInt("idlocal");
        mudarNomeToolBar(nome);

    }
}