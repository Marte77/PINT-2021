package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ForumActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //NAO FOI IMPLEMENTADA NA BASE DE DADOS
        //ERA SUPOSTO SER UM CHAT MAS ISSO JA FICAVA MUITO AQUEM DO TRABALHO PROPOSTO
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
    }
}